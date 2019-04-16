package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;

/**
 * @Name: SmtpServerContext
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 23:20
 * @Version: 1.0
 */
@Log
public class SmtpServerContext {
    private final String smtp;
    private final int port;


    public SmtpServerContext(String smtp, int port) {
        this.smtp = smtp;
        this.port = port;
    }

    public String getSmtp() {
        return this.smtp;
    }

    public int getPort() {
        return this.port;
    }
}
