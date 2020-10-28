package com.ibm.reactive.concurrency;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

class StreamProcessor {
    public static void processStream() {
        Observable.range(1, 10)
                .map(i -> {
                    System.out.println("First Map is running on " + Thread.currentThread().getName());
                    return i;
                })
                .observeOn(Schedulers.computation())
                .flatMap(j -> {
                    System.out.println("flat map is running on " + Thread.currentThread().getName());
                    return Observable.just('A', 'B');
                })
                .observeOn(Schedulers.newThread())
                .map(k -> {
                    System.out.println("second Map is running on " + Thread.currentThread().getName());
                    return k.toString();
                })
                .subscribeOn(Schedulers.io())
                .subscribe(System.out::println, System.out::println, () -> System.out.println("onComplete"));

    }
}

public class ConcurrencyStream {
    public static void main(String[] args) {
        StreamProcessor.processStream();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
