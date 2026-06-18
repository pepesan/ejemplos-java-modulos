# reflexion — Módulo utilitario de reflexión

**Java 9+ (JPMS)**

## Qué cubre

Módulo utilitario modular que expone herramientas para inspeccionar objetos utilizando la API de reflexión de Java (`java.lang.reflect`). Demuestra cómo se comportan las restricciones de encapsulamiento fuerte de JPMS entre dos módulos distintos del proyecto.

## Módulo JPMS

```java
module com.cursosdedesarrollo.reflexion {
    exports com.cursosdedesarrollo.reflexion;
}
```

## Clase principal

`com.cursosdedesarrollo.reflexion.Reflector`

Esta clase expone el método `inspect(Object obj)`, el cual intenta leer todos los campos declarados (incluyendo los privados) de la clase del objeto. Lanza `InaccessibleObjectException` si el módulo de origen no abre (`opens`) el paquete correspondiente al módulo reflector.
