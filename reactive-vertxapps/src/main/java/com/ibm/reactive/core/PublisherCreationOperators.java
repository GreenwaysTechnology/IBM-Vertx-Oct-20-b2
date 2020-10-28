package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class PublisherCreationOperators {

    //publisher using just
    public static void justOperator() {
        Observable<Integer> stream = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //subscription
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    //publisher using array ds.
    public static void arrayOperator() {
        Integer[] items = {1, 2, 3, 4, 5, 6};
        Observable<Integer> stream = Observable.fromArray(items);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("done"));

    }

    //publisher using List ds.
    public static void listOperator() {
        Integer[] items = {1, 2, 3, 4, 5, 6};
        List<Integer> list = Arrays.asList(items);
        Observable<Integer> stream = Observable.fromIterable(list);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    //if i want sequence of 1000 numbers
    public static void rangeOperator() {
        Observable<Integer> stream = Observable.range(1, 1000);
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    public static void main(String[] args) {
        justOperator();
        arrayOperator();
        listOperator();
        rangeOperator();

    }
}
