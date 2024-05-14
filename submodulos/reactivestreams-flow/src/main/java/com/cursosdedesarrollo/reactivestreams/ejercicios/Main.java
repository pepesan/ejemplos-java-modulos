package com.cursosdedesarrollo.reactivestreams.ejercicios;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de StringPublisher y StringSubscriber
        StringPublisher publisher = new StringPublisher();
        StringSubscriber subscriber = new StringSubscriber();

        // Suscribir el StringSubscriber al StringPublisher
        publisher.subscribe(subscriber);

        // Publicar algunos datos en el StringPublisher
        publisher.submit("dato1");
        publisher.submit("dato2");
        publisher.submit("dato3");

    }
}
