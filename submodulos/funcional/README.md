# funcional — Interfaces funcionales

**Java 8+**

## Qué cubre

Las interfaces funcionales del paquete `java.util.function` son la base del estilo funcional en Java: permiten pasar comportamiento como si fuera un valor, usando lambdas o referencias a métodos. Una interfaz funcional es cualquier interfaz con exactamente un método abstracto.

---

## `App.java` — Interfaces funcionales del JDK

Clase principal: `com.cursosdedesarrollo.funcional.App`

| Interfaz | Firma | Qué se demuestra |
|----------|-------|-----------------|
| `Function<T,R>` | `R apply(T t)` | `andThen`, `compose`, `identity`, encadenamiento largo |
| `Predicate<T>` | `boolean test(T t)` | `negate`, `Predicate.not()` (Java 11), `and`, `or`, validación de contraseña compuesta |
| `Consumer<T>` | `void accept(T t)` | `andThen`, pipeline de efectos secundarios (registrar → auditar → notificar) |
| `Supplier<T>` | `T get()` | Inicialización diferida (*lazy*), `orElse` vs `orElseGet` |
| `BiFunction<T,U,R>` | `R apply(T t, U u)` | Dos argumentos, `andThen`, referencia a constructor |
| `@FunctionalInterface` propia | — | `Transformador<E,S>` con método `default`, `ValidadorConExcepcion<T>` con checked exception |

---

## `EjemplosFuncionales.java` — Patrones avanzados

Clase principal: `com.cursosdedesarrollo.funcional.EjemplosFuncionales`

### 1. Referencias a métodos (4 tipos)

| Tipo | Sintaxis | Equivalente lambda |
|------|----------|--------------------|
| Método estático | `Integer::parseInt` | `x -> Integer.parseInt(x)` |
| Método de instancia sobre el tipo | `String::toUpperCase` | `x -> x.toUpperCase()` |
| Método de instancia sobre objeto concreto | `System.out::println` | `x -> System.out.println(x)` |
| Constructor | `Articulo::new` | `(a,b) -> new Articulo(a,b)` |

Son equivalentes en bytecode a una lambda; mejoran la legibilidad cuando el cuerpo de la lambda es una única llamada a método existente.

### 2. `UnaryOperator` y `BinaryOperator`

Subinterfaces especializadas de `Function` y `BiFunction` para cuando entrada y salida son del **mismo tipo**:

- `UnaryOperator<T>` → `T apply(T t)` — usado en `List.replaceAll()`
- `BinaryOperator<T>` → `T apply(T t1, T t2)` — usado en `Stream.reduce()`
- `BinaryOperator.maxBy(Comparator)` / `minBy(Comparator)` — fábrica estática

### 3. Funciones especializadas para primitivos

Las interfaces genéricas usan autoboxing (`Integer`, `Long`, `Double`), lo que genera objetos temporales. Las variantes especializadas operan directamente sobre primitivos:

| Categoría | Ejemplos |
|-----------|---------|
| `T → int/long/double` | `ToIntFunction<T>`, `ToDoubleFunction<T>` |
| `int/long/double → R` | `IntFunction<R>`, `LongFunction<R>` |
| Operador unario | `IntUnaryOperator`, `LongUnaryOperator` |
| Operador binario | `IntBinaryOperator` |
| Predicado | `IntPredicate`, `LongPredicate` |

`IntFunction<R>` aparece en `Stream.toArray(String[]::new)`.  
Regla: para colecciones grandes o cálculos intensivos, usar la variante especializada.

### 4. Currying y aplicación parcial

- **Currying**: transforma `f(a, b)` en `f(a)(b)` — se expresa con `Function<A, Function<B, R>>`.
- **Aplicación parcial**: fija uno o varios argumentos de antemano y devuelve una función con menos parámetros libres.

```java
Function<Integer, Function<Integer, Integer>> potencia =
        base -> exponente -> (int) Math.pow(base, exponente);

Function<Integer, Integer> potenciasDeDos = potencia.apply(2);
potenciasDeDos.apply(8);   // 256
potenciasDeDos.apply(16);  // 65536
```

Útil para reutilizar lógica con contexto fijado (nivel de log, plantilla, tasa de impuesto…).

### 5. Wrapper para checked exceptions

Las interfaces del JDK no pueden lanzar checked exceptions. Tres patrones progresivos:

| Patrón | Cuándo usarlo |
|--------|--------------|
| `try-catch` inline en la lambda | Casos puntuales sencillos |
| `@FunctionalInterface` propia con `throws` | Cuando el nombre de dominio mejora la legibilidad |
| `wrapChecked()` genérico | Para usar checked exceptions en `Stream.map()` sin contaminar el pipeline |

`wrapChecked()` convierte cualquier `FunctionChecked<T,R>` en una `Function<T,R>` estándar, relanzando las checked exceptions como `RuntimeException`.

### 6. Memoización

Caché de resultados para funciones puras (misma entrada → siempre misma salida):

```java
static <T, R> Function<T, R> memoizar(Function<T, R> fn) {
    Map<T, R> cache = new HashMap<>();
    return entrada -> {
        if (cache.containsKey(entrada)) return cache.get(entrada);
        R resultado = fn.apply(entrada);
        cache.put(entrada, resultado);
        return resultado;
    };
}
```

> **Por qué no `computeIfAbsent`**: `HashMap.computeIfAbsent` lanza `ConcurrentModificationException` si el mapping function modifica el mapa durante su ejecución, algo que ocurre exactamente en funciones recursivas (fibonacci). La secuencia `containsKey/get/put` evita este problema.

Aplicado a fibonacci recursivo: reduce la complejidad de O(2ⁿ) a O(n).
