# logging — Sistema de logging

**Java 9+**

## Qué cubre

Integración del sistema de logging del JDK (`java.util.logging`) con frameworks externos mediante la API `System.LoggerFinder` introducida en Java 9. Permite que las librerías usen el logging del JDK mientras la aplicación redirige las trazas a SLF4J (o cualquier otro backend).

## Clases

| Clase | Descripción |
|-------|-------------|
| `App` | Punto de entrada; carga `logging.properties` y realiza una petición HTTP cuyo resultado se loguea |
| `MyLoggerFinder` | Implementación personalizada de `System.LoggerFinder` que delega en SLF4J |
| `Slf4jLogger` | Adaptador que mapea los niveles del JDK a los niveles de SLF4J |

## Clase principal

`com.cursosdedesarrollo.logging.App`
