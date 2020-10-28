package com.ibm.reactive.backpressure;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlowableStream {
    static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Flowable.range(1, 999_999_999)
                .map(item -> new MyItem(item)) //upstream is fast enough to send data
                .observeOn(Schedulers.io())
                .subscribe(myItem -> {
                    sleep(500); //down stream/subscriber are slow
                    System.out.println("Subscriber received " +
                            myItem.id + Thread.currentThread().getName());
                });
        sleep(Long.MAX_VALUE);
    }


    static final class MyItem {
        final int id;

        MyItem(int id) {
            this.id = id;
            System.out.println("Consuming myitem " + id  +Thread.currentThread().getName());
        }
    }
}
