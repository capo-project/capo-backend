package com.realworld.v1.global.config;//package com.realworld.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//    @Bean
//    public JavaMailSender javaMailService(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost("smtp.naver.com");
//        javaMailSender.setUsername("hans9839@naver.com");
//        javaMailSender.setPassword("qwer54953");
//        javaMailSender.setPort(465);
//
//        javaMailSender.setJavaMailProperties(getMailProperties());
//        return javaMailSender;
//    }
//
//    private Properties getMailProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.debug", "true");
//        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com");
//        properties.setProperty("mail.smtp.enable", "true");
//        return properties;
//    }
//}
