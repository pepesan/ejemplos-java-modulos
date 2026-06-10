# funcional — Interfaces funcionales

**Java 8+**

## Qué cubre

Las interfaces funcionales del paquete `java.util.function` son la base del estilo funcional en Java: permiten pasar comportamiento como si fuera un valor, usando lambdas o referencias a métodos. Una interfaz funcional es cualquier interfaz con exactamente un método abstracto.

## Ejemplos incluidos

| Interfaz | Firma | Qué se demuestra |
|----------|-------|-----------------|
| `Function<T,R>` | `R apply(T t)` | `andThen`, `compose`, `identity`, encadenamiento largo |
| `Predicate<T>` | `boolean test(T t)` | `negate`, `Predicate.not()`, `and`, `or`, validación de contraseña |
| `Consumer<T>` | `void accept(T t)` | `andThen`, pipeline de efectos secundarios (registrar→auditar→notificar) |
| `Supplier<T>` | `T get()` | Inicialización diferida (*lazy*), `orElse` vs `orElseGet` |
| `BiFunction<T,U,R>` | `R apply(T t, U u)` | Dos argumentos, `andThen`, referencia a constructor |
| `@FunctionalInterface` propia | — | `Transformador<E,S>` con método `default`, `ValidadorConExcepcion<T>` con checked exception |

## Clase principal

`com.cursosdedesarrollo.funcional.App`
