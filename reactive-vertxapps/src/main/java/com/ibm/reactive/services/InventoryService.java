package com.ibm.reactive.services;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class InventoryService {

    public Flowable<Long> getOrders() {
        //database calls --get data then stream it.... while streaming observe it
        return Flowable.interval(1000, TimeUnit.MILLISECONDS)
                .map(i -> i * 3)
                .subscribeOn(Schedulers.io())
                .filter(i -> i % 2 == 0);
    }
}
