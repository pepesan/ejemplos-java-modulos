# dao — Interfaz DAO con JPMS

**Java 9+ (JPMS)**

## Qué cubre

Define la interfaz del patrón DAO (Data Access Object) como módulo JPMS independiente. Al ser un módulo separado, `mainapp` puede depender de la abstracción sin conocer la implementación concreta.

## Contenido

| Elemento | Descripción |
|----------|-------------|
| `Dao<T>` | Interfaz genérica con operaciones CRUD básicas |
| `module-info.java` | `exports com.cursosdedesarrollo.dao` |

## Módulo JPMS

```java
module com.cursosdedesarrollo.dao {
    exports com.cursosdedesarrollo.dao;
}
```

La implementación concreta está en el módulo `servicio`, que declara `provides Dao with MyServiceImpl`.
