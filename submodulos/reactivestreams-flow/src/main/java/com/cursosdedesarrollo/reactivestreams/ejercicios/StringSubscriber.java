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
        try {
            // Tratamiento del dato recibido
            String itemModificado = item + "!";
            System.out.println("Dato recibido: " + itemModificado);
            // Simulamos un error
            if (item.equals("error")) {
                throw new RuntimeException("Error en el dato recibido");
            }
        } catch (RuntimeException e) {
            onError(e); // Llamamos a onError en caso de error
        }
        subscription.request(1); // Solicitamos más datos al publisher
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Se produjo un error: " + throwable.getMessage());
        // subscription.request(1); // Solicitamos más datos al publisher
    }

    @Override
    public void onComplete() {
        System.out.println("La suscripción se ha completado.");
    }
}
