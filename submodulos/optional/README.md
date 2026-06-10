# optional — API Optional

**Java 8–11**

## Qué cubre

`Optional<T>` es un contenedor que puede o no contener un valor. Evita los `null` explícitos y fuerza a pensar en el caso "vacío". Este módulo cubre los métodos añadidos en Java 9–11.

## Ejemplos incluidos

| Método | Java | Descripción |
|--------|------|-------------|
| `ofNullable`, `isPresent`, `get` | 8 | Uso básico del Optional |
| `ifPresentOrElse` | 9 | Ejecuta Plan A si hay valor, Plan B si está vacío |
| `or` | 9 | Devuelve otro `Optional` como alternativa si el primero está vacío |
| `orElseThrow` | 10 | Lanza una excepción específica si el Optional está vacío |
| `stream()` | 9 | Convierte el Optional en un Stream de 0 o 1 elemento; útil para concatenar con `IntStream` |

## Clase principal

`com.cursosdedesarrollo.optional.App`
