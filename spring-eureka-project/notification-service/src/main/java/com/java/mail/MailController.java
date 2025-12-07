package com.java.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/user-activated")
    public ResponseEntity<?> sendUserActivatedMail(@RequestBody UserActivationMailRequest request) {
        mailService.sendUserActivatedMail(request);
        return ResponseEntity.ok("Mail sent");
    }
}
