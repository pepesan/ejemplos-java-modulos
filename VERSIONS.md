# Novedades de Java por versión (Java 8 → 25)

Cada fila indica la novedad, el módulo y el fichero exacto donde está ejemplificada.
Las rutas son relativas a la raíz del proyecto.

---

## Java 8 (LTS) — Marzo 2014

| Novedad | Fichero(s) |
|---------|-----------|
| Lambdas y referencias a métodos | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` |
| `Function<T,R>` — `apply`, `andThen`, `compose` | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoFunction()` |
| `Predicate<T>` — `test`, `and`, `or`, `negate` | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoPredicate()` |
| `Consumer<T>` — `accept`, `andThen` | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoConsumer()` |
| `Supplier<T>` — `get`, lazy init | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoSupplier()` |
| `BiFunction<T,U,R>` | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoBiFunction()` |
| `@FunctionalInterface` propia | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — interfaces `Transformador` y `ValidadorConExcepcion` |
| Stream API (filter, map, reduce, flatMap, collect) | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Optional<T>` básico — `ofNullable`, `isPresent`, `get` | `submodulos/optional/src/main/java/com/cursosdedesarrollo/optional/App.java` |
| `java.time` — `LocalDate`, `LocalTime`, `ZonedDateTime` | `submodulos/javatime/src/main/java/org/example/App.java` |
| `DateTimeFormatter` — formateo y parseo | `submodulos/javatime/src/main/java/org/example/App.java` |
| Métodos `default` en interfaces | `submodulos/privatemethodsinterfaces/src/main/java/com/cursosdedesarrollo/privatemethodsinterfaces/Foo.java` |
| `CompletableFuture` — `supplyAsync`, `thenApply`, `thenCombine`, `whenComplete` | `submodulos/futures/src/main/java/org/example/App.java` |
| `Base64` encode/decode | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` |

---

## Java 9 — Septiembre 2017

| Novedad | Fichero(s) |
|---------|-----------|
| `module-info.java` — `exports`, `requires` | `submodulos/dao/src/main/java/module-info.java` `submodulos/hijo/src/main/java/module-info.java` `submodulos/mainapp/src/main/java/module-info.java` `submodulos/servicio/src/main/java/module-info.java` |
| `provides … with` (ServiceLoader proveedor) | `submodulos/servicio/src/main/java/module-info.java` |
| `uses` (ServiceLoader consumidor) | `submodulos/mainapp/src/main/java/module-info.java` |
| Encapsulamiento fuerte — advertencias (Java 9–15) | `submodulos/modulos-migracion/src/main/java/com/cursosdedesarrollo/migracion/App.java` — comentario de cabecera |
| `List.of`, `Set.of` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` |
| `Map.of` — hasta 10 pares | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `Map.of` |
| `Map.ofEntries` + `Map.entry` — más de 10 pares | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `Map.ofEntries` |
| `Stream.takeWhile` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Stream.dropWhile` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Stream.ofNullable` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Stream.iterate` con predicado de parada | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Collectors.filtering` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Collectors.flatMapping` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` |
| `Optional.ifPresentOrElse` | `submodulos/optional/src/main/java/com/cursosdedesarrollo/optional/App.java` |
| `Optional.or` | `submodulos/optional/src/main/java/com/cursosdedesarrollo/optional/App.java` |
| `Optional.stream()` | `submodulos/optional/src/main/java/com/cursosdedesarrollo/optional/App.java` |
| Métodos `private` en interfaces | `submodulos/privatemethodsinterfaces/src/main/java/com/cursosdedesarrollo/privatemethodsinterfaces/Foo.java` |
| Flow API — `Publisher`, `Subscriber` | `submodulos/reactivestreams-flow/src/main/java/com/cursosdedesarrollo/reactivestreams/AppPublisherSubscriber.java` `submodulos/reactivestreams-flow/src/main/java/com/cursosdedesarrollo/reactivestreams/MySubscriber.java` |
| Flow API — `Processor` | `submodulos/reactivestreams-flow/src/main/java/com/cursosdedesarrollo/reactivestreams/MainProcessor.java` `submodulos/reactivestreams-flow/src/main/java/com/cursosdedesarrollo/reactivestreams/CustomProcessor.java` |
| `System.LoggerFinder` personalizado | `submodulos/logging/src/main/java/com/cursosdedesarrollo/logging/MyLoggerFinder.java` `submodulos/logging/src/main/java/com/cursosdedesarrollo/logging/Slf4jLogger.java` |
| `HttpClient` (incubator → estable en 11) | `submodulos/httpclient/src/main/java/com/cursosdedesarrollo/httpclient/App.java` |

---

## Java 10 — Marzo 2018

| Novedad | Fichero(s) |
|---------|-----------|
| `var` — variable local simple | `submodulos/var/src/main/java/com/cursosdedesarrollo/var_inference/App.java` — secciones 1 y 2 |
| `var` — bucle `for-each` y `for` clásico | `submodulos/var/src/main/java/com/cursosdedesarrollo/var_inference/App.java` — secciones 3 y 4 |
| `var` — `try-with-resources` | `submodulos/var/src/main/java/com/cursosdedesarrollo/var_inference/App.java` — sección 5 |
| `var` — límites (campos, parámetros, retorno, null) | `submodulos/var/src/main/java/com/cursosdedesarrollo/var_inference/App.java` — bloque de comentarios al final |
| `List.copyOf` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `copyOf` |
| `Map.copyOf` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `Map.copyOf` |
| `Collectors.toUnmodifiableList` / `toUnmodifiableMap` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` — bloque `toUnmodifiableList` |
| `Optional.orElseThrow()` sin argumento | `submodulos/optional/src/main/java/com/cursosdedesarrollo/optional/App.java` |

