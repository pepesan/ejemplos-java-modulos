# reactor — Project Reactor

**Project Reactor (librería externa)**

## Qué cubre

Programación reactiva con Project Reactor, la implementación de Reactive Streams usada por Spring WebFlux. Cubre `Mono` (0-1 elementos) y `Flux` (0-N elementos).

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| `Mono.just` | Observable de un único elemento |
| `Flux.just`, `Flux.fromIterable` | Observable de múltiples elementos |
| `delayElement`, `delaySubscription` | Introducción de latencia asíncrona |
| `flatMap` + `distinct` + `sort` | Pipeline de transformación de letras |
| `concatWith` | Concatenación de dos Flux en secuencia |
| `zipWith` | Combina elementos con un índice |
| `Flux.first` | Emite el Flux que responda primero (race) |
| `toStream` / `toIterable` | Materialización bloqueante del Flux |

## Clase de test

`com.cursosdedesarrollo.app.ReactorSnippetsTest`
