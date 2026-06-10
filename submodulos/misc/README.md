# misc — Miscelánea de novedades

**Java 12–21**

## Qué cubre

Ejemplos de características diversas que no encajan en un módulo propio: formateo de números compacto, manejo asíncrono de excepciones, pattern matching con `instanceof`, NullPointerException con mensajes detallados y otras novedades.

## Ejemplos incluidos

| Feature | Java | Descripción |
|---------|------|-------------|
| `NumberFormat.getCompactNumberInstance` | 12 | Formateo compacto con locale: 1.500 → "1,5K" / "1,5 mil" |
| `CompletableFuture.exceptionallyAsync` | 12 | Recuperación de errores asíncronos con executor propio |
| Pattern matching `instanceof` | 14 | `if (o instanceof String s && s.length() > 5)` |
| NPE con mensajes detallados | 14 | Mensajes que indican exactamente qué referencia era null |
| NPE en cadena de llamadas | 14 | `persona.getDireccion().getCiudad()` → mensaje señala qué eslabón fue null |
| `RandomGeneratorFactory` | 17 | Nueva API unificada para generadores de números pseudoaleatorios |
| Variables sin nombre `_` | 21 | `catch (NumberFormatException _)`, `for (int _ : array)` |
| Javadoc con Markdown (`///`) | 23 | Documentación en formato Markdown en `Calculadora.java` |

## Clases

`App`, `Calculadora`, `SimpleMain`, `UnnamedMain`, `MultiFile`, `Biblioteca`, `Order`
