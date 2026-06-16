# streams — Stream API

**Java 9–25**

## Qué cubre

Mejoras progresivas de la API de Streams desde Java 9, incluyendo los nuevos métodos de corte, manejo de nulos, colectores avanzados y la API de Gatherers de Java 25.

## Ejemplos incluidos

| Método / API | Java | Descripción |
|---|---|---|
| `takeWhile` | 9 | Toma elementos mientras se cumpla la condición; para al primer fallo |
| `dropWhile` | 9 | Descarta elementos mientras se cumpla la condición; emite el resto |
| `Stream.ofNullable` | 9 | Stream de 0 o 1 elemento según si el valor es null |
| `Stream.iterate` con predicado | 9 | Genera secuencia con condición de parada integrada |
| `Collectors.filtering` | 9 | Filtra dentro de un collector sin un `filter` previo |
| `Collectors.flatMapping` | 9 | `flatMap` dentro de un collector |
| `Collectors.toUnmodifiableList/Map` | 10 | Colecciones inmutables directas desde collectors |
| `Collectors.teeing` | 12 | Aplica dos collectors en paralelo y combina los resultados |
| `toList()` directo | 16 | `stream.toList()` sin necesidad de `collect(Collectors.toList())` |
| `Gatherers` | 25 | `windowFixed`, `windowSliding`, `scan`, `fold`, `mapConcurrent` |
| Gatherer personalizado | 25 | `DistinctConsecutiveGatherer` — elimina duplicados consecutivos |

## Clases

* **[App](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/App.java)**: Guía didáctica paso a paso desde conceptos básicos (Java 8) hasta APIs avanzadas.
* **[EjemplosStreamsMejorasIntermedias](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/EjemplosStreamsMejorasIntermedias.java)**: Características intermedias y mejoras específicas introducidas en Java 9, 10 y 16.
* **[EjemplosStreamsGatherersAvanzado](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/EjemplosStreamsGatherersAvanzado.java)**: Ejemplos prácticos y avanzados de la API de Gatherers (Java 24/25).
* **[EjemplosStreamsGathererPersonalizado](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/EjemplosStreamsGathererPersonalizado.java)**: Implementación de un colector intermedio (Gatherer) a medida para eliminar duplicados consecutivos.
* **[Persona](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/streams/src/main/java/com/cursosdedesarrollo/streams/Persona.java)**: Clase POJO auxiliar.
