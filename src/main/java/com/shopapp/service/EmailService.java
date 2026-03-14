package com.shopapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service @RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOrderConfirmation(String to, String orderId, double amount) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(msg, true);
            h.setTo(to);
            h.setSubject("Order Confirmed! #" + orderId);
            h.setText(
                    "<h2>Thank you for your order!</h2>" +
                            "<p>Order ID: <strong>" + orderId + "</strong></p>" +
                            "<p>Total: <strong>Rs." + amount + "</strong></p>" +
                            "<p>Your order will be shipped soon.</p>",
                    true
            );
            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("Email error: " + e.getMessage());
        }
    }
}