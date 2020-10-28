package com.ibm.reactive.core;

import io.reactivex.rxjava3.core.Observable;

public class PublisherSubscriberMain {
    public static void main(String[] args) {
        //create publihser
        Observable<String> stream = Observable.create(subscriber -> {
            //push data to subscriber ; you can send data,error,complete
            subscriber.onNext("Hello");  //---> will trigger - data event
            subscriber.onNext("Hai");
            try {
                String somedata = null;
                if (somedata == null) {
                    throw new RuntimeException("Something went wrong");
                }
            } catch (Throwable throwable) {
                subscriber.onError(throwable); // --will trigger error event, channel will be closed
            }
            subscriber.onComplete();  // -- will trigger - complete event
            subscriber.onNext("foo"); // will not emit this data because channel already closed.
        });

        //subscription with subscriber ; listens for data,error,complete
        stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("done"));
    }
}
