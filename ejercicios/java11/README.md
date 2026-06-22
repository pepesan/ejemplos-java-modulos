# Ejercicios Java 11 (LTS)

Este submódulo contiene los ejercicios prácticos enfocados en dominar las novedades fundamentales de Java 11.

---

## Ficheros involucrados

Para realizar este conjunto de ejercicios, debes trabajar con los siguientes ficheros:
1. **Fichero de implementación:** [Java11Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11/src/main/java/com/cursosdedesarrollo/ejercicios/java11/Java11Exercise.java)
2. **Fichero de pruebas unitarias:** [Java11ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11/src/test/java/com/cursosdedesarrollo/ejercicios/java11/Java11ExerciseTest.java)

---

## Estructuras y Requisitos a Implementar

En el fichero [Java11Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java11/src/main/java/com/cursosdedesarrollo/ejercicios/java11/Java11Exercise.java) debes completar las siguientes estructuras de métodos:

### 1. Método `crearPeticionHttpGet` (HttpClient API)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static HttpRequest crearPeticionHttpGet(String uriStr)
  ```
* **Qué debe hacer:** Debe instanciar y configurar una petición HTTP usando el nuevo cliente nativo. Para ello, debe llamar a `HttpRequest.newBuilder()`, pasarle la URI construida mediante `URI.create(uriStr)`, indicar que es un método de tipo `GET` llamando a `.GET()`, y finalmente construir y retornar el objeto `HttpRequest` con `.build()`.

### 2. Método `obtenerLineasNoVacias` (Mejoras en Strings)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static List<String> obtenerLineasNoVacias(String multilineStr)
  ```
* **Qué debe hacer:** 
  1. Convertir la cadena de texto multilínea en un flujo de líneas mediante `multilineStr.lines()`.
  2. Limpiar los espacios en blanco sobrantes al inicio y final de cada línea mediante `.map(String::strip)`.
  3. Filtrar para retener únicamente aquellas líneas que contengan contenido visible usando `.filter(line -> !line.isBlank())`.
  4. Agrupar el flujo procesado y retornarlo como una lista (`.collect(Collectors.toList())`).

### 3. Método `filtrarNombresNoVacios` (Predicate.not)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static List<String> filtrarNombresNoVacios(List<String> nombres)
  ```
* **Qué debe hacer:** Utilizar Streams sobre la lista `nombres` y filtrar los elementos usando el adaptador de negación de predicados `Predicate.not()`. Específicamente, debe filtrar usando `Predicate.not(String::isEmpty)` para quedarse únicamente con aquellas cadenas que posean caracteres (incluso si son espacios en blanco), retornando la lista final.

### 4. Método `escribirYLeerArchivoTemporal` (Archivos NIO)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String escribirYLeerArchivoTemporal(String contenido) throws IOException
  ```
* **Qué debe hacer:**
  1. Crear un fichero temporal en el sistema de archivos con `Files.createTempFile("prefijo", ".txt")`.
  2. Escribir la cadena `contenido` de forma directa al fichero usando `Files.writeString(path, contenido)`.
  3. Leer de vuelta todo el contenido textual del fichero creado de forma simplificada con `Files.readString(path)`.
  4. Garantizar en un bloque `finally` la limpieza y eliminación del archivo en disco con `Files.deleteIfExists(path)`, retornando el texto leído.
