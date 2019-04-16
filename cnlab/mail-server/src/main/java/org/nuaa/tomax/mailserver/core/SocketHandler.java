package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.utils.MailSendUtil;

import java.io.*;
import java.net.Socket;

/**
 * @Name: SocketHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 22:57
 * @Version: 1.0
 */
@Log
public class SocketHandler implements Runnable{
    private Socket socket;
    private SmtpServerContext context;

    public SocketHandler(Socket socket, SmtpServerContext context) {
        this.socket = socket;
        this.context = context;
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
                    ConstState.CONNECT_SUCCESS + " from " + context.getSmtp());

            // read instruction
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

    public boolean handleHello(BufferedInputStream bis) {
        return false;
    }

    public boolean handleAuth(BufferedInputStream bis) {
        return false;
    }

    public boolean handleAuthUsername(BufferedInputStream bis) {
        return false;
    }

    public boolean handleAuthPassword(BufferedInputStream bis) {
        return false;
    }

    public boolean handleFrom(BufferedInputStream bis) {
        return false;
    }

    public boolean handleRcpt(BufferedInputStream bis) {
        return false;
    }

    public boolean handleData(BufferedInputStream bis) {
        return false;
    }

    public boolean handleReset(BufferedInputStream bis) {
        return false;
    }

    public boolean handleQuit(BufferedInputStream bis) {
        return false;
    }

    public boolean localSmtpStorage() {
        return false;
    }

    public boolean forwardMail() {
        return false;
    }
}