---

## Java 11 (LTS) — Septiembre 2018

| Novedad | Fichero(s) |
|---------|-----------|
| `HttpClient` estable — GET síncrono y asíncrono | `submodulos/httpclient/src/main/java/com/cursosdedesarrollo/httpclient/App.java` |
| `HttpClient` — POST con form data y JSON | `submodulos/httpclient/src/main/java/com/cursosdedesarrollo/httpclient/App.java` |
| `String.strip`, `stripLeading`, `stripTrailing` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` — bloque inicial |
| `String.isBlank` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` |
| `String.lines()` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` |
| `String.repeat(n)` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` |
| `Files.readString` / `writeString` | `submodulos/nio/src/main/java/com/cursosdedesarrollo/nio/App.java` |
| `Path.of(String…)` / `Path.of(URI)` | `submodulos/nio/src/main/java/com/cursosdedesarrollo/nio/App.java` |
| `var` en parámetros de lambda | `submodulos/var/src/main/java/com/cursosdedesarrollo/var_inference/App.java` — sección 6 |
| `Predicate.not(Predicate)` | `submodulos/funcional/src/main/java/com/cursosdedesarrollo/funcional/App.java` — `demoPredicate()` |
| `List.toArray(IntFunction)` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `toArray` |

---

## Java 12 — Marzo 2019

| Novedad | Fichero(s) |
|---------|-----------|
| `String.indent(n)` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` — bloque `Indent` |
| `String.transform(Function)` | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` — bloque `transformaciones` |
| `NumberFormat.getCompactNumberInstance` | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — métodos `formatForLocale` y `formatForLocaleHalf` |
| `Files.mismatch(Path, Path)` | `submodulos/nio/src/main/java/com/cursosdedesarrollo/nio/App.java` — bloque `Java 12` |
| `Collectors.teeing` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `Java 12` |
| `CompletableFuture.exceptionallyAsync` | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `Java 12 / Manejo de excepciones` |
| Switch expressions (preview) | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` |

---

## Java 13 — Septiembre 2019

| Novedad | Fichero(s) |
|---------|-----------|
| Text Blocks `"""..."""` (preview) | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` — bloque `Java 13 / Strings multilínea` |

---

## Java 14 — Marzo 2020

| Novedad | Fichero(s) |
|---------|-----------|
| Switch expressions — sintaxis `->` estable | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — primeros dos bloques `switch` |
| Switch expressions — `yield` con bloque multilínea | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — tercer bloque `switch` |
| NullPointerException con mensajes detallados | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `NPE con variable directamente null` |
| NPE en acceso a array null | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `NPE en acceso a array null` |
| NPE en cadena de llamadas | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloques `NPE en cadena de llamadas` y `NPE en cadena más larga` |
| Pattern Matching `instanceof` (preview) | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `instanceof` |
| Records (preview) | `submodulos/record/src/main/java/com/cursosdedesarrollo/Person.java` `submodulos/record/src/main/java/com/cursosdedesarrollo/Rectangle.java` |

---

## Java 15 — Septiembre 2020

| Novedad | Fichero(s) |
|---------|-----------|
| Text Blocks estables | `submodulos/strings/src/main/java/com/cursosdedesarrollo/strings/App.java` — bloque `Java 13 / Strings multilínea` |
| Sealed classes (primera preview) | `submodulos/sealed/src/main/java/com/cursosdedesarrollo/Account.java` |

