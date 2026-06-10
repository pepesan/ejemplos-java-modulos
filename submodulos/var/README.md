# var — Inferencia de tipos locales

**Java 10+**

## Qué cubre

La palabra reservada `var` permite que el compilador infiera el tipo de una variable local a partir de la expresión que se le asigna. El tipo queda fijado en tiempo de compilación: no es tipado dinámico.

## Ejemplos incluidos

| Caso | Descripción |
|------|-------------|
| Variable local simple | `var lista = new ArrayList<String>()` — infiere `ArrayList<String>` |
| Tipo genérico complejo | `var mapa = new HashMap<String, List<Integer>>()` — evita repetir el tipo |
| Bucle `for-each` | `for (var fruta : frutas)` — el tipo de cada elemento se infiere del iterable |
| Bucle `for` clásico | `for (var i = 0; i < 5; i++)` |
| `try-with-resources` | `try (var reader = new BufferedReader(...))` |
| Lambda (Java 11) | `filter((var p) -> ...)` — permite anotar parámetros de lambda |
| Resultado de método | El tipo inferido es el tipo de retorno del método |
| Límites (comentados) | No se puede usar en campos, parámetros, tipo de retorno, ni con `null` literal |

## Clase principal

`com.cursosdedesarrollo.var_inference.App`
