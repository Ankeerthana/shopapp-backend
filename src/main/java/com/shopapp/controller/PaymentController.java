package com.shopapp.controller;

import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    @Value("${razorpay.key.id}")     private String keyId;
    @Value("${razorpay.key.secret}") private String keySecret;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        try {
            int amount = (int)(Double.parseDouble(
                    body.get("amount").toString()) * 100);
            RazorpayClient client = new RazorpayClient(keyId, keySecret);
            JSONObject opts = new JSONObject();
            opts.put("amount",   amount);
            opts.put("currency", "INR");
            opts.put("receipt",  "order_" + System.currentTimeMillis());
            com.razorpay.Order order = client.orders.create(opts);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId",  order.get("id"));
            response.put("amount",   amount);
            response.put("currency", "INR");
            response.put("keyId",    keyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}