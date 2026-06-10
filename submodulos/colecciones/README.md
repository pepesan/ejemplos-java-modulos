# colecciones — Colecciones inmutables y mejoras

**Java 9–21**

## Qué cubre

Métodos de factoría para crear colecciones inmutables, nuevos collectors y las colecciones secuenciadas de Java 21.

## Ejemplos incluidos

| Método / Feature | Java | Descripción |
|---|---|---|
| `List.of`, `Set.of` | 9 | Colecciones inmutables de forma compacta |
| `List.copyOf` | 10 | Copia inmutable de una colección mutable |
| `Map.of` | 9 | Mapa inmutable de hasta 10 pares clave-valor |
| `Map.ofEntries` + `Map.entry` | 9 | Mapa inmutable sin límite de pares |
| `Map.copyOf` | 10 | Copia inmutable de un mapa mutable |
| `toArray(IntFunction)` | 11 | `list.toArray(String[]::new)` sin casting |
| `Collectors.teeing` | 12 | Dos collectors en paralelo sobre el mismo stream |
| Colecciones secuenciadas | 21 | `getFirst`, `getLast`, `addFirst`, `addLast`, `reversed` en `List`, `LinkedHashSet`, `LinkedHashMap` |

Referencias: https://www.baeldung.com/java-21-sequenced-collections

## Clase principal

`com.cursosdedesarrollo.colecciones.App`
