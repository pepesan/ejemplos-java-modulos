# privatemethodsinterfaces — Métodos privados en interfaces

**Java 9**

## Qué cubre

Java 9 permite declarar métodos `private` (y `private static`) en interfaces. Esto resuelve el problema de duplicación de código entre métodos `default` de la misma interfaz, sin exponer lógica interna como `default` ni como parte del contrato público.

## Ejemplo incluido

| Elemento | Descripción |
|----------|-------------|
| `Foo` (interfaz) | Declara métodos `default` públicos y métodos `private` de utilidad compartida entre ellos |
| `CustomFoo` | Implementación concreta de `Foo` |
| `AppInterfaces` | Punto de entrada que ejercita los métodos de la interfaz |

## Por qué es útil

Sin métodos privados en interfaces, la lógica compartida entre métodos `default` obligaba a extraerla a una clase de utilidad separada o a duplicarla. Con métodos `private` la interfaz puede encapsular su propia lógica interna.

## Clase principal

`com.cursosdedesarrollo.privatemethodsinterfaces.AppInterfaces`
