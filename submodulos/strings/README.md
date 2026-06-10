# strings — Manipulación de cadenas

**Java 11–21**

## Qué cubre

Mejoras progresivas de la clase `String` desde Java 11, incluyendo nuevos métodos de limpieza, indentado, transformación y los bloques de texto multilínea.

## Ejemplos incluidos

| Método / Feature | Java | Descripción |
|---|---|---|
| `strip`, `stripLeading`, `stripTrailing` | 11 | Eliminación de espacios Unicode (mejora de `trim`) |
| `formatted(args...)` | 11 | Equivalente a `String.format()` como método de instancia; combinable con Text Blocks |
| `isBlank` | 11 | Comprueba si la cadena está vacía o solo tiene espacios |
| `lines` | 11 | Devuelve un Stream de líneas |
| `repeat` | 11 | Repite la cadena N veces |
| `indent` | 12 | Añade o elimina sangría, normalizando saltos de línea |
| `transform` | 12 | Aplica una función a la cadena y devuelve el resultado |
| Text Blocks `"""..."""` | 13–15 (estable 15) | Cadenas multilínea con alineamiento automático del margen |
| Base64 encode/decode | 8 | Codificación y decodificación de bytes |
| String Templates (comentado) | preview | STR y FMT processors (feature retirada en Java 23) |

## Clase principal

`com.cursosdedesarrollo.strings.App`
