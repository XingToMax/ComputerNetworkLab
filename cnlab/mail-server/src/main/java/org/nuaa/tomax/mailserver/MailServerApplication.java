package org.nuaa.tomax.mailserver;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.core.MailServer;
import org.nuaa.tomax.mailserver.core.SmtpServerContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Log
public class MailServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MailServerApplication.class, args);
        SmtpServerContext context = (SmtpServerContext) applicationContext.getBean("smtpServerContext");
        MailServer server = (MailServer) applicationContext.getBean("mailServer", context);

        try {
            server.start();
        } catch (Exception e) {
            log.info("error: " + e.getMessage());
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

}
