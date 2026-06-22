# Ejercicios Java 8 (LTS)

Este submódulo contiene los ejercicios prácticos enfocados en dominar las novedades fundamentales de Java 8.

---

## Ficheros involucrados

Para realizar este conjunto de ejercicios, debes trabajar con los siguientes ficheros:
1. **Fichero de implementación:** [Java8Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8/src/main/java/com/cursosdedesarrollo/ejercicios/java8/Java8Exercise.java)
2. **Fichero de pruebas unitarias:** [Java8ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8/src/test/java/com/cursosdedesarrollo/ejercicios/java8/Java8ExerciseTest.java)

---

## Estructuras y Requisitos a Implementar

En el fichero [Java8Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java8/src/main/java/com/cursosdedesarrollo/ejercicios/java8/Java8Exercise.java) debes crear y completar las siguientes estructuras:

### 1. Interfaz Funcional `@FunctionalInterface` `Transformador`
* **Estructura:** Crea una interfaz funcional genérica dentro de la clase `Java8Exercise`:
  ```java
  @FunctionalInterface
  public interface Transformador<T, R> {
      R transformar(T input);
  }
  ```
* **Qué debe hacer:** Define el contrato funcional para transformar un objeto de tipo `T` a un tipo `R`. Debe poseer un único método abstracto `transformar`.

### 2. Método `aplicarTransformacion`
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static Integer aplicarTransformacion(String input, Transformador<String, Integer> transformador)
  ```
* **Qué debe hacer:** Debe invocar el método `transformar` de la interfaz `transformador` pasando el argumento `input` y retornar el valor entero de retorno. Permite ver el uso de expresiones lambdas o referencias a métodos en el paso de comportamiento.

### 3. Método `procesarNumeros` (Stream API)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static List<Integer> procesarNumeros(List<Integer> numeros)
  ```
* **Qué debe hacer:** Debe procesar la lista de entrada usando streams:
  1. Invocar `.stream()` sobre la lista.
  2. Utilizar `.filter()` con una lambda para quedarse solo con los números que son pares (`n % 2 == 0`).
  3. Utilizar `.map()` para elevar al cuadrado los números filtrados (`n * n`).
  4. Coleccionar el resultado a una nueva lista con `.collect(Collectors.toList())` y retornarla.

### 4. Método `obtenerLongitudOp` (Optional API)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static int obtenerLongitudOp(Optional<String> optionalStr)
  ```
* **Qué debe hacer:** Extraer la longitud de la cadena almacenada en el `Optional`. Debe hacerse de forma segura usando operadores funcionales (`.map()` y `.orElse()`). Si el `Optional` es nulo (`null`) o está vacío (`Optional.empty()`), debe retornar `0` en lugar de lanzar excepciones.

### 5. Método `calcularDiasEntre` (Date & Time API)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static long calcularDiasEntre(LocalDate inicio, LocalDate fin)
  ```
* **Qué debe hacer:** Utilizar el nuevo motor de fechas de Java 8 (`java.time`) para obtener la diferencia en días transcurridos desde `inicio` hasta `fin`. Se debe implementar utilizando `ChronoUnit.DAYS.between(inicio, fin)`.
