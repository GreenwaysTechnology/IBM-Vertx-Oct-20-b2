package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class ComibingStreams {
    public static void zipoperator(){
        Observable<Integer> intStream = Observable.just(1, 2, 3, 4);
        Observable<String> stringStream = Observable.just("a", "b", "c", "d", "e");

        //coimbine
        Observable.zip(intStream, stringStream, (i, j) -> {
            String result = i + j;
            return result;
        }).subscribe(System.out::println);
    }
    public static void coimbineMany(){
        List<String> words = Arrays.asList("the", "quick", "quick", "brown", "fox", "apple", "fox", "jumped", "over", "the", "lazy", "dog");
        Observable<String> manyLetters = Observable
                .fromIterable(words)
                .flatMap(word -> {
                    return Observable.just(word);
                })
                .distinct()
                .sorted();
        manyLetters.subscribe(System.out::println);
    }
    public static void main(String[] args) {
        coimbineMany();
    }
}
