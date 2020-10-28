package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

public class FlatMapOperator {
    public static void main(String[] args) {
        Observable.just("A", "B", "C")
                .map(String::toLowerCase)
                .subscribe(
                        System.out::println, System.out::println, () -> {
                            System.out.println("done");
                        }
                );
        //flat map. i want to return [1,2,3] for each item
        Observable.just("A", "B", "C")
                .flatMap(i -> {
                    System.out.println("item " + i);
                    return Observable.just(1, 2, 3);
                })
                .subscribe(
                        System.out::println, System.out::println, () -> {
                            System.out.println("done");
                        }
                );
    }
}
