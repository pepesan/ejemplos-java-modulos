# module_import — Importación de módulos completos

**Java 23** (preview), **Java 25** (estable)

## Qué cubre

La declaración `import module <nombre>` importa de una sola vez todos los paquetes exportados por un módulo del JDK, eliminando la necesidad de múltiples líneas de `import`.

## Ejemplo incluido

```java
import module java.base;   // importa java.util, java.io, java.lang.*, etc.
import module java.xml;    // importa javax.xml, org.w3c.dom, etc.

var lista = List.of(1, 2, 3); // List disponible sin import explícito
```

## Por qué es útil

Reduce el ruido de imports en ejemplos, prototipos y código educativo. No tiene impacto en rendimiento: el compilador resuelve qué tipos se usan realmente.

## Clase de test

`com.cursosdedesarrollo.AppTest`
