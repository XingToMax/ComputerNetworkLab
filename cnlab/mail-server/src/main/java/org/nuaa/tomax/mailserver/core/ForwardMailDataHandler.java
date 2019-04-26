package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.constant.SmtpInstruction;
import org.nuaa.tomax.mailserver.constant.SmtpResponseState;
import org.nuaa.tomax.mailserver.utils.HostsProxy;
import org.nuaa.tomax.mailserver.utils.MailSendUtil;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Name: ForwardMailDataHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 11:07
 * @Version: 1.0
 */
@Log
@Component(value = "forward")
public class ForwardMailDataHandler implements IDataHandler {
    /**
     * dns记录查询上下文
     */
    private static InitialDirContext dnsContext;

    static {
        try {
            dnsContext = new InitialDirContext(new Hashtable<String, String>() {{
                put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
            }});
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handle(MailBean mail) {
        int port = 25;
        String host = extractHostFromEmailAddress(mail.getTo());
        List<String> addressList = queryDomain(host);
        if (host.equals("tomax.xin") || host.equals("toyer.xyz")) {
            addressList = new ArrayList<>();
            addressList.add("127.0.0.1");
            port = HostsProxy.queryLocalhostPort(host);
        }
        Socket socket = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            for (String address : addressList) {
                socket = new Socket(address, port);
                log.info("connect to : " + address + "(" + port + ")");
                bis = new BufferedInputStream(socket.getInputStream());
                bos = new BufferedOutputStream(socket.getOutputStream());

                if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.CONNECT_SUCCESS) {
                    log.info("connect error");
                    continue;
                }

                break;
            }

            if (socket == null) {
                log.info("connect error");
                return false;
            }

            return hello(mail, bis, bos);
        } catch (UnknownHostException e) {
            log.info("error : unknown host");
        } catch (IOException e) {
            log.info("error : connect to socket error");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean hello(MailBean mail, BufferedInputStream bis, BufferedOutputStream bos) {
        log.info("begin hello to smtp server");
        // TODO : modify host name
        MailSendUtil.sendMessage(bos, SmtpInstruction.HELLO + " tomax.xin");
        MailSendUtil.extractMessage(bis);
//        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
//            log.info("hello error");
//            return false;
//        }
        return from(mail, bis, bos);
    }

    private boolean from(MailBean mail, BufferedInputStream bis, BufferedOutputStream bos) {
        log.info("begin from stmp server");
        // TODO : choose right from address
        String mf = "0@tomax.xin";
        MailSendUtil.sendMessage(bos, SmtpInstruction.MAIL_FROM + ":<" + mf + ">");
        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
            log.info("from error");
            return false;
        }
        return to(mail, bis, bos);
    }

    private boolean to(MailBean mail, BufferedInputStream bis, BufferedOutputStream bos) {
        log.info("begin to stmp server");
        MailSendUtil.sendMessage(bos, SmtpInstruction.MAIL_TO + ":<" + mail.getTo() + ">");
        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
            log.info("to error");
            return false;
        }
        return data(mail, bis, bos);
    }

    private boolean data(MailBean mail, BufferedInputStream bis, BufferedOutputStream bos) {
        log.info("begin data smtp server");
        MailSendUtil.sendMessage(bos, SmtpInstruction.DATA);
        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.START_SEND) {
            log.info("data error");
            return false;
        }

        // TODO : send to be optimized
        byte[] buffer = mail.getData().getBytes();
        try {
            bos.write(buffer);
            bos.flush();
        } catch (IOException e) {
            log.info("send data error");
        }
        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
            log.info("send data error");
            return false;
        }

        return quit(mail, bis, bos);
    }

    private boolean quit(MailBean mail, BufferedInputStream bis, BufferedOutputStream bos) {
        log.info("begin quit smtp server");
        MailSendUtil.sendMessage(bos, SmtpInstruction.QUIT);
        MailSendUtil.extractMessage(bis);
//        if (MailSendUtil.getResponseCode(bis) != SmtpResponseState.PROCESSING) {
//            log.info("quit error");
//            return false;
//        }
        return true;
    }

    /**
     * 通过url获取主机地址
     * @param url 目标url
     * @return 主机地址
     */
    public static List<String> queryDomain(String url) {
        NamingEnumeration records = null;
        try {
            records = dnsContext.getAttributes(url, new String[]{"MX"}).getAll();
            if (records.hasMore()) {
                url = records.next().toString();
                url = url.substring(url.indexOf(": ") + 2);
                String[] addressList = url.split(",");
                return Arrays.stream(addressList).
                        map(String::trim)
                        .sorted(Comparator.reverseOrder())
                        .map(s -> s.split("\\s+")[1])
                        .collect(Collectors.toList());
            }
            records = dnsContext.getAttributes(url, new String[]{"a"}).getAll();
            if (records.hasMore()) {
                url = records.next().toString();
                url = url.substring(url.indexOf(": ") + 2).replace(" ","");
                return Arrays.stream(url.split(",")).collect(Collectors.toList());
            }

            return Collections.singletonList(url);
        } catch (NamingException e) {
            // TODO : 错误待处理
            log.info(url + " query error");
            return Collections.emptyList();
        }
    }

    public static String extractHostFromEmailAddress(String url) {
        return url.split("@")[1];
    }


}
