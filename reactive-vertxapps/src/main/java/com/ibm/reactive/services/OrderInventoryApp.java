package com.ibm.reactive.services;

public class OrderInventoryApp {
    public static void main(String[] args) {
        OrderService orderService = new OrderService(new InventoryService());
        orderService.processOrder();
    }
}
