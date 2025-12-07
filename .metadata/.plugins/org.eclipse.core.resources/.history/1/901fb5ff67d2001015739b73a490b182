package com.java.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@appstore.com}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserActivatedMail(UserActivationMailRequest req) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(req.getEmail());
        message.setFrom(from);

        String name = (req.getFirstName() != null && !req.getFirstName().isEmpty())
                ? req.getFirstName()
                : req.getUserName();

        message.setSubject("Your App Store Account is Active");

        String body = "Hi " + name + ",\n\n"
                + "Your App Store account has been successfully activated by the admin.\n"
                + "You can now log in using your username and password.\n\n"
                + "Regards,\n"
                + "App Store Team";

        message.setText(body);

        mailSender.send(message);
    }
}
