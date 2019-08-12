package com.danny.jba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
    private String smtpSocketFactoryPort;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
    private String smtpSocketFactoryClass;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
    private String smtpSocketFactoryFallback;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String smtpStartTlsEnabled;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.socketFactory.port", smtpSocketFactoryPort);
        props.put("mail.smtp.socketFactory.class", smtpSocketFactoryClass);
        props.put("mail.smtp.socketFactory.fallback", smtpSocketFactoryFallback);
        props.put("mail.smtp.starttls.enable", smtpStartTlsEnabled);
        props.put("mail.debug", "true");

        return mailSender;
    }

}