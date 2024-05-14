package com.cursosdedesarrollo.reactivestreams.ejercicios;

import java.util.concurrent.Flow;

// Implementación de la clase StringPublisher que implementa la interfaz Flow.Publisher
class StringPublisher implements Flow.Publisher<String> {
    private Flow.Subscriber<? super String> subscriber;

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        subscriber.onSubscribe(new StringSubscription());
    }

    // Método para enviar datos al suscriptor
    public void submit(String data) {
        subscriber.onNext(data);
    }

    // Clase interna que implementa la interfaz Flow.Subscription
    private class StringSubscription implements Flow.Subscription {
        @Override
        public void request(long n) {
            // No se implementa ya que se enviarán datos continuamente
        }

        @Override
        public void cancel() {
            // No se implementa ya que la cancelación no es relevante en este ejemplo
        }
    }
}

