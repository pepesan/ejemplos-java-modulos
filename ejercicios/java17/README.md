# Ejercicios Java 17 (LTS)

Este submódulo contiene los ejercicios prácticos enfocados en dominar las novedades fundamentales de Java 17.

---

## Ficheros involucrados

Para realizar este conjunto de ejercicios, debes trabajar con los siguientes ficheros:
1. **Fichero de implementación:** [Java17Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17/src/main/java/com/cursosdedesarrollo/ejercicios/java17/Java17Exercise.java)
2. **Fichero de pruebas unitarias:** [Java17ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17/src/test/java/com/cursosdedesarrollo/ejercicios/java17/Java17ExerciseTest.java)

---

## Estructuras y Requisitos a Implementar

En el fichero [Java17Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java17/src/main/java/com/cursosdedesarrollo/ejercicios/java17/Java17Exercise.java) debes crear y completar las siguientes estructuras:

### 1. Registro (Record) `Vehiculo`
* **Estructura:** Define un `record` en el ámbito de la clase o archivo:
  ```java
  public record Vehiculo(String marca, String modelo, String tipoMotor) {}
  ```
* **Qué debe hacer:** Almacena datos inmutables de un vehículo. Genera automáticamente los getters nativos (`marca()`, `modelo()`, `tipoMotor()`), constructores, `equals()`, `hashCode()` y `toString()`.

### 2. Jerarquía de Clases Selladas (Sealed Types)
* **Estructuras:** 
  1. Define una interfaz sellada llamada `Figura`:
     ```java
     public sealed interface Figura permits Circulo, Rectangulo {}
     ```
  2. Implementa las dos únicas clases/records permitidos bajo el contrato de `Figura`:
     ```java
     public record Circulo(double radio) implements Figura {}
     ```
     ```java
     public record Rectangulo(double base, double altura) implements Figura {}
     ```
* **Qué debe hacer:** Asegura por contrato a nivel de compilador que no puede haber otras subclases o implementaciones de `Figura` fuera de las declaradas en la cláusula `permits`.

### 3. Método `describirFigura` (Switch Expressions)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String describirFigura(Figura figura)
  ```
* **Qué debe hacer:** Debe usar una expresión `switch` moderna que retorne directamente una cadena de texto describiendo la figura (sintaxis de flecha `->`). Al ser `Figura` una interfaz sellada, el compilador verificará que todos los casos posibles (`Circulo` y `Rectangulo`) estén contemplados sin necesidad de utilizar una rama `default`.

### 4. Método `formatearObjeto` (Pattern Matching para instanceof)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String formatearObjeto(Object obj)
  ```
* **Qué debe hacer:** Inspeccionar el tipo del argumento `obj`:
  - Si es una instancia de `String`, vincularla directamente a la variable de patrón `s` (ej. `obj instanceof String s`) y retornar la longitud y contenido del texto.
  - Si es una instancia de `Integer`, vincularla a la variable de patrón `i` y retornar una cadena con su valor al cuadrado.
  - Si es una instancia del record `Vehiculo`, vincularla a `v` y retornar su marca y modelo.
  - Para cualquier otro tipo, retornar "Objeto no soportado".
