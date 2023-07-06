package com.example.shoppApp.controller;

import com.example.shoppApp.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    final IEmailService emailService;

    @GetMapping("/send-email")
    @Scheduled(cron = "0 15 0,12 * * ?")
    public String sendEmail() {
        try {
            emailService.sendEmail();
            return "send email successfully!";
        } catch (Exception e) {
//            log.error("Can not send email: {}", e.getMessage());
            e.printStackTrace();
            return "Can not send email!";
        }
    }
}
