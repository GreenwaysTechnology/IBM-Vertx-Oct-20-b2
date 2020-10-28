package com.ibm.rx;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

class UserService {
    public Observable<Long> getFavorites() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS);
    }
}

public class MyReactive {
    public static void main(String[] args) {

        new UserService().getFavorites()
                .timeout(500, TimeUnit.MILLISECONDS)
                .retry(5)
                .onErrorReturnItem(Long.valueOf(0))
                .doOnNext(System.out::println)
                .doOnError(System.out::println)
                .blockingLast();
    }
}

