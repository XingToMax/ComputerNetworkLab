package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.constant.SmtpInstruction;
import org.nuaa.tomax.mailserver.constant.SmtpResponseState;
import org.nuaa.tomax.mailserver.entity.MailEntity;
import org.nuaa.tomax.mailserver.utils.Base64Wrapper;
import org.nuaa.tomax.mailserver.utils.MailSendUtil;
import org.nuaa.tomax.mailserver.utils.StringUtil;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name: SocketHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 22:57
 * @Version: 1.0
 */
@Log
public class SocketHandler implements Runnable{

    /**
     * 接收本地客户端邮件
     */
    private final static int LOCAL_RECEIVE_MODE = 1;
    /**
     * 接收其他smtp服务器的邮件
     */
    private final static int REMOTE_RECEIVE_MODE = 2;
    /**
     * 向其他邮件服务器转发
     */
    private final static int REMOTE_SEND_MODE = 3;

    /**
     * 处理该mode的handler的名称映射
     */
    private final static Map<Integer, String> MODE_TO_NAME = new HashMap<Integer, String>() {{
        // 对应将数据发送到本地(即保存到本地)
        put(LOCAL_RECEIVE_MODE, "localSend");
        // 对应其他服务器发送来的，需要保存到本地
        put(REMOTE_RECEIVE_MODE, "localReceive");
        // 对应要将数据发送到其他服务器
        put(REMOTE_SEND_MODE, "forward");
    }};

    /**
     * 未登录
     */
    private final static int NOT_LOGIN_MODE = -1;

    private Socket socket;
    private SmtpServerContext context;
    private MailBean mail;

    public SocketHandler(Socket socket, SmtpServerContext context) {
        this.socket = socket;
        this.context = context;

        mail = new MailBean();
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());

            // reply
            MailSendUtil.sendMessage(bos,
                    SmtpResponseState.CONNECT_SUCCESS + " from " + context.getSmtp());

            // read instruction, process from helo and success to next
            handleHello(bis, bos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean handleHello(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);

        String[] parts = msg.split("\\s+");
        if (parts.length >= 2 && parts[0].equalsIgnoreCase(SmtpInstruction.HELLO)) {
            // TODO : check service available, if cannot apply message below
            // MailSendUtil.sendMessage(bos, SmtpResponseState.SERVICE_NOT_AVAILABLE + " Service not available");
            mail.setHostName(parts[1]);

            MailSendUtil.sendMessage(bos, SmtpResponseState.REQUEST_FINISH + " OK");
            log.info("helo from " + mail.getHostName() + " success");
            // continue next step
            return handleAuthOrFrom(bis, bos);
        }

        // cannot recognize instruction
        MailSendUtil.sendMessage(bos,
                SmtpResponseState.INSTRUCTION_ERROR + " invalid command " + msg);
        log.info("helo error");
        return false;
    }

