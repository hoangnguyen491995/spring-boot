package com.example.shoppApp.service;

import javax.mail.MessagingException;

public interface IEmailService {
    void sendEmail() throws MessagingException;
}
