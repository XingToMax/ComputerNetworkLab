package org.nuaa.tomax.mailclient.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailclient.constant.EncodingType;
import org.nuaa.tomax.mailclient.constant.MimeType;
import org.nuaa.tomax.mailclient.constant.SmtpInstruction;
import org.nuaa.tomax.mailclient.constant.SmtpResponseState;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;
import org.nuaa.tomax.mailclient.utils.StringUtil;

import javax.naming.directory.InitialDirContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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
    private final static int PORT = 8081;
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
     * mime boundary
     */
    private final static String BOUNDARY = "Tomax=MGEwNTIzYjhiMzBkNjNmNWQ5M2EyYmYzMmE5YzE3YjE=Boundary";

    /**
     * charset
     */
    private final static String CHARSET = Charset.defaultCharset().displayName();
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

    public List<SendMessage> send(MailBean mail) {
        // check from
//        if (!MAIL_PATTERN.matcher(from).find()) {
//            throw new IllegalArgumentException("param from(" + from + ") is not a valid email address");
//        }
        if (!MAIL_PATTERN.matcher(mail.getTo()).find()) {
            throw new IllegalArgumentException("param to(" + mail.getTo() + ") is not a valid email address");
        }

        log.info("mail send from " + mail.getFrom() + " to " + mail.getTo() + " begin");

        List<SendMessage> messages = new LinkedList<>();

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            // connect to smtp server
            for (int i = 1; i <= RETRY; i++) {
                try {
                    // begin connect
                    log.info("begin to connect to stmp server(" + smtp + ")");
                    socket = new Socket(smtp, PORT);
                    in = socket.getInputStream();
                    out = socket.getOutputStream();

                    bis = new BufferedInputStream(in);
                    bos = new BufferedOutputStream(out);

                    if (extractResponseCode(bis) != SmtpResponseState.CONNECT_SUCCESS) {
                        String message = "connect to stmp server fail";
                        log.info(message);
                        messages.add(new SendMessage(message, 0));
                    }

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

            socket.setSoTimeout(TIMEOUT);

            // hello
            sendMessage(bos, SmtpInstruction.HELLO + " " + extractHostFromEmailAddress(mail.getFrom()));
            if (extractResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
                String message = "helo error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // auth login
            sendMessage(bos, SmtpInstruction.AUTH);
            if (extractResponseCode(bis) != SmtpResponseState.AUTH_RESPONSE) {
                String message = "auth login error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // send username
            sendMessage(bos, mail.getUser());
            if (extractResponseCode(bis) != SmtpResponseState.AUTH_RESPONSE) {
                String message = "auth login(send user name) error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // send password
            sendMessage(bos, mail.getPassword());
            if (extractResponseCode(bis) != SmtpResponseState.AUTH_ACCESS) {
                String message = "auth login password error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // mail from
            sendMessage(bos, SmtpInstruction.MAIL_FROM + ": <" + mail.getFrom() + ">");
            if (extractResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
                String message = "mail from error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // mail to
            sendMessage(bos, SmtpInstruction.MAIL_TO + ": <" + mail.getTo() + ">");
            if (extractResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
                String message = "mail to error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // start data transmission
            sendMessage(bos, SmtpInstruction.DATA);
            if (extractResponseCode(bis) != SmtpResponseState.START_SEND) {
                String message = "start send data error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }
            // send data

            sendMessage(bos, "subject:" + mail.getSubject());
            sendMessage(bos, "from:" + mail.getFrom());
            sendMessage(bos, "to:" + mail.getTo());
            sendMessage(bos, "MIME-Version: 1.0");

            // contains attachments, content-type is multipart/mixed
            if (mail.ifNeedBoundary()) {
                sendMessage(bos, "Content-Type: " + EncodingType.CONTENT_TYPE_MULTIPART_MIXED +
                        "; BOUNDARY=\"" + BOUNDARY + "\"");
            } else if (mail.isHtml()){
                sendMessage(bos, "Content-Type: " + MimeType.getMimeType("html") +
                        "; charset=\"" + CHARSET +"\"");
            } else {
                sendMessage(bos, "Content-Type: text/plain; charset=\"" + CHARSET +"\"");
            }

            // set default content transfer encoding
            if (!mail.ifNeedBoundary()) {
                sendMessage(bos, "Content-Transfer-Encoding: base64");
            }

            // check urgent
            sendMessage(bos, "X-Priority: " + (mail.isUrgent() ? 1 : 3));

            // send content
            if (mail.ifNeedBoundary()) {
                sendMessage(bos, "--" + BOUNDARY);

                if (mail.isHtml()) {
                    sendMessage(bos, "Content-Type: " + MimeType.getMimeType("html") +
                            "; charset=\"" + CHARSET +"\"");
                } else {
                    sendMessage(bos, "Content-Type: text/plain; charset=\"" + CHARSET +"\"");
                }
                sendMessage(bos, "Content-Transfer-Encoding: base64");
            }

            // add an empty line represents content after
            sendMessage(bos, "");
            // TODO : data send to be optimizeed
            byte[] dataBuffer = (mail.getContent() != null ? mail.getContent() : "").getBytes();
            for (int k = 0; k < dataBuffer.length; k += 54) {
                sendMessage(bos, Base64Wrapper.encode(
                        new String(dataBuffer, k, Math.min(dataBuffer.length - k, 54))
                ));
            }

            // append attachment
            if (mail.ifNeedBoundary()) {
                RandomAccessFile attachment = null;

                dataBuffer = new byte[54];

                try {
                    for (File file : mail.getAttachments()) {
                        String fileName = file.getName();
                        attachment = new RandomAccessFile(file, "r");

                        sendMessage(bos, "--" + BOUNDARY);
                        sendMessage(bos, "Content-Type: " +
                                MimeType.getMimeType(StringUtil.getFileSuffix(fileName)) +
                                "; name=\"" + (fileName = Base64Wrapper.encode(fileName)) + "\"");
                        sendMessage(bos, "Content-Disposition: attachment; filename=\"" +
                                fileName + "\"");
                        sendMessage(bos, "Content-Transfer-Encoding: base64");

                        int len = 54;
                        while (len == 54) {
                            len = attachment.read(dataBuffer, 0, 54);
                            if (len < 0) {
                                break;
                            }

                            sendMessage(bos, Base64Wrapper.encode(new String(dataBuffer, 0, len)));
                        }
                    }
                } catch (FileNotFoundException e) {
                    log.info("error : attachment not exists");
                } catch (IOException e) {
                    log.info("error : read attachment error");
                } finally {
                    if (attachment != null) {
                        attachment.close();
                    }
                }
                // data send end
                sendMessage(bos, "--" + BOUNDARY + "--");
            }


            sendMessage(bos, ".");

            if (extractResponseCode(bis) != SmtpResponseState.REQUEST_FINISH) {
                String message = "send data error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }

            // quit
            sendMessage(bos, SmtpInstruction.QUIT);
            if (extractResponseCode(bis) != SmtpResponseState.PROCESSING) {
                String message = "quit error";
                log.info(message);
                messages.add(new SendMessage(message, 0));
                return messages;
            }
        } catch (SocketTimeoutException e) {
            log.info("error: socket connect timeout " + e.getMessage());
        } catch (IOException e) {
            log.info("error: connect error " + e.getMessage());
        } catch (Exception e) {
            log.info("error: " + e.getMessage());
        } finally {
            // close
            try {
                bis.close();
                bos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (messages.size() == 0) {
            log.info("mail send from " + mail.getFrom() + " to " + mail.getTo() + " success");
        }

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

    public static int extractResponseCode(BufferedInputStream bis) {
        byte[] buffer = new byte[1024];
        String response = null;
        int len = 0;
        try {
            len = bis.read(buffer);
        } catch (IOException e) {
            log.info("error : " + e.getMessage());
        }
        if (len <= 0) {
            return len;
        }
        response = new String(buffer, 0, len).trim();
        log.info("connect response: " + response);
        return Integer.parseInt(response.split("\\s+")[0]);
    }

    public static void sendMessage(BufferedOutputStream bos, String content) {
        byte[] bytes = (content + "\r\n").getBytes();
        try {
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
