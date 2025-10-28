package com.cursosdedesarrollo.reactivestreams.ejercicios;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Main {
    static void main(String[] args) throws InterruptedException {
        // Crear una instancia de StringPublisher y StringSubscriber
        StringPublisher publisher = new StringPublisher();
        StringSubscriber subscriber = new StringSubscriber();

        // Suscribir el StringSubscriber al StringPublisher
        publisher.subscribe(subscriber);

        // Publicar algunos datos en el StringPublisher
        publisher.submit("dato1");
        publisher.submit("dato2");
        publisher.submit("dato3");
        publisher.submit("error");
        publisher.submit("dato4");
        publisher.close();

        // Crear un publicador
        SubmissionPublisher<String> publisher2 = new SubmissionPublisher<>();

        // Crear un procesador
        StringProcessor<String, String> processor = new StringProcessor<>();

        // Suscribir el procesador al publicador
        publisher2.subscribe(processor);

        // Crear un suscriptor para el procesador
        Flow.Subscriber<String> subscriber2 = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
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
        processor.subscribe(subscriber2);

        // Publicar algunos elementos
        publisher2.submit("hello");
        publisher2.submit("world");

        // Cerrar el publicador (esto activará el onComplete en el suscriptor)
        publisher2.close();

        // Esperar un poco para que se procesen todos los elementos
        Thread.sleep(1000);

    }
}
