package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

class LotteryService {

    public Observable<Integer> getLotteries() {
//        Observable<Integer> numbersStream = Observable
//                .range(1, 100)
//                .map(i -> {
//                    System.out.println("map is called for " + i);
//                    return i * 2;
//                });
//        return numbersStream;
        return Observable
                .range(1, 100)
                .map(i -> {
                    System.out.println("map is called for " + i);
                    return i * 2;
                });
    }
}


public class DataProcessing {

    public static void transform() {
        //code refactoring: using fluent pattern; builder pattern
        Observable<Integer> numbersStream = Observable
                .range(1, 100)
                .map(i -> {
                    System.out.println("map is called for " + i);
                    return i * 2;
                });
        numbersStream.subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    public static void upAnddownStream() {
        Observable.range(1, 100)
                .map(i -> {
                    System.out.println("map-i is called for " + i);
                    return i * 2;
                })
                .map(j -> {
                    System.out.println("map-j is called for " + j);
                    return j * 10;
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    public static void main(String[] args) {
        // transform();
        LotteryService lotteryService = new LotteryService();
       // lotteryService.getLotteries().subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
        upAnddownStream();
    }
}
