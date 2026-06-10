# modulos-migracion — Encapsulamiento fuerte y migración

**Java 9–25**

## Qué cubre

El encapsulamiento fuerte (*strong encapsulation*) introducido por JPMS en Java 9 y activado por defecto en Java 16 impide que código externo acceda por reflexión a paquetes internos del JDK. Este módulo muestra el problema, su impacto en librerías antiguas y las soluciones disponibles.

## Evolución del encapsulamiento

| Versión | Comportamiento |
|---------|----------------|
| Java ≤ 8 | Reflexión libre sobre cualquier clase del JDK |
| Java 9–15 | Acceso permitido con advertencias (`WARNING`) |
| Java 16+ | `InaccessibleObjectException` por defecto |
| Java 17+ | Sin opción de retroceso |

## Escenarios del ejemplo

| Escenario | Resultado sin flags | Con `--add-opens` |
|-----------|--------------------|--------------------|
| Reflexión sobre clase propia | Funciona | — |
| `ArrayList.elementData` (campo privado JDK) | `InaccessibleObjectException` | Funciona |
| `sun.misc.Unsafe` (paquete no exportado) | `InaccessibleObjectException` | Funciona |
| Simulación de serialización por framework | Funciona (clase propia) | — |

## Soluciones

1. **`--add-opens java.base/java.util=ALL-UNNAMED`** — workaround temporal en línea de comandos o `pom.xml`
2. **`opens` en `module-info.java`** — solución limpia para librerías ya modularizadas
3. **Actualizar la librería** — Hibernate 6+, Jackson 2.12+, Spring 6+, Lombok 1.18.22+

## Clase principal

`com.cursosdedesarrollo.migracion.App`
