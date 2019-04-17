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
    private final String host;


    public SmtpServerContext(String smtp, int port, String host) {
        this.smtp = smtp;
        this.port = port;
        this.host = host;
    }

    public String getSmtp() {
        return this.smtp;
    }

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }
}
