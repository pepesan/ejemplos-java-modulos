# modulos-migracion — Encapsulamiento fuerte y migración

**Java 9–25**

## Qué cubre

El encapsulamiento fuerte (*strong encapsulation*) introducido por JPMS en Java 9 y activado por defecto en Java 16 impide que código externo acceda por reflexión a paquetes internos del JDK. Este módulo muestra el problema, su impacto en biliotecas antiguas y las soluciones disponibles.

## Evolución del encapsulamiento

| Versión | Comportamiento |
|---------|----------------|
| Java ≤ 8 | Reflexión libre sobre cualquier clase del JDK |
| Java 9–15 | Acceso permitido con advertencias (`WARNING`) |
| Java 16+ | `InaccessibleObjectException` por defecto |
| Java 17+ | Sin opción de retroceso |

## Escenarios del ejemplo

| Escenario | Resultado sin flags | Con `--add-opens` / `opens` |
|-----------|--------------------|--------------------|
| Reflexión sobre clase propia | Funciona | — |
| `ArrayList.elementData` (campo privado JDK) | `InaccessibleObjectException` | Funciona con `--add-opens` |
| `sun.misc.Unsafe` (paquete no exportado) | `InaccessibleObjectException` | Funciona con `--add-opens` |
| Simulación de serialización por framework | Funciona (clase propia) | — |
| Reflexión a través de módulo propio (`reflexion`) | `InaccessibleObjectException` | Funciona con `opens ... to ...` en `module-info` |

## Soluciones

1. **`--add-opens java.base/java.util=ALL-UNNAMED`** — workaround temporal en línea de comandos o `pom.xml` para clases del JDK
2. **`opens` en `module-info.java`** — solución limpia para que otros módulos (como nuestro nuevo módulo de utilidades `reflexion`) puedan acceder por reflexión a tus clases
3. **Actualizar la librería** — Hibernate 6+, Jackson 2.12+, Spring 6+, Lombok 1.18.22+

## Clase principal

`com.cursosdedesarrollo.migracion.App`

## Ejecución

Puedes ejecutar este ejemplo de dos formas para comparar el comportamiento del encapsulamiento fuerte:

### 1. Ejecutar sin flags (Muestra el bloqueo por defecto)
Ejecuta la aplicación usando el plugin `exec:exec` en la JVM con el module-path activado (bloqueando el acceso reflexivo a `ArrayList.elementData` de `java.base`):
```bash
mvn exec:exec -pl submodulos/modulos-migracion
```

### 2. Ejecutar con flags (Workaround de desbloqueo)
Ejecuta la aplicación en una nueva JVM configurada mediante el perfil `workaround` (`exec:exec`), que aplica el flag `--add-opens java.base/java.util=ALL-UNNAMED`:
```bash
mvn exec:exec -pl submodulos/modulos-migracion -Pworkaround
```