---

## Java 16 — Marzo 2021

| Novedad | Fichero(s) |
|---------|-----------|
| Records estables | `submodulos/record/src/main/java/com/cursosdedesarrollo/Person.java` `submodulos/record/src/main/java/com/cursosdedesarrollo/Rectangle.java` `submodulos/record/src/main/java/com/cursosdedesarrollo/App.java` |
| Pattern Matching `instanceof` estable | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `instanceof` `submodulos/record/src/main/java/com/cursosdedesarrollo/App.java` — bloque `instanceof` |
| Encapsulamiento fuerte activado por defecto | `submodulos/modulos-migracion/src/main/java/com/cursosdedesarrollo/migracion/App.java` — `demoAccesoPrivadoClaseJDK()` y `demoAccesoClaseNoExportada()` |
| `Stream.toList()` directo (sin collect) | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java` — bloque `Gatherers` al final |

---

## Java 17 (LTS) — Septiembre 2021

| Novedad | Fichero(s) |
|---------|-----------|
| Sealed classes estables — `sealed … permits` | `submodulos/sealed/src/main/java/com/cursosdedesarrollo/Account.java` |
| Sealed classes — subtipo `final` | `submodulos/sealed/src/main/java/com/cursosdedesarrollo/SavingsAccount.java` `submodulos/sealed/src/main/java/com/cursosdedesarrollo/CheckingAccount.java` |
| Sealed classes — subtipo `non-sealed` | `submodulos/sealed/src/main/java/com/cursosdedesarrollo/InvestmentAccount.java` |
| Switch exhaustivo sobre jerarquía sealed | `submodulos/sealed/src/main/java/com/cursosdedesarrollo/App.java` — método `describir()` |
| Encapsulamiento fuerte sin opción de retroceso | `submodulos/modulos-migracion/src/main/java/com/cursosdedesarrollo/migracion/App.java` — comentario de cabecera |
| `RandomGeneratorFactory` — nueva API de aleatoriedad | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `Java 17 / Random` |
| Pattern Matching en switch (primera preview) | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — bloque `case String s` |

---

## Java 18 — Marzo 2022

| Novedad | Fichero(s) |
|---------|-----------|
| Javadoc con snippets `@snippet` (precursor del Markdown) | `submodulos/misc/src/main/java/com/cursosdedesarrollo/PreTest.java` — comentario `<pre>` en Javadoc |

---

## Java 19 — Septiembre 2022

| Novedad | Fichero(s) |
|---------|-----------|
| Virtual threads (primera preview) | `submodulos/virtual/src/main/java/org/example/App.java` — `virtualThreads()` |
| Scoped Values (incubator) | `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesExample.java` |

---

## Java 20 — Marzo 2023

| Novedad | Fichero(s) |
|---------|-----------|
| Virtual threads (segunda preview) | `submodulos/virtual/src/main/java/org/example/App.java` — `virtualThreads()` |
| Scoped Values (segunda preview) | `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesExample.java` `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesAdvancedExample.java` |
| Record patterns en `instanceof` | `submodulos/record/src/main/java/com/cursosdedesarrollo/App.java` — bloques `instanceof` |

---

## Java 21 (LTS) — Septiembre 2023

| Novedad | Fichero(s) |
|---------|-----------|
| Virtual threads estables — `Thread.startVirtualThread` | `submodulos/virtual/src/main/java/org/example/App.java` — `virtualThreads()` |
| Comparativa plataforma vs virtual (50.000 hilos) | `submodulos/virtual/src/main/java/org/example/App.java` — `platformThreads()` vs `virtualThreads()` |
| Scoped Values (preview) | `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesExample.java` |
| Scoped Values avanzado | `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesAdvancedExample.java` |
| Pattern Matching en switch estable — tipos | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — bloque `case String s` |
| Pattern Matching en switch — `case null` | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — bloque `case null` |
| Deconstrucción de records en switch — `case Point(var x, var y) when` | `submodulos/switch/src/main/java/com/cursosdedesarrollo/ejemplos_switch/MainApp.java` — bloque `Point` |
| Colecciones secuenciadas — `getFirst`, `getLast` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `Java 21` |
| Colecciones secuenciadas — `addFirst`, `addLast`, `reversed` | `submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java` — bloque `LinkedHashSet` y `LinkedHashMap` |
| Variables sin nombre `_` en `catch` | `submodulos/misc/src/main/java/com/cursosdedesarrollo/App.java` — bloque `Java 21` |
| Variables sin nombre `_` en `for`, `instanceof`, `switch` | `submodulos/misc/src/main/java/com/cursosdedesarrollo/UnnamedMain.java` |
| Nuevo método `main` de instancia (primera preview) | `submodulos/nuevo_main/src/main/java/com/cursosdedesarrollo/Greeter.java` |

---

## Java 22 — Marzo 2024

| Novedad | Fichero(s) |
|---------|-----------|
| Foreign Function & Memory API estable — `Linker`, `Arena`, `MemorySegment` | `submodulos/function_memory/src/test/java/com/cursosdedesarrollo/AppTest.java` |
| Cuerpos de constructor flexibles (primera preview) — lógica antes de `super(...)` | `submodulos/flexible_constructor/src/main/java/com/cursosdedesarrollo/Coche.java` |
| Variables sin nombre `_` estables | `submodulos/misc/src/main/java/com/cursosdedesarrollo/UnnamedMain.java` |
| Nuevo método `main` (segunda preview) | `submodulos/nuevo_main/src/main/java/com/cursosdedesarrollo/Greeter.java` |
| Ficheros fuente multi-clase ejecutables | `submodulos/misc/src/main/java/com/cursosdedesarrollo/MultiFile.java` `submodulos/misc/src/main/java/com/cursosdedesarrollo/Biblioteca.java` |

---

## Java 23 — Septiembre 2024

| Novedad | Fichero(s) |
|---------|-----------|
| `import module java.base` — importación de módulo completo (primera preview) | `submodulos/module_import/src/test/java/com/cursosdedesarrollo/AppTest.java` |
| Javadoc en Markdown con `///` estable | `submodulos/misc/src/main/java/com/cursosdedesarrollo/Calculadora.java` |
| Cuerpos de constructor flexibles (segunda preview) | `submodulos/flexible_constructor/src/main/java/com/cursosdedesarrollo/Coche.java` |
| `SimpleMain` — `void main()` sin clase pública (preview) | `submodulos/misc/src/main/java/com/cursosdedesarrollo/SimpleMain.java` |

