# nio — NIO.2 — Manejo moderno de ficheros

**Java 11–12**

## Qué cubre

Mejoras de la API NIO.2 (`java.nio.file`) para leer y escribir ficheros de forma más cómoda, y el método `Files.mismatch` para comparar contenidos.

## Ejemplos incluidos

| Método | Java | Descripción |
|--------|------|-------------|
| `Files.writeString` | 11 | Escribe una cadena en un fichero con charset opcional |
| `Files.readString` | 11 | Lee todo el contenido de un fichero como `String` |
| `Path.of` | 11 | Crea un `Path` desde partes o desde un `URI` |
| `Files.exists` | — | Comprueba si una ruta existe |
| `Files.mismatch` | 12 | Devuelve la posición del primer byte diferente entre dos ficheros (-1 si son iguales) |

## Clase principal

`com.cursosdedesarrollo.nio.App`
