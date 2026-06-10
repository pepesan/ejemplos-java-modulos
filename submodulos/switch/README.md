# switch — Switch expressions y pattern matching

**Java 14** (expresiones), **Java 21** (pattern matching estable)

## Qué cubre

Evolución completa del `switch` en Java: desde la sintaxis clásica con fall-through hasta las expresiones con pattern matching exhaustivo.

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| Switch como sentencia con `->` | Sin `break`, sin fall-through, múltiples etiquetas en un `case` |
| Switch como expresión | Retorna un valor: `String quarter = switch (mes) { ... }` |
| `yield` | Retorna un valor desde un bloque multilínea dentro de un `case` |
| Pattern matching en switch | `case String s ->` liga la referencia y comprueba el tipo simultáneamente |
| `case null` | Manejo explícito del valor nulo sin `NullPointerException` |
| Deconstrucción de record | `case Point(var x, var y) when x > 0 && y > 0 ->` extrae componentes y aplica guarda |

## Clase principal

`com.cursosdedesarrollo.ejemplos_switch.MainApp`
