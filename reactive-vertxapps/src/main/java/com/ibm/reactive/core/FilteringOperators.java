package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

public class FilteringOperators {
    public static void main(String[] args) {
        Observable
                .range(1, 100) // source /up stream
                .filter(i -> i % 2 == 0) //down / up stream
                .map(j -> j * 2) // down // up stream
                .subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }
}
