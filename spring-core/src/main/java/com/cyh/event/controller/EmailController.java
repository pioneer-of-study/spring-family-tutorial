package com.cyh.event.controller;

import com.cyh.event.email.EmailEvent;
import com.cyh.event.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/sendEmail")
    public void sendEmail() {
        EmailEvent emailEvent = new EmailEvent("source");
        emailEvent.setAddress("my address");
        emailEvent.setText("hello world");
        emailService.sendEmail(emailEvent);
    }
}
