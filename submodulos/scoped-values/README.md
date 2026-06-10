# scoped-values — Scoped Values

**Java 21** (preview en 20)

## Qué cubre

`ScopedValue<T>` es una alternativa más segura y eficiente a `ThreadLocal` para compartir datos inmutables dentro de un ámbito (scope) bien definido, especialmente con hilos virtuales.

## Diferencia con `ThreadLocal`

| | `ThreadLocal` | `ScopedValue` |
|--|---|---|
| Mutabilidad | Mutable (set/get en cualquier momento) | Inmutable dentro del scope |
| Limpieza | Manual (`remove()`) | Automática al salir del scope |
| Hilos virtuales | Problemas de fuga de memoria | Diseñado para funcionar con ellos |
| Propagación a hilos hijo | Heredado automáticamente | Explícita (re-binding manual) |

## Ejemplos incluidos

| Clase | Descripción |
|-------|-------------|
| `ScopedValuesExample` | Binding básico con `ScopedValue.where(...).run(...)` y propagación a hilo virtual hijo |
| `ScopedValuesAdvancedExample` | Uso avanzado con múltiples scoped values y tareas paralelas |

## Clase principal

`com.cursosdedesarrollo.ScopedValuesExample`
