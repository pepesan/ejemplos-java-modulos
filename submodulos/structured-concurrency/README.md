# structured-concurrency — Concurrencia Estructurada

**Java 25 (JEP 499 — en estado preview)** (también preview en Java 21-24 bajo JEPs anteriores)

## Qué cubre

La **Concurrencia Estructurada** trata a múltiples tareas concurrentes como una única unidad de trabajo. Esto simplifica el código de hilos concurrentes al acotar su ciclo de vida dentro de un bloque léxico (usualmente un bloque `try-with-resources`), asegurando que todos los hilos hijos terminen o se cancelen antes de continuar el flujo del hilo principal.

En **Java 25**, la API de `StructuredTaskScope` se ha consolidado utilizando el método de instanciación unificado `StructuredTaskScope.open(Joiner)`. Los `Joiner` predefinidos permiten definir políticas claras de éxito, fallo, cancelación y espera grupal.

## Ejemplos incluidos

El submódulo contiene los siguientes ejemplos:

1. **[App.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/structured-concurrency/src/main/java/com/cursosdedesarrollo/structured/App.java):**
   Demuestra el uso básico de la interfaz `StructuredTaskScope` utilizando distintos tipos de `Joiner`:
   * `Joiner.allSuccessfulOrThrow()`: Espera a que terminen todas las subtareas; si alguna falla, cancela el resto y lanza una excepción.
   * `Joiner.anySuccessfulResultOrThrow()`: Termina el ámbito en cuanto la primera tarea finaliza con éxito, cancelando inmediatamente el resto (ideal para consultas a servidores espejo).
   * `Joiner.awaitAll()`: Espera a que terminen todas las subtareas permitiendo inspeccionar individualmente el estado de éxito o fallo de cada una.

2. **[StructuredConcurrencyCancellationDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/structured-concurrency/src/main/java/com/cursosdedesarrollo/structured/StructuredConcurrencyCancellationDemo.java):**
   Muestra en detalle el ciclo de vida y las políticas de cancelación en la concurrencia estructurada:
   * **Cancelación en Cascada:** Al fallar una subtarea crítica, la JVM propaga automáticamente una señal de interrupción al resto de subtareas activas del mismo ámbito para evitar desperdicio de CPU o red.
   * **Cancelación por Límite de Tiempo (Timeout):** Uso de `scope.joinUntil(Instant)` para finalizar todas las tareas si exceden un tiempo determinado.

## Ejecución de los Ejemplos

Puedes ejecutar cualquiera de las clases desde la raíz del proyecto usando Maven (es necesario pasar `MAVEN_OPTS="--enable-preview"` ya que la concurrencia estructurada es una característica en estado preview):

```bash
# 1. Ejecutar el ejemplo general de Joiners
MAVEN_OPTS="--enable-preview" mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.structured.App" -pl submodulos/structured-concurrency

# 2. Ejecutar la demostración de Cancelación en Cascada y Timeouts
MAVEN_OPTS="--enable-preview" mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.structured.StructuredConcurrencyCancellationDemo" -pl submodulos/structured-concurrency
```
