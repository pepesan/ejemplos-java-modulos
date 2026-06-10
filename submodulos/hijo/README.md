# hijo — Modelo de dominio con JPMS y Lombok

**Java 9+ (JPMS), Lombok**

## Qué cubre

Módulo Java con `module-info.java` que exporta un paquete de modelo de dominio. Usa Lombok para eliminar el boilerplate de getters/setters.

## Contenido

| Elemento | Descripción |
|----------|-------------|
| `User` | Entidad con `@Data` de Lombok (genera getters, setters, equals, hashCode, toString) |
| `module-info.java` | `exports com.cursosdedesarrollo.hijo` + `requires lombok` |

## Módulo JPMS

```java
module com.cursosdedesarrollo.hijo {
    requires lombok;
    exports com.cursosdedesarrollo.hijo;
}
```

Este módulo es consumido por `mainapp` y `servicio`.
