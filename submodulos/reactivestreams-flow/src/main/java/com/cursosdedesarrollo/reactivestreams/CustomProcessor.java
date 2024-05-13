package com.cursosdedesarrollo.reactivestreams;

import java.util.concurrent.Flow.*;

class CustomProcessor<T, R> implements Processor<T, R> {

    private Subscriber<? super R> subscriber;
    private Subscription subscription;

    @Override
    public void subscribe(Subscriber<? super R> subscriber) {
        this.subscriber = subscriber;
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                // No hacemos nada con esto aquí
            }

            @Override
            public void cancel() {
                // Implementación de cancelación si es necesaria
            }
        });
    }

    @Override
    public void onNext(T item) {
        // Realizar algún procesamiento sobre el elemento recibido
        // En este ejemplo, simplemente convertimos a mayúsculas el elemento y lo pasamos al Subscriber
        R result = (R) item.toString().toUpperCase();
        // Pasar el resultado al Subscriber
        subscriber.onNext(result);
        // Solicitar el siguiente elemento
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        // Manejar el error, por ejemplo, pasándolo al Subscriber
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        // El procesamiento se completó con éxito, notificar al Subscriber
        subscriber.onComplete();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // Solicitar al menos un elemento
    }
}