package com.acme.demo;

public class FooService {
    public String processOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId is required");
        }
        return "OK-" + orderId;
    }
}
