package org.nuaa.tomax.mailclient.core;

import lombok.extern.java.Log;

import javax.naming.directory.InitialDirContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description: client mail sender
 * @Date: Created in 2019/4/10 15:57
 */
@Log
public class Sender {
    /**
     * smtp 端口
     */
    private final static int PORT = 25;
    /**
     * 连接失败重连最大次数
     */
    private final static int RETRY = 3;
    /**
     * 重连时间间隔
     */
    private final static int INTERVAL = 1000;
    /**
     * 网络超时限制
     */
    private final static int TIMEOUT = 10000;
    /**
     * 邮箱格式校验器
     */
    private final static Pattern MAIL_PATTERN = Pattern.compile(".+@[^.@]+(\\.[^.@]+)+$");

    /**
     * dns记录查询上下文
     */
    private static InitialDirContext dnsContext;

    /**
     * smtp服务器地址
     */
    private String smtp;

    static {
        try {
            dnsContext = new InitialDirContext(new Hashtable<String, String>() {{
                put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
            }});
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Sender(String smtp) {
        if (smtp == null) {
            throw new IllegalArgumentException("param stmp should not be null");
        }

        this.smtp = smtp;
    }

    public List<SendMessage> send(String from, String to, String content) {
        // check from
        if (!MAIL_PATTERN.matcher(from).find()) {
            throw new IllegalArgumentException("param from(" + from + ") is not a valid email address");
        }
        if (!MAIL_PATTERN.matcher(to).find()) {
            throw new IllegalArgumentException("param to(" + to + ") is not a valid email address");
        }

        List<SendMessage> messages = new LinkedList<>();

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        for (int i = 1; i <= RETRY; i++) {
            try {
                // begin connect
                log.info("begin to connect to stmp server(" + smtp + ")");
                socket = new Socket(smtp, PORT);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                log.info("connect to " + smtp + " success");
                // connect success and quit
                break;
            } catch (IOException e) {
                // connect fail
                String message = "connect to smtp server(" + smtp + ") fail, count " + i;
                log.info(message);
                messages.add(new SendMessage(message, 0));
                // retry
                try {
                    TimeUnit.MILLISECONDS.sleep(INTERVAL);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // connect fail after retry limit
        if (socket == null) {
            String message = "cannot connect to smtp server(" + smtp + ")";
            log.info(message);
            messages.add(new SendMessage(message, 0));
            return messages;
        }

        if (in == null || out == null) {
            String message = "cannot get input stream or output stream";
            log.info(message);
            messages.add(new SendMessage(message, 0));
            return messages;
        }

        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);


        return messages;
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
