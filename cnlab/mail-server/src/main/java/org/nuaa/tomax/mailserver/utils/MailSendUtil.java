package org.nuaa.tomax.mailserver.utils;

import lombok.extern.java.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Name: MailSendUtil
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 23:06
 * @Version: 1.0
 */
@Log
public class MailSendUtil {
    /**
     * use output stream to send data and append \r\n after
     * @param bos output stream
     * @param message data message
     */
    public static void sendMessage(BufferedOutputStream bos, String message) {
        try {
            bos.write((message + "\r\n").getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * extract data from input stream
     * @param bis input stream
     * @return data
     */
    public static String extractMessage(BufferedInputStream bis) {
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int len = -1;
        try {
            len = bis.read(buffer);
            builder.append(new String(buffer, 0, len));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msg = builder.toString().trim();
        log.info("msg : " + msg + "(" + msg.length() + ")");
        return msg;
    }

    public static String extractIpFromSocket(Socket socket) {
        // TODO : extract ip from socket
        return "";
    }

    public static int getResponseCode(BufferedInputStream bis) {
        return Integer.parseInt(extractMessage(bis).split("\\s+")[0]);
    }
}
