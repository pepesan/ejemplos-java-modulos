# colecciones — Colecciones inmutables y mejoras

**Java 9–21**

## Qué cubre

Métodos de factoría para crear colecciones inmutables, nuevos collectors y las colecciones secuenciadas de Java 21.

## Ejemplos incluidos

| Método / Feature | Java | JEP / Detalle | Descripción |
|---|---|---|---|
| `List.of`, `Set.of` | 9 | [JEP 269](https://openjdk.org/jeps/269) | Colecciones inmutables rápidas de forma compacta |
| `Map.of` | 9 | [JEP 269](https://openjdk.org/jeps/269) | Mapa inmutable de hasta 10 pares clave-valor |
| `Map.ofEntries` + `Map.entry` | 9 | [JEP 269](https://openjdk.org/jeps/269) | Mapa inmutable sin límite de pares |
| `List.copyOf`, `Set.copyOf` | 10 | API Java 10 | Copia inmutable de una colección mutable |
| `Map.copyOf` | 10 | API Java 10 | Copia inmutable de un mapa mutable |
| `toArray(IntFunction)` | 11 | API Java 11 | `list.toArray(String[]::new)` sin necesidad de casting manual |
| `Collectors.teeing` | 12 | [JEP 344](https://openjdk.org/jeps/344) | Dos collectors en paralelo sobre el mismo stream combinando sus resultados |
| Colecciones secuenciadas | 21 | [JEP 431](https://openjdk.org/jeps/431) | `getFirst`, `getLast`, `addFirst`, `addLast`, `reversed` en `List`, `LinkedHashSet`, `LinkedHashMap` |

## Referencias

* [Java 21 Sequenced Collections (Baeldung)](https://www.baeldung.com/java-21-sequenced-collections)
* [JEP 431: Sequenced Collections](https://openjdk.org/jeps/431)
* [JEP 269: Convenience Factory Methods for Collections](https://openjdk.org/jeps/269)

## Clase principal

[App](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/colecciones/src/main/java/com/cursosdedesarrollo/colecciones/App.java)
