package com.cursosdedesarrollo.reactivestreams;

import java.util.concurrent.Flow;

public class MySubscriber implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("onSubscribe: " + subscription);
        this.subscription = subscription;
        // solicita los primeros elementos
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.println("item: " + item);
        // solicita el siguiente elemento a procesar
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("error: " + throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