    private boolean handleAuthOrFrom(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);
        if (StringUtil.startsIgnoreCaseWith(msg, SmtpInstruction.AUTH)) {
            log.info("auth");
            return handleAuth(bis, bos, msg);
        } else {
            log.info("from");
            return handleFrom(bis, bos, msg);
        }
    }

    private boolean handleAuth(BufferedInputStream bis, BufferedOutputStream bos, String msg) {
        // check auth
        MailSendUtil.sendMessage(bos, SmtpResponseState.AUTH_RESPONSE  + " " + Base64Wrapper.encode("username"));
        return handleAuthUsername(bis, bos);
    }

    private boolean handleAuthUsername(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);
        String username = Base64Wrapper.decode(msg);
        // TODO : check username exists
        mail.setUser(username);
        MailSendUtil.sendMessage(bos, SmtpResponseState.AUTH_RESPONSE + " " + Base64Wrapper.encode("password"));
        return handleAuthPassword(bis, bos);
    }

    private boolean handleAuthPassword(BufferedInputStream bis, BufferedOutputStream bos) {
        String password = MailSendUtil.extractMessage(bis);
        // TODO : check password
        MailSendUtil.sendMessage(bos, SmtpResponseState.AUTH_ACCESS + " OK");
        // update mode to login success mode
        mail.setMode(LOCAL_RECEIVE_MODE);
        return handleFrom(bis, bos, MailSendUtil.extractMessage(bis));
    }

    private boolean handleFrom(BufferedInputStream bis, BufferedOutputStream bos, String msg) {
        if (msg.contains(":")) {
            String[] parts = msg.split(":");
            if (parts[0].trim().equalsIgnoreCase(SmtpInstruction.MAIL_FROM) &&
                    parts.length > 1 &&
                    parts[1].trim().startsWith("<") &&
                    parts[1].trim().endsWith(">")) {
                String from = parts[1].trim().substring(1, parts[1].trim().length() - 1);
                // TODO : check from pattern whether is mail or not
                String[] mailParts = from.split("@");
                // user name not match before
                if (mail.getUser() != null && !mailParts[0].equals(mail.getUser())) {
                    log.info("from param is not matching auth login name");
                    MailSendUtil.sendMessage(bos, SmtpResponseState.PARAM_CONFLICT + " Mail from must equal authorized user");
                    return false;
                }

                // check mode, if not current host represents host from other smtp server
                if (!mailParts[1].equals(context.getHost())) {
                    mail.setMode(REMOTE_RECEIVE_MODE);
                } else if (mail.getMode() == NOT_LOGIN_MODE) {
                    // client belongs to current server, but not login
                    log.info("not login and wants to forward by current server");
                    MailSendUtil.sendMessage(bos, SmtpResponseState.BAD_SEQUENCE + " bad sequence of commands");
                    return false;
                }

                mail.setFrom(from);

                MailSendUtil.sendMessage(bos, SmtpResponseState.REQUEST_FINISH + " OK");

                return handleRcpt(bis, bos);
            }
        }
        log.info(SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        MailSendUtil.sendMessage(bos,
                SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        return false;
    }

    private boolean handleRcpt(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);
        if (msg.contains(":")) {
            String[] parts = msg.split(":");
            if (parts[0].equalsIgnoreCase(SmtpInstruction.MAIL_TO) &&
                    parts.length > 1 &&
                    parts[1].trim().startsWith("<") &&
                    parts[1].trim().endsWith(">")) {
                String to = parts[1].trim().substring(1, parts[1].trim().length() - 1);
                // TODO : check from pattern whether is mail or not
                String[] mailParts = to.split("@");

                // remote recieve mode need rcpt to address host is current host
                if (mail.getMode() == REMOTE_RECEIVE_MODE && !mailParts[1].equals(context.getHost())) {
                    log.info("unknown rcpt host");
                    MailSendUtil.sendMessage(bos, SmtpResponseState.COMMAND_UN_EXECUTABLE + " unknown rcpt host");
                    return false;
                }

                // to other smtp, need forward
                if (!mailParts[1].equals(context.getHost())) {
                    mail.setMode(REMOTE_SEND_MODE);
                }

                mail.setTo(to);
                // TODO : check user
                MailSendUtil.sendMessage(bos, SmtpResponseState.REQUEST_FINISH + " OK");

                return handleData(bis, bos);
            }
        }
        log.info(SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        MailSendUtil.sendMessage(bos,
                SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        return false;

    }

    private boolean handleData(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);
        if (StringUtil.startsIgnoreCaseWith(msg, SmtpInstruction.DATA)) {
            // TODO : init data receive task
            MailSendUtil.sendMessage(bos, SmtpResponseState.START_SEND + " End data with <CR><LF>.<CR><LF>");
            // begin receive data task
            String data = readData(bis, bos);
            if (data == null) {
                return false;
            }

            // TODO : check data
            mail.setData(data);
            // forward or store in local
            boolean result = context.getDataHandlerFactory().handle(
                    MODE_TO_NAME.get(mail.getMode()), mail
            );
            if (result) {
                // store data
                context.getDatabaseService().saveMail(mail.toMail());
                log.info("store data success");
            } else {
                log.info("data handle error");
            }

            return handleQuit(bis, bos);

        }
        log.info(SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        MailSendUtil.sendMessage(bos,
                SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        return false;
    }

    private boolean handleReset(BufferedInputStream bis, BufferedOutputStream bos) {
        return false;
    }

    private boolean handleQuit(BufferedInputStream bis, BufferedOutputStream bos) {
        String msg = MailSendUtil.extractMessage(bis);
        if (StringUtil.startsIgnoreCaseWith(msg, SmtpInstruction.QUIT)) {
            MailSendUtil.sendMessage(bos, SmtpResponseState.PROCESSING + " QUIT");
            return true;
        }
        log.info(SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        MailSendUtil.sendMessage(bos,
                SmtpResponseState.INSTRUCTION_ERROR + " cannot recognize command " + msg);
        return false;
    }

    private String readData(BufferedInputStream bis, BufferedOutputStream bos) {
        StringBuilder data = new StringBuilder();
        byte[] buffer = new byte[1024];
        // count data length, avoid data overflow
        int countLen = 0;
        int len = 0;
        try {
            while ((len = bis.read(buffer)) > 0) {
                countLen += len;

                // TODO : check data overflow (use count data)

                String msg = new String(buffer, 0, len);
                data.append(msg);
                if (msg.trim().endsWith(".")) {
                    parseData(data.toString());
                    MailSendUtil.sendMessage(bos, SmtpResponseState.REQUEST_FINISH + " OK");
                    return data.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // data content error
        MailSendUtil.sendMessage(bos, SmtpResponseState.DATA_INVALID + " exists invalid data");
        return null;
    }

    private void parseData(String data) {
        // TODO : check data
        System.out.println(data);
    }

}
