package com.karacheban.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${application.email.recipient}")
    private String recipientEmail;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendAspectExecutionNotification(String aspectName, String details) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(recipientEmail);
            helper.setSubject("Aspect Execution Notification: " + aspectName);
            helper.setText(details, true); // HTML enabled

            emailSender.send(message);
            System.out.println("Email notification sent for aspect: " + aspectName);
        } catch (MessagingException e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
    }
}