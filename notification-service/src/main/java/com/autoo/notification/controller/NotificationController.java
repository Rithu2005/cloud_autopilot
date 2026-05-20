package com.autoo.notification.controller;

import com.autoo.notification.service.EmailService;
import com.autoo.notification.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SlackService slackService;

    @PostMapping("/email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully";
    }

    @PostMapping("/slack")
    public String sendSlack(@RequestParam String message) {
        slackService.sendSlackMessage(message);
        return "Slack message sent successfully";
    }
}
