package com.ibm.reactive.sterotypes;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PublisherTypes {
    //observable ;  0 - n items
    public static void getObservable() {
        Observable.just(10, 20).subscribe(System.out::println);
    }
    //

    public static void singleType() {
        //only one item , not more than that
        Single.create(subscriber -> {
            subscriber.onSuccess("Hello");
            subscriber.onSuccess("Hai");
        }).subscribe(System.out::println);
        Single.just(10).subscribe(System.out::println);
    }
    public static void maybeType() {
        //only item
        Maybe.just(1).subscribe(System.out::println);
        //only error
        Maybe.error(new RuntimeException("error")).subscribe(System.out::println, System.out::println);
        //only complete
        Maybe.empty().subscribe(System.out::println, System.out::println, () -> System.out.println("onComplete"));

    }
    public static void completeTest() {
        Completable.complete().subscribe(() -> System.out.println("Completeable"));
    }
    public static void main(String[] args) {
        getObservable();
        singleType();
        maybeType();
        completeTest();
    }
}
