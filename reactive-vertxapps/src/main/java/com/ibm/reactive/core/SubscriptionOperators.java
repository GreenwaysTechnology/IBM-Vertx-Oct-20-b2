package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SubscriptionOperators {
    public static void main(String[] args) {
        Observable
                .range(1, 10)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("done"));

        Observable
                .range(1, 10)
                .doOnSubscribe(subscription -> System.out.println("Subscribed"))
                .doOnNext(System.out::println)
                .doOnError(System.out::println)
                .doOnComplete(() -> System.out.println("done"))
                .subscribe();

        ////////////////////////////////////////////////////////////////////////////
        //i want to emit each item based on timer; like emit every item after 1 sec/min/ms/day
        //value emission is going to be number.
        Observable
                .interval(1000, TimeUnit.MILLISECONDS)
                .doOnSubscribe(subscription -> System.out.println("interval subscription"))
                .doOnNext(System.out::println)
                .doOnError(System.out::println)
                .doOnComplete(() -> System.out.println("done"))
                .blockingLast();

        //pause main thread for some time; so that i can see data emitted by interval

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException exception) {
//            exception.printStackTrace();
//        }


    }
}
