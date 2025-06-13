package com.expense.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpMail(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("gautamashastry@gmail.com");
            message.setTo(email);
            message.setSubject("Expense Tracker OTP");
            message.setText("Your OTP is: " + otp + "\n\nThank you for using Expense Tracker. This code will expire in 2 minutes.");
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error sending mail");
            e.printStackTrace();
        }
    }
}
