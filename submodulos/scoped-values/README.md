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
| [ScopedValuesExample](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesExample.java) | Binding básico con `ScopedValue.where(...).run(...)` y propagación manual a hilo virtual hijo. |
| [ScopedValuesAdvancedExample](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ScopedValuesAdvancedExample.java) | Uso avanzado con múltiples scoped values, sombreado (shadowing) y tareas en paralelo. |
| [ThreadLocalVsScopedValueDemo](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/scoped-values/src/main/java/com/cursosdedesarrollo/ThreadLocalVsScopedValueDemo.java) | Comparación lado a lado de `ThreadLocal` frente a `ScopedValue`, ejemplificando los peligros de mutación/fuga y el impacto en memoria. |

## Ejecución de los Ejemplos

Puedes ejecutar cualquiera de las clases individualmente desde el directorio raíz del proyecto (es necesario pasar `MAVEN_OPTS="--enable-preview"` si tu entorno requiere habilitar preview):

```bash
# 1. Ejecutar el ejemplo básico de Scoped Values
MAVEN_OPTS="--enable-preview" mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.ScopedValuesExample" -pl submodulos/scoped-values

# 2. Ejecutar el ejemplo avanzado de Scoped Values
MAVEN_OPTS="--enable-preview" mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.ScopedValuesAdvancedExample" -pl submodulos/scoped-values

# 3. Ejecutar la comparativa ThreadLocal vs ScopedValue
MAVEN_OPTS="--enable-preview" mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.ThreadLocalVsScopedValueDemo" -pl submodulos/scoped-values
```
