package com.cursosdedesarrollo.reactivestreams.ejercicios;

import java.util.concurrent.Flow;

// Implementación de la clase StringSubscriber que implementa la interfaz Flow.Subscriber
class StringSubscriber implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1); // Solicitar un elemento para empezar
    }

    @Override
    public void onNext(String item) {
        // Modificar los datos antes de imprimirlos
        String modifiedItem = item.toUpperCase(); // Convertir a mayúsculas
        System.out.println("Subscrition: onNext: " +modifiedItem);
        subscription.request(1); // Solicitar el siguiente elemento
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Se produjo un error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("La suscripción se ha completado.");
    }
}