---

## Java 24 — Marzo 2025

| Novedad | Fichero(s) |
|---------|-----------|
| Scoped Values estables | `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesExample.java` `submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesAdvancedExample.java` |
| `import module` (segunda preview) | `submodulos/module_import/src/test/java/com/cursosdedesarrollo/AppTest.java` |
| Gatherers estables — `windowFixed`, `windowSliding`, `scan`, `fold` | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/EjemplosGatherers.java` |
| Gatherers — `mapConcurrent` con hilos virtuales | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/EjemplosGatherers.java` — `ejemploMapConcurrent()` |
| Gatherer personalizado | `submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/DistinctConsecutiveGatherer.java` |

---

## Java 25 (LTS) — Septiembre 2025

| Novedad | Fichero(s) |
|---------|-----------|
| `KDF` API — `HKDF-SHA256` derive key | `submodulos/crypto/src/main/java/com/cursosdedesarrollo/KdfAesGcmExample.java` |
| AES-GCM cifrado + descifrado autenticado | `submodulos/crypto/src/main/java/com/cursosdedesarrollo/KdfAesGcmExample.java` |
| `import module` estable | `submodulos/module_import/src/test/java/com/cursosdedesarrollo/AppTest.java` |
| Cuerpos de constructor flexibles estables | `submodulos/flexible_constructor/src/main/java/com/cursosdedesarrollo/Coche.java` |
| Nuevo método `main` estable | `submodulos/nuevo_main/src/main/java/com/cursosdedesarrollo/Greeter.java` `submodulos/nuevo_main/src/main/java/com/cursosdedesarrollo/App.java` |

---

## Novedades sin módulo dedicado en este proyecto

| Versión | Novedad |
|---------|---------|
| Java 9 | JShell (REPL interactivo) |
| Java 9 | `ProcessHandle` — introspección de procesos del SO |
| Java 11 | `String.formatted` (alternativa a `String.format`) |
| Java 13 | Reimplementación de sockets con NIO |
| Java 14 | `jpackage` — empaquetado de aplicaciones nativas |
| Java 18 | UTF-8 como charset por defecto |
| Java 19 | Structured Concurrency (incubator) |
| Java 21 | Structured Concurrency (preview) |
| Java 23 | String Templates retirado definitivamente |
| Java 24 | `Stream.gather(Gatherer)` como operación intermedia genérica |
