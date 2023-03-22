package com.cursosdedesarrollo.app;

/*
Ref: https://www.infoq.com/articles/reactor-by-example/
 */

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ReactorSnippetsTest {

    private static List<String> words = Arrays.asList(
            "the", // [t,h,e]
            "quick", // [q,u,i,c,k]
            "brown",
            "fox",
            "jumped",
            "overs",
            "the",
            "lazy",
            "dog"
    );

    @Test
    public void testTexto() {
        Mono<String> mensaje=Mono.just("hola");
        mensaje.subscribe(System.out::println);
    }

    @Test
    public void testObjeto() {


        Mono<Persona> mensaje=Mono.just(new Persona("David","Vaquero",44));

        mensaje.subscribe(System.out::println);

    }

    @Test
    public void testAsincrono() throws InterruptedException {


        Mono<Persona> mensaje=Mono.just(new Persona("pepe","perez",20)).delayElement(Duration.ofSeconds(2));

        mensaje.subscribe(System.out::println);

        Thread.sleep(3000);

    }

    @Test
    public void testLista() throws InterruptedException {


        Flux<String> mensaje=Flux.just("hola" ,"que" ,"tal","estas","tu").delayElements(Duration.ofSeconds(1));

        mensaje.subscribe(System.out::println);

        Thread.sleep(10000);

    }
    @Test
    public void simpleCreation() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);

        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);
    }

    @Test
    public void twoFluxInOne() throws InterruptedException {
        Flux<String> mensajes1=Flux.just("hola" ,"que" ,"tal","estas","tu").delayElements(Duration.ofSeconds(1));
        Flux<String> mensajes2=Flux.just("hola2" ,"que2" ,"tal2","estas2","tu2").delayElements(Duration.ofSeconds(1));
        mensajes1
                .concatWith(mensajes2)
                .subscribe(System.out::println);

        Thread.sleep(15000);
    }
    @Test
    public void findingMissingLetter() {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(System.out::println);
    }
    @Test
    public void restoringMissingLetter() {
        Mono<String> missing = Mono.just("s");
        Flux<String> allLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(missing)
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        allLetters.subscribe(System.out::println);
    }

    @Test
    public void shortCircuit() {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                    .concatWith(Mono.just("world")
                    .delaySubscription(Duration.ofMillis(500)));

        helloPauseWorld.subscribe(System.out::println);
    }

    @Test
    public void blocks() {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                    .concatWith(Mono.just("world")
                    .delaySubscription(Duration.ofMillis(500)));

        helloPauseWorld.toStream()
                .forEach(System.out::println);
    }

    @Test
    public void firstEmitting() {
        Mono<String> a = Mono.just("oops I'm late")
                .delaySubscription(Duration.ofMillis(500));
        Flux<String> b = Flux.just("let's get", "the party", "started")
                .delaySubscription(Duration.ofMillis(200));

        Flux.first(a, b)
                .toIterable()
                .forEach(System.out::println);
    }

}
