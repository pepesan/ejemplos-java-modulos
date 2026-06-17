# Ejemplos Java — Módulos y novedades del lenguaje

Proyecto Maven multi-módulo con ejemplos prácticos de las principales novedades de Java, organizados por tema. Cada submodulo es independiente y compilable por separado.

**Java utilizado:** OpenJDK / Corretto 25

## Compilación

```bash
# Compilar todo el proyecto
mvn clean compile

# Compilar un módulo concreto
mvn compile -pl submodulos/streams -am
```

---

## Índice de módulos

### Lenguaje — Sintaxis y tipos

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`var`](submodulos/var/README.md) | 10 | Inferencia de tipos locales: variables, bucles, `try-with-resources`, lambdas, límites |
| [`switch`](submodulos/switch/README.md) | 14–25 | Switch como expresión, sintaxis `->`, `yield`, pattern matching, `case null`, deconstrucción de records, primitive types in patterns (`case int n when`) |
| [`record`](submodulos/record/README.md) | 16 | Records inmutables: constructor compacto, interfaz Comparable, campos estáticos, pattern matching e instanceof con binding |
| [`sealed`](submodulos/sealed/README.md) | 17 | Clases e interfaces selladas: `sealed`, `final`, `non-sealed`, herencia entre interfaces selladas, implementación por record, switch exhaustivo sin `default` |
| [`strings`](submodulos/strings/README.md) | 11–21 | `strip`, `isBlank`, `lines`, `indent`, `transform`, `formatted`, Text Blocks `"""..."""`, `indexOf` en rango, `splitWithDelimiters`, `repeat` y validaciones Emoji |
| [`misc`](submodulos/misc/README.md) | 12–21 | NPE con mensajes detallados, `instanceof`, CompletableFuture, formateo compacto, UTF-8 por defecto (JEP 400), variables sin nombre `_` |
| [`nuevo_main`](submodulos/nuevo_main/README.md) | 21–25 | Método `main` de instancia sin `static`, sin array de args |
| [`flexible_constructor`](submodulos/flexible_constructor/README.md) | 22–25 | Lógica antes de `super(...)` en constructores de subclase |

### Programación funcional y Streams

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`funcional`](submodulos/funcional/README.md) | 8+ | `Function`, `Predicate`, `Consumer`, `Supplier`, `BiFunction`, `@FunctionalInterface` propia; referencias a métodos (4 tipos), `UnaryOperator`, `BinaryOperator`, funciones especializadas para primitivos, currying, aplicación parcial, wrapper para checked exceptions, memoización |
| [`streams`](submodulos/streams/README.md) | 9–25 | `takeWhile`, `dropWhile`, `ofNullable`, `toList`, Gatherers (`windowFixed`, `scan`, `fold`, `mapConcurrent`) |
| [`optional`](submodulos/optional/README.md) | 9–11 | `ifPresentOrElse`, `or`, `stream`, `orElseThrow` |

### Colecciones y datos

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`colecciones`](submodulos/colecciones/README.md) | 9–21 | Inmutabilidad de colecciones en Java 9 (`of`) y Java 10 (`copyOf`), conversión a array en Java 11 (`toArray`), collectors bifurcados en Java 12 (`Collectors.teeing`) y colecciones secuenciadas en Java 21 (incluyendo FIFO/LIFO unificando `Deque`/`ArrayDeque` de Java 6) |
| [`javatime`](submodulos/javatime/README.md) | 8 | `LocalDate`, `LocalTime`, `ZonedDateTime`, `DateTimeFormatter`, aritmética de fechas |

### Concurrencia

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`virtual`](submodulos/virtual/README.md) | 21 | Hilos virtuales (Project Loom): comparativa práctica con hilos de plataforma |
| [`threads`](submodulos/threads/README.md) | 8+ | Hilos tradicionales (Platform Threads) y mecanismos de coordinación (`synchronized`, `wait`/`notify`, `Lock`/`Condition`, `CountDownLatch`, `CyclicBarrier`, `Semaphore`) |
| [`futures`](submodulos/futures/README.md) | 5–8 | `Future`, `CompletableFuture`: `supplyAsync`, `thenCombine`, `thenApply`, `whenComplete` |
| [`scoped-values`](submodulos/scoped-values/README.md) | 21–24 | `ScopedValue` como alternativa a `ThreadLocal` para hilos virtuales, comparación ThreadLocal vs ScopedValue |
| [`structured-concurrency`](submodulos/structured-concurrency/README.md) | 25 | `StructuredTaskScope.open(Joiner)`, ciclo de vida, cancelación en cascada y timeouts |

