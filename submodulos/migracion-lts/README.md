# migracion-lts — Guía Práctica de Migración de Versiones LTS

**Java 8 → 11 → 17 → 21 → 25**

## Qué cubre

Este submódulo está diseñado para servir como catálogo práctico de refactorización de código obsoleto e ineficiente hacia las características estables recomendadas en cada versión de soporte a largo plazo (LTS) de Java.

## Contenido de las Guías de Migración

### 1. [Java 8 a 11](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/migracion-lts/src/main/java/com/cursosdedesarrollo/migracionlts/Java8to11Migration.java)
* **Colecciones Inmutables**: Uso de `List.of()` en lugar de `Collections.unmodifiableList()`.
* **Inferencia de Tipos Locales**: Uso de `var` para reducir el boilerplate al instanciar tipos genéricos complejos.
* **Optional**: Uso de `ifPresentOrElse(...)` para evitar comprobaciones manuales de presencia con `isPresent()`.

### 2. [Java 11 a 17](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/migracion-lts/src/main/java/com/cursosdedesarrollo/migracionlts/Java11to17Migration.java)
* **Text Blocks**: Uso de `"""..."""` en lugar de concatenaciones manuales y engorrosas con `\n`.
* **Expresiones Switch**: Uso de `->` para evitar el boilerplate de `break` y retornos directos.
* **Records**: Uso de `record` para clases portadoras de datos sin boilerplate (eliminación de getters/setters/equals/hashCode).
* **Pattern Matching para instanceof**: Eliminación del casting manual explícito en estructuras condicionales.

### 3. [Java 17 a 21](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/migracion-lts/src/main/java/com/cursosdedesarrollo/migracionlts/Java17to21Migration.java)
* **Colecciones Secuenciadas**: Uso de `getFirst()`, `getLast()` y `reversed()` en lugar de accesos numéricos manuales.
* **Hilos Virtuales**: Migración de pools físicos fijos pesados a `Executors.newVirtualThreadPerTaskExecutor()`.

### 4. [Java 21 a 25](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/migracion-lts/src/main/java/com/cursosdedesarrollo/migracionlts/Java21to25Migration.java)
* **Scoped Values**: Uso de `ScopedValue` para variables de ámbito seguras y de bajo costo frente a `ThreadLocal`.
* **Variables Sin Nombre**: Uso de `_` en catches e inicializaciones no utilizadas.
* **Concurrencia Estructurada**: Coordinación con `StructuredTaskScope.open(Joiner)` en sustitución de `CompletableFuture` complejos.

---

## Cómo Ejecutar la Guía Completa de Migración

Puedes ejecutar todo el flujo de demostración desde la raíz del proyecto usando el comando Maven:

```bash
mvn exec:java -pl submodulos/migracion-lts -Dexec.mainClass="com.cursosdedesarrollo.migracionlts.App"
```
