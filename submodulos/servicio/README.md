# servicio — Implementación de servicio con JPMS ServiceLoader

**Java 9+ (JPMS)**

## Qué cubre

Implementación del patrón ServiceLoader con JPMS: el módulo declara que provee una implementación de `Dao` sin que los consumidores necesiten conocer la clase concreta.

## Módulo JPMS

```java
module com.cursosdedesarrollo.service {
    exports com.cursosdedesarrollo.servicio;
    requires com.cursosdedesarrollo.hijo;
    requires com.cursosdedesarrollo.dao;
    provides com.cursosdedesarrollo.dao.Dao
            with com.cursosdedesarrollo.servicio.MyServiceImpl;
}
```

## Patrón provides / uses

| Declaración | Módulo | Significado |
|-------------|--------|-------------|
| `provides Dao with MyServiceImpl` | `servicio` | "Yo ofrezco una implementación de Dao" |
| `uses Dao` | `mainapp` | "Yo necesito una implementación de Dao en runtime" |

El consumidor (`mainapp`) usa `ServiceLoader.load(Dao.class)` para obtener la implementación en runtime sin referenciar `MyServiceImpl` directamente.