### Modularidad (JPMS)

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`mainapp`](submodulos/mainapp/README.md) | 9–16 | `requires`, `uses` (ServiceLoader consumidor), `jpackage` (instalador nativo) |
| [`servicio`](submodulos/servicio/README.md) | 9 | `provides … with` (ServiceLoader proveedor) |
| [`dao`](submodulos/dao/README.md) | 9 | `exports` (interfaz pública del módulo) |
| [`hijo`](submodulos/hijo/README.md) | 9 | `exports` + `requires lombok` (modelo de dominio) |
| [`modulos-migracion`](submodulos/modulos-migracion/README.md) | 9–25 | Encapsulamiento fuerte, `InaccessibleObjectException`, `--add-opens`, impacto en librerías |
| [`module_import`](submodulos/module_import/README.md) | 23–25 | `import module java.base` — importación de módulo completo |

### I/O y red

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`nio`](submodulos/nio/README.md) | 11–12 | `Files.readString`, `writeString`, `Path.of`, `Files.mismatch` |
| [`httpclient`](submodulos/httpclient/README.md) | 11 | `HttpClient`: GET/POST síncrono y asíncrono, peticiones múltiples concurrentes, autenticación, cabeceras |

### Programación reactiva

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`reactivestreams-flow`](submodulos/reactivestreams-flow/README.md) | 9 | Flow API del JDK: `Publisher`, `Subscriber`, `Processor` sin dependencias externas |
| [`reactive`](submodulos/reactive/README.md) | — | RxJava 3: `Single`, `Maybe`, `Completable`, `PublishSubject`, `BehaviorSubject` |
| [`reactor`](submodulos/reactor/README.md) | — | Project Reactor: `Flux`, `Mono`, operadores, `concatWith`, `zipWith` |

### APIs avanzadas y rendimiento

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`vectorapi`](submodulos/vectorapi) | 16–25 | Vector API SIMD (incubator): suma de arrays, producto punto, máximo con máscara, benchmark escalar vs vectorial |
| [`function_memory`](submodulos/function_memory/README.md) | 22 | Foreign Function & Memory API: llamada a `strlen` nativo desde Java con `Linker` y `Arena` |
| [`crypto`](submodulos/crypto/README.md) | 25 | HKDF-SHA256 (nueva API `KDF`) + AES-GCM: derivación de clave y cifrado autenticado |
| [`classfile-api`](submodulos/classfile-api) | 24 | Class-File API: `parse` (inspeccionar `.class`), `build` (generar bytecode), `transformClass` (eliminar métodos) |

### Otras features

| Módulo | Java | Qué cubre |
|--------|------|-----------|
| [`jshell`](submodulos/jshell/README.md) | 9 | JShell interactivo (scripts `.jsh`) y API programático (`jdk.jshell`) embebido |
| [`privatemethodsinterfaces`](submodulos/privatemethodsinterfaces/README.md) | 9 | Métodos `private` en interfaces para encapsular lógica compartida entre `default` |
| [`logging`](submodulos/logging/README.md) | 9 | `System.LoggerFinder` personalizado que redirige trazas del JDK a SLF4J |

---

## Estructura del proyecto

```
ejemplos-java-modulosv2/
├── pom.xml                         ← POM raíz (parent)
├── README.md                       ← Este fichero
├── PLAN.md                         ← Plan de mejora del temario
└── submodulos/
    ├── var/                        ← Inferencia de tipos con var
    ├── funcional/                  ← Interfaces funcionales
    ├── streams/                    ← Stream API
    ├── optional/                   ← Optional
    ├── colecciones/                ← Colecciones inmutables
    ├── strings/                    ← String methods y Text Blocks
    ├── switch/                     ← Switch expressions y pattern matching
    ├── record/                     ← Records
    ├── sealed/                     ← Sealed classes
    ├── misc/                       ← NPE, instanceof, CompletableFuture...
    ├── virtual/                    ← Hilos virtuales
    ├── threads/                    ← Hilos tradicionales y coordinación
    ├── futures/                    ← Future y CompletableFuture
    ├── scoped-values/              ← Scoped Values
    ├── javatime/                   ← Java Time API
    ├── nio/                        ← NIO.2
    ├── httpclient/                 ← HttpClient
    ├── nuevo_main/                 ← Nuevo método main
    ├── flexible_constructor/       ← Cuerpos de constructor flexibles
    ├── privatemethodsinterfaces/   ← Métodos privados en interfaces
    ├── logging/                    ← Logging y LoggerFinder
    ├── reactivestreams-flow/       ← Flow API
    ├── reactive/                   ← RxJava 3
    ├── reactor/                    ← Project Reactor
    ├── vectorapi/                  ← Vector API
    ├── function_memory/            ← Foreign Function & Memory API
    ├── crypto/                     ← Criptografía (HKDF + AES-GCM)
    ├── module_import/              ← import module
    ├── modulos-migracion/          ← Encapsulamiento fuerte y migración
    ├── jshell/                     ← JShell interactivo y API programático
    ├── mainapp/                    ← App modular (JPMS)
    ├── dao/                        ← Interfaz DAO (JPMS)
    ├── servicio/                   ← Servicio con ServiceLoader (JPMS)
    ├── hijo/                       ← Modelo con Lombok (JPMS)
    ├── structured-concurrency/     ← Structured Concurrency (Java 25)
    └── classfile-api/              ← Class-File API (Java 24)
```
