# Ejercicios de Java LTS (Java 8 a 25)

Este documento detalla los ejercicios prácticos diseñados para repasar los cambios fundamentales introducidos en cada una de las versiones de soporte a largo plazo (LTS) de Java. Cada conjunto de ejercicios está alojado en su propio submódulo dentro de la carpeta [ejercicios](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios).

---

## Estructura del Módulo de Ejercicios

Todos los ejercicios heredan la configuración del proyecto principal y están organizados como submódulos de Maven bajo la carpeta raíz `ejercicios`:

- **Padre de ejercicios:** [ejercicios/pom.xml](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/pom.xml)
- **Java 8:** [ejercicios/java8](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8)
- **Java 11:** [ejercicios/java11](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11)
- **Java 17:** [ejercicios/java17](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17)
- **Java 21:** [ejercicios/java21](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21)
- **Java 25:** [ejercicios/java25](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25)

---

## Contenido de los Ejercicios

### 1. Java 8 (LTS) — Lambdas, Streams, Time y Optional
*   **Módulo:** [ejercicios/java8](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8)
*   **Clase principal del ejercicio:** [Java8Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8/src/main/java/com/cursosdedesarrollo/ejercicios/java8/Java8Exercise.java)
*   **Tests de verificación:** [Java8ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8/src/test/java/com/cursosdedesarrollo/ejercicios/java8/Java8ExerciseTest.java)
*   **Tareas:**
    1.  **Lambdas y Referencias:** Implementar una interfaz funcional personalizada (`Transformador<String, Integer>`) usando lambdas y referencias a métodos.
    2.  **Streams API:** Filtrar una lista de números para obtener solo los pares, elevarlos al cuadrado, y coleccionar los resultados en una lista.
    3.  **Optional:** Implementar un método que reciba una cadena opcional, y devuelva su longitud, o `0` si es nula/vacía, evitando lanzar excepciones de puntero nulo.
    4.  **Fecha y Hora (java.time):** Crear un método para calcular la diferencia en días entre dos fechas utilizando `LocalDate` y `ChronoUnit`.

### 2. Java 11 (LTS) — HttpClient, Strings y Predicate
*   **Módulo:** [ejercicios/java11](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11)
*   **Clase principal del ejercicio:** [Java11Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11/src/main/java/com/cursosdedesarrollo/ejercicios/java11/Java11Exercise.java)
*   **Tests de verificación:** [Java11ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11/src/test/java/com/cursosdedesarrollo/ejercicios/java11/Java11ExerciseTest.java)
*   **Tareas:**
    1.  **HttpClient:** Configurar un `HttpClient` básico para preparar una solicitud GET sincrónica simulada (se evaluará la construcción de la petición con `HttpRequest`).
    2.  **Métodos de String:** Procesar textos usando `strip()`, `isBlank()` y `lines()` para limpiar una cadena multilínea y contar sus líneas no vacías.
    3.  **Predicate.not:** Filtrar elementos usando `Predicate.not` en combinación con streams.
    4.  **Files (NIO):** Guardar y recuperar cadenas de texto usando `Files.writeString` y `Files.readString`.

### 3. Java 17 (LTS) — Sealed Classes, Records y Switch
*   **Módulo:** [ejercicios/java17](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17)
*   **Clase principal del ejercicio:** [Java17Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17/src/main/java/com/cursosdedesarrollo/ejercicios/java17/Java17Exercise.java)
*   **Tests de verificación:** [Java17ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17/src/test/java/com/cursosdedesarrollo/ejercicios/java17/Java17ExerciseTest.java)
*   **Tareas:**
    1.  **Records:** Crear un `record` llamado `Vehiculo` con atributos como marca, modelo y tipo de motor.
    2.  **Sealed Classes:** Implementar una jerarquía sellada de figuras geométricas (`Figura` que permita a `Circulo` y `Rectangulo`).
    3.  **Switch Expressions:** Escribir una función que reciba una instancia de la jerarquía `Figura` y retorne una descripción en texto usando una expresión `switch` exhaustiva.
    4.  **Pattern Matching instanceof:** Utilizar el desempaquetado inteligente de variables con `instanceof` para extraer y formatear datos de un objeto genérico de forma segura.

### 4. Java 21 (LTS) — Virtual Threads, Record Patterns y Sequenced Collections
*   **Módulo:** [ejercicios/java21](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21)
*   **Clase principal del ejercicio:** [Java21Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21/src/main/java/com/cursosdedesarrollo/ejercicios/java21/Java21Exercise.java)
*   **Tests de verificación:** [Java21ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21/src/test/java/com/cursosdedesarrollo/ejercicios/java21/Java21ExerciseTest.java)
*   **Tareas:**
    1.  **Virtual Threads:** Usar `Executors.newVirtualThreadPerTaskExecutor()` para lanzar un lote de tareas cortas y simular una espera no bloqueante.
    2.  **Sequenced Collections:** Implementar operaciones en colecciones ordenadas (como `List` o `LinkedHashSet`) usando `getFirst()`, `getLast()` y `reversed()`.
    3.  **Record Patterns:** Utilizar deconstrucción de records (ej. `Point(int x, int y)`) en una sentencia/expresión `switch`.
    4.  **Variables sin Nombre:** Usar la sintaxis de variable sin nombre `_` dentro de un bloque `catch` para ignorar excepciones esperadas de manera explícita.

### 5. Java 25 (LTS) — Constructores Flexibles, Structured Concurrency y Module Imports
*   **Módulo:** [ejercicios/java25](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25)
*   **Clase principal del ejercicio:** [Java25Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25/src/main/java/com/cursosdedesarrollo/ejercicios/java25/Java25Exercise.java)
*   **Tests de verificación:** [Java25ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25/src/test/java/com/cursosdedesarrollo/ejercicios/java25/Java25ExerciseTest.java)
*   **Tareas:**
    1.  **Flexible Constructor Bodies:** Escribir un constructor de una subclase que realice validaciones previas y asigne variables locales antes de invocar a `super()`.
    2.  **Structured Concurrency:** Implementar `StructuredTaskScope` con políticas de cancelación oportuna (por ejemplo, `ShutdownOnFailure`) para realizar dos tareas concurrentes y unir sus resultados de forma estructurada.
    3.  **Module Imports:** Declarar e importar todas las APIs fundamentales usando `import module java.base;` para simplificar la sección de importaciones.
    4.  **Nuevo Método main:** Ejecutar código usando la sintaxis del nuevo método `main` de instancia o implícitamente declarado (clase sin nombre).

---

## Ejecución de los Ejercicios

Para compilar y comprobar que todas las soluciones de ejercicios pasan sus test unitarios, ejecuta en la raíz del proyecto:

```bash
mvn clean test -pl ejercicios -am
```
o para probar un módulo concreto (por ejemplo, Java 8):
```bash
mvn test -pl ejercicios/java8
```
