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
    public static void sendMessage(BufferedOutputStream bos, String message) {
        try {
            bos.write((message + "\r\n").getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractMessage(BufferedInputStream bis) {
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int len = -1;
        try {
            while ((len = bis.read(buffer)) > 0) {
                builder.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            log.info("extract message from input stream error");
        }

        return builder.toString().trim();
    }

    public static String extractIpFromSocket(Socket socket) {
        // TODO : extract ip from socket
        return "";
    }
}
