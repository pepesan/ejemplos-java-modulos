# Ejercicios Java 25 (LTS)

Este submódulo contiene los ejercicios prácticos enfocados en dominar las novedades y APIs estables más modernas introducidas en Java 25.

---

## Ficheros involucrados

Para realizar este conjunto de ejercicios, debes trabajar con los siguientes ficheros:
1. **Fichero de implementación:** [Java25Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25/src/main/java/com/cursosdedesarrollo/ejercicios/java25/Java25Exercise.java)
2. **Fichero de pruebas unitarias:** [Java25ExerciseTest.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25/src/test/java/com/cursosdedesarrollo/ejercicios/java25/Java25ExerciseTest.java)

---

## Estructuras y Requisitos a Implementar

En el fichero [Java25Exercise.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/ejercicios/java25/src/main/java/com/cursosdedesarrollo/ejercicios/java25/Java25Exercise.java) debes crear y completar las siguientes estructuras:

### 1. Importación del Módulo Principal (`import module`)
* **Estructura:** Añadir en la primera línea del archivo de código:
  ```java
  import module java.base;
  ```
* **Qué debe hacer:** Importar todas las clases e interfaces del módulo fundamental `java.base` de forma masiva, lo que elimina la necesidad de realizar importaciones individuales de APIs comunes como `List`, `StructuredTaskScope`, `Thread`, etc.

### 2. Jerarquía de Constructores Flexibles (Flexible Constructor Bodies)
* **Estructuras:**
  1. Define la clase base `CuentaBancaria`:
     ```java
     public static class CuentaBancaria {
         private final double saldoInicial;
         public CuentaBancaria(double saldoInicial) {
             this.saldoInicial = saldoInicial;
         }
         public double getSaldoInicial() { return saldoInicial; }
     }
     ```
  2. Define la subclase `CuentaBancariaLimitada`:
     ```java
     public static class CuentaBancariaLimitada extends CuentaBancaria {
         private final double limiteCredito;
         // Constructor a completar
     }
     ```
* **Qué debe hacer:** En el constructor de `CuentaBancariaLimitada(double saldoInicial, double limiteCredito)`:
  - Realizar una validación previa del parámetro `saldoInicial`: si es inferior a cero, lanzar una `IllegalArgumentException` inmediatamente.
  - Ajustar el parámetro local `limiteCredito` si es inferior a 100 para que tome un valor mínimo de 100.
  - Invocar al constructor padre llamando a `super(saldoInicial)`.
  - Asignar el campo final `this.limiteCredito = limiteCredito`.
  *(Nota: Esto muestra la capacidad de Java 22+ de ejecutar sentencias lógicas y validaciones antes de llamar a `super()`)*.

### 3. Método `ejecutarTareasEstructuradas` (Structured Concurrency)
* **Estructura:** Un método público y estático con la siguiente firma:
  ```java
  public static String ejecutarTareasEstructuradas() throws Exception
  ```
* **Qué debe hacer:** Debe abrir un ámbito de concurrencia estructurada mediante un bloque try-with-resources que instancie la interfaz `StructuredTaskScope` utilizando la política de unión de tareas exitosas:
  ```java
  try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) { ... }
  ```
  - Lanzar de forma asíncrona dos tareas usando `scope.fork(() -> String)` que simulen retrasos menores y retornen cadenas simples (ej. "Servicio A" y "Servicio B").
  - Coordinar la sincronización estructurada llamando a `scope.join()`.
  - Extraer los resultados con `.get()` de cada subtarea, concatenarlos con un ampersand ("&") y retornar el resultado.

### 4. Estructura para nuevo método `main` simplificado
* **Estructura:** Un método ordinario de prueba de instancia o implícitamente declarado:
  ```java
  public String saludarDesdeInstancia()
  ```
* **Qué debe hacer:** Retornar una cadena de bienvenida de prueba, demostrando la capacidad de Java 25 para la ejecución directa sin requerir que el punto de entrada sea estático (`public static void main(String[] args)`).
