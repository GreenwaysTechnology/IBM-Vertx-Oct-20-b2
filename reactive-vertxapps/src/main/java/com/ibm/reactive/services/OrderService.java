package com.ibm.reactive.services;

import java.util.concurrent.TimeUnit;

public class OrderService {
    private InventoryService inventoryService;

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //fallback api , which may get data from cache servers
    public long getDummyOrderFromCache() {
        return 100;
    }

    public void processOrder() {
        inventoryService.getOrders()
                .timeout(5000, TimeUnit.MILLISECONDS) // order service expect data should come within 500ms
                .onErrorReturnItem(getDummyOrderFromCache())  //fallback section
                .doOnNext(data -> {
                    System.out.println("Order Details");
                    System.out.println(data);
                })
                .doOnError(err -> {
                    System.out.println(err);
                })
                .blockingLast();
    }
}
