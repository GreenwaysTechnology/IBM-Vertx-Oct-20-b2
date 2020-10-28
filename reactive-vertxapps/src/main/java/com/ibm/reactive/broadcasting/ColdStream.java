package com.ibm.reactive.broadcasting;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ColdStream {

    //unsubscription
    public static void unsubscribe() throws InterruptedException {
        //one to many ; cold stream;
        Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);

        myObservable.subscribe(item -> System.out.println("Observer 1: " + item));

        //after 3scs new subscriber joins
        Thread.sleep(3000);

        //unsubscription handle
        Disposable subscriber2 = myObservable
                .doOnSubscribe((r) -> System.out.println("Observer 2 Joining"))
                .doFinally(() -> System.out.println("Observer 2 left"))
                .subscribe(item -> System.out.println("Observer 2: " + item));

        Thread.sleep(5000);
        subscriber2.dispose();
        Thread.sleep(8000);
    }


    public static void coldStream() throws InterruptedException {
        //one publisher to many subscriber; using cold stream.
        Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);
        //the 1st subscriber
        myObservable.subscribe(item -> System.out.println("Subscriber 1: " + item));
        Thread.sleep(3000);
        //after 3 ms new subscriber joins
        myObservable
                .doOnSubscribe((r) -> System.out.println("Subscriber 2 Joining"))
                .doOnNext(item -> System.out.println("Subscriber 2: " + item))
              //  .subscribe(item -> System.out.println("Subscriber 2: " + item));
                .blockingLast();
       // Thread.sleep(5000);

    }

    public static void main(String[] args) throws  InterruptedException {
       // coldStream();
        unsubscribe();
    }
}
