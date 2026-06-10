# record — Clases de datos inmutables

**Java 16** (preview en 14–15)

## Qué cubre

Los `record` son clases inmutables portadoras de datos. El compilador genera automáticamente constructor canónico, accessors, `equals`, `hashCode` y `toString`. Eliminan el boilerplate de los DTOs/Value Objects clásicos.

## Ejemplos incluidos

| Concepto | Clase | Descripción |
|----------|-------|-------------|
| Record básico | `Person` | `record Person(String name, String gender, int age)` — la forma más simple |
| Constructor compacto | `Rectangle` | Validación de parámetros en el constructor canónico |
| Constructor adicional | `Rectangle` | Constructor sin parámetros que delega al canónico |
| Campo y método estático | `Rectangle` | `static double goldenRatio` e inicializador estático |
| Accessor explícito | `Rectangle` | Sobreescritura del accessor `length()` con lógica adicional |
| Método de factoría | `Rectangle` | `createGoldenRectangle(double width)` |
| `instanceof` con binding | `App` | `if (person instanceof Person p)` sobre un record |

## Características declaradas en comentarios

Un record: es `final`, puede implementar interfaces, puede tener métodos de instancia, puede ser genérico, puede anotarse. No puede ser abstracto ni tener campos de instancia adicionales.

## Clases

`Person`, `Rectangle`, `App`
