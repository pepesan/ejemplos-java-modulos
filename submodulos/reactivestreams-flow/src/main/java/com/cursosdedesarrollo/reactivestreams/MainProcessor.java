package com.cursosdedesarrollo.reactivestreams;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.*;

public class MainProcessor {
    public static void main(String[] args) throws InterruptedException {
        // Crear un publicador
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Crear un procesador
        CustomProcessor<String, String> processor = new CustomProcessor<>();

        // Suscribir el procesador al publicador
        publisher.subscribe(processor);

        // Crear un suscriptor para el procesador
        Subscriber<String> subscriber = new Subscriber<>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // Solicitar al menos un elemento
            }

            @Override
            public void onNext(String item) {
                System.out.println("Elemento recibido: " + item);
                subscription.request(1); // Solicitar el siguiente elemento
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Procesamiento completo");
            }
        };

        // Suscribir el suscriptor al procesador
        processor.subscribe(subscriber);

        // Publicar algunos elementos
        publisher.submit("hello");
        publisher.submit("world");

        // Cerrar el publicador (esto activará el onComplete en el suscriptor)
        publisher.close();

        // Esperar un poco para que se procesen todos los elementos
        Thread.sleep(1000);
    }
}
