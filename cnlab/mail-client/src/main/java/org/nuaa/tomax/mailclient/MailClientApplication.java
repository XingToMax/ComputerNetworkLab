package org.nuaa.tomax.mailclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MailClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(MailClientApplication.class, args);
	}
}
