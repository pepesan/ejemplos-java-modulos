# flexible_constructor — Cuerpos de constructor flexibles

**Java 22** (preview), **Java 25** (estable)

## Qué cubre

Antes de Java 22, la primera sentencia de un constructor de subclase debía ser obligatoriamente `super(...)` o `this(...)`. Con los cuerpos de constructor flexibles se puede ejecutar lógica (validaciones, transformaciones) antes de llamar al constructor padre.

## Ejemplo incluido

`Coche extends Vehiculo` — antes de llamar a `super(marca, anio)`:

```java
int anioValido = Math.max(1886, anio);          // validación
String marcaNormalizada = marca.trim().toUpperCase(); // transformación
super(marcaNormalizada, anioValido);             // ahora sí, con valores preparados
```

## Por qué es útil

Evita tener que crear métodos de utilidad estáticos (`static`) solo para preparar los argumentos del constructor padre, que era el único workaround posible antes.

## Clases

`Vehiculo`, `Coche`
