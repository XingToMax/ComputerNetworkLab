package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.service.DatabaseService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Name: SmtpServerContext
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 23:20
 * @Version: 1.0
 */
@Log
@Component(value = "smtpServerContext")
public class SmtpServerContext {
    private final String smtp;
    private final int port;
    private final String host;

    private final DataHandlerFactory dataHandlerFactory;
    private final DatabaseService databaseService;

    public SmtpServerContext(Environment environment, DataHandlerFactory dataHandlerFactory, DatabaseService databaseService) {
        this.smtp = environment.getProperty("mail.server.smtp", "");
        this.port = Integer.parseInt(environment.getProperty("mail.server.port", ""));
        this.host = environment.getProperty("mail.server.host", "");
        this.dataHandlerFactory = dataHandlerFactory;
        this.databaseService = databaseService;
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

    public DataHandlerFactory getDataHandlerFactory() {
        return this.dataHandlerFactory;
    }

    public DatabaseService getDatabaseService() {
        return this.databaseService;
    }
}
