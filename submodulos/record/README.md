# record — Clases de datos inmutables

**Java 16** (preview en 14–15)

## Qué cubre

Los `record` son clases inmutables portadoras de datos. El compilador genera automáticamente constructor canónico, accessors, `equals`, `hashCode` y `toString`. Eliminan el boilerplate de los DTOs/Value Objects clásicos.

## Ejemplos incluidos

| Concepto | Clase | Descripción |
|----------|-------|-------------|
| Record básico | `Person` | `record Person(String name, String gender, int age)` — la forma más simple posible |
| Record intermedio | `Student` | Implementa `Comparable<Student>`, demuestra validaciones y métodos adicionales |
| Constructor compacto | `Student` y `Rectangle` | Validación de parámetros antes de la asignación automática |
| Constructor adicional | `Rectangle` | Constructor sin parámetros que delega al canónico (`this(...)`) |
| Campo y método estático | `Rectangle` | `static double goldenRatio` e inicializador estático |
| Accessor explícito | `Rectangle` | Sobrescritura del accessor `length()` con lógica de logging adicional |
| Método de factoría | `Rectangle` | `createGoldenRectangle(double width)` |
| `instanceof` con binding | `App` | `if (person instanceof Person p)` sobre un record |
| Record Patterns | `App` | Deconstrucción directa en instanceof: `Person(String name, String gender, int age)` |

## Características declaradas en comentarios

Un record: es `final` (no heredable), puede implementar interfaces, puede tener métodos de instancia, puede ser genérico, puede anotarse. No puede ser abstracto ni tener campos de instancia adicionales no estáticos.

## Clases

`Person`, `Student`, `Rectangle`, `App`
