# virtual — Hilos virtuales (Project Loom)

**Java 21** (preview en 19–20)

## Qué cubre

Los hilos virtuales son hilos ligeros gestionados por la JVM, no por el sistema operativo. Permiten crear millones de hilos concurrentes con un coste de memoria y creación drásticamente inferior al de los hilos de plataforma tradicionales.

## Diferencia clave

| | Hilos de plataforma | Hilos virtuales |
|--|---|---|
| Gestión | Sistema operativo | JVM |
| Coste de creación | Alto (~1 MB stack) | Muy bajo (~KB) |
| Límite práctico | Miles | Millones |
| Creación | `new Thread(...)` | `Thread.startVirtualThread(...)` |
| Uso ideal | CPU-intensivo | I/O intensivo (HTTP, BD, archivos) |

## Ejemplo incluido

Se crean 50.000 hilos de cada tipo, cada uno duerme 5 segundos, y se mide el tiempo total. El ejemplo demuestra empíricamente la diferencia de rendimiento.

```java
// Hilo de plataforma
new Thread(() -> Thread.sleep(5_000)).start();

// Hilo virtual
Thread.startVirtualThread(() -> Thread.sleep(5_000));
```

## Clase principal

`org.example.App`
