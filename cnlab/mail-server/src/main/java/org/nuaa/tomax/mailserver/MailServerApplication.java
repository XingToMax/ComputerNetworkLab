package org.nuaa.tomax.mailserver;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.core.MailServer;
import org.nuaa.tomax.mailserver.core.SmtpServerContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class MailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServerApplication.class, args);

        MailServer server = null;

        try {
            SmtpServerContext context = new SmtpServerContext(
                    "localhost", 8081, "localhost");

            server = new MailServer(context);

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
