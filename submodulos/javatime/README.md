# javatime — API de fecha y hora

**Java 8+**

## Qué cubre

La API `java.time` (JSR-310) reemplaza a `Date` y `Calendar`. Es inmutable, thread-safe y mucho más expresiva.

## Ejemplos incluidos

| Clase / Operación | Descripción |
|---|---|
| `LocalDate.now()` | Fecha actual sin hora ni zona horaria |
| `LocalTime.now()` | Hora actual sin fecha ni zona horaria |
| `ZonedDateTime` | Fecha y hora con zona horaria explícita (`Europe/Madrid`) |
| `DateTimeFormatter` | Formateo personalizado: `dd/MM/yyyy`, `HH:mm:ss` |
| `plusDays`, `minusMonths`, `plusYears` | Aritmética de fechas de forma inmutable |
| `LocalDate.parse` / `toString` | Conversión entre `String` y fecha |

## Clase principal

`org.example.App`
