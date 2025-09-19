package com.cursosdedesarrollo.reactivestreams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;


public class AppPublisherSubscriber
{
    public static void main( String[] args )
    {
        System.out.println( "Ejemplos de Streams Reactivos!" );
        ExecutorService executor = Executors.newFixedThreadPool(4);
        SubmissionPublisher<String> sb = new SubmissionPublisher<>(executor, Flow.defaultBufferSize());
        sb.subscribe(new MySubscriber());
        for (int i = 1; i < 11; i++) {
            sb.submit("item " +i);
        }
        sb.close();
        executor.shutdown();
    }
}
