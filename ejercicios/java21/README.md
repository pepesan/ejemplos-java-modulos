# Ejercicios Java 21 (LTS)

Este submódulo contiene los ejercicios prácticos enfocados en dominar las novedades fundamentales de Java 21.

---

## Ficheros involucrados

Para realizar este conjunto de ejercicios, debes trabajar con los siguientes ficheros:
1. **Fichero de implementación:** [Java21Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21/src/main/java/com/cursosdedesarrollo/ejercicios/java21/Java21Exercise.java)
2. **Fichero de pruebas unitarias:** [Java21ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21/src/test/java/com/cursosdedesarrollo/ejercicios/java21/Java21ExerciseTest.java)

---

## Estructuras y Requisitos a Implementar

En el fichero [Java21Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java21/src/main/java/com/cursosdedesarrollo/ejercicios/java21/Java21Exercise.java) debes crear y completar las siguientes estructuras:

### 1. Registro (Record) `Point`
* **Estructura:** Define un `record` de apoyo:
  ```java
  public record Point(int x, int y) {}
  ```
* **Qué debe hacer:** Almacena coordenadas cartesianas enteras para su uso en deconstrucción de patrones.

### 2. Método `ejecutarEnHiloVirtual` (Virtual Threads)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String ejecutarEnHiloVirtual(Callable<String> tarea) throws ExecutionException, InterruptedException
  ```
* **Qué debe hacer:** Debe abrir un cargador de hilos virtuales mediante `Executors.newVirtualThreadPerTaskExecutor()`. Lanzar y enviar la tarea `tarea` a través del executor, esperar a que complete (`Future.get()`) y retornar el resultado devuelto de la misma.

### 3. Registro `ColeccionExtremos` y Método `procesarColeccionSecuenciada` (Sequenced Collections)
* **Estructuras:**
  1. Un `record` para modelar el resultado:
     ```java
     public record ColeccionExtremos(String primero, String ultimo, List<String> reversa) {}
     ```
  2. Un método estático para procesar la colección secuenciada:
     ```java
     public static ColeccionExtremos procesarColeccionSecuenciada(SequencedCollection<String> items)
     ```
* **Qué debe hacer:**
  - Extraer el primer elemento llamando a `items.getFirst()`.
  - Extraer el último elemento llamando a `items.getLast()`.
  - Crear una copia en formato de lista invertida usando `items.reversed()` (ej. `List.copyOf(items.reversed())`).
  - Retornar una nueva instancia del record `ColeccionExtremos` con estos datos.

### 4. Método `evaluarObjetoConRecordPattern` (Record Patterns en Switch)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String evaluarObjetoConRecordPattern(Object obj)
  ```
* **Qué debe hacer:** Utilizar una expresión switch para evaluar el tipo del argumento `obj`:
  - Si coincide con el record `Point`, aplicar deconstrucción directa (Record Pattern): `case Point(int x, int y) -> ...` y retornar una cadena con los valores de `x` e `y` sin llamar a métodos accessor.
  - Si es `String s`, retornar el texto.
  - Si es `null` (usando `case null`), retornar un aviso de nulo.
  - En la rama `default`, retornar "Otro tipo".

### 5. Método `parsearEnteroSeguroConUnnamed` (Variables sin nombre)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static boolean parsearEnteroSeguroConUnnamed(String str)
  ```
* **Qué debe hacer:** Intentar parsear el parámetro `str` a un entero con `Integer.parseInt(str)`. Capturar la posible excepción `NumberFormatException` utilizando el carácter de descarte o variable sin nombre `_` (ej. `catch (NumberFormatException _)`). Retornar `true` si se capturó la excepción, o `false` en caso de éxito.
