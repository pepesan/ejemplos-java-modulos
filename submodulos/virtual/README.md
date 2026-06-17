# virtual — Hilos virtuales (Project Loom)

**Java 21** (preview en 19–20)

## Qué cubre

Los hilos virtuales son hilos ligeros gestionados por la JVM, no por el sistema operativo. 
Permiten crear millones de hilos concurrentes con un coste de memoria y creación drásticamente inferior 
al de los hilos de plataforma tradicionales.

## Diferencia clave

| | Hilos de plataforma | Hilos virtuales |
|--|---|---|
| Gestión | Sistema operativo | JVM |
| Coste de creación | Alto (~1 MB stack) | Muy bajo (~KB) |
| Límite práctico | Miles | Millones |
| Creación | `new Thread(...)` | `Thread.startVirtualThread(...)` |
| Uso ideal | CPU-intensivo | I/O intensivo (HTTP, BD, archivos) |

## Ejemplos incluidos y Comparativa de Rendimiento

El programa principal realiza una comparativa de rendimiento y tiempos de ejecución ejecutando **2.000 tareas bloqueantes independientes de 100ms** utilizando 4 enfoques diferentes:

1. **Hilos Tradicionales sin envoltura (`new Thread`):** La forma clásica de Java de instanciar e iniciar hilos nativos directamente mapeados 1:1 al sistema operativo.
2. **Hilos de Plataforma con Builder (`Thread.ofPlatform`):** Utiliza la nueva API de creación simétrica de Java 21 para hilos tradicionales.
3. **Hilos Virtuales Directos (`Thread.startVirtualThread`):** Hilos ligeros instanciados y arrancados directamente por la JVM.
4. **Hilos Virtuales con `ExecutorService` (`VirtualThreadPerTaskExecutor`):** El patrón recomendado en aplicaciones corporativas que administra las tareas concurrentes de forma estructurada usando la interfaz `ExecutorService` (compatible con `try-with-resources` en Java 21).

### Rendimiento Típico

| Método | Tiempo Total de Ejecución |
|---|---|
| **1. Tradicional sin envoltura (`new Thread`)** | ~240 ms - 270 ms |
| **2. Hilo de Plataforma con Builder** | ~230 ms - 260 ms |
| **3. Hilo Virtual directo (`startVirtualThread`)** | **~110 ms** (casi el mínimo teórico de 100ms) |
| **4. Hilo Virtual con ExecutorService** | **~115 ms** (casi el mínimo teórico de 100ms) |

El resultado demuestra empíricamente cómo los Hilos Virtuales eliminan prácticamente todo el coste de creación, planificación y cambio de contexto (*context switch*), permitiendo ejecutar miles de tareas concurrentes al límite de velocidad física de la propia tarea.

## Sincronización entre Hilos Virtuales y Pinning

Además de la comparativa de rendimiento, el submódulo incluye una demostración avanzada sobre la sincronización ([VirtualThreadSynchronizationDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/VirtualThreadSynchronizationDemo.java)) que aborda:

* **El problema de Carrier Thread Pinning:** Qué sucede cuando un hilo virtual se bloquea dentro de un bloque `synchronized` (anclando el hilo físico portador del SO, lo que obliga al planificador de la JVM a crear hilos nativos adicionales de compensación).
* **La solución mediante ReentrantLock:** Cómo el uso de `ReentrantLock` permite a la JVM desmontar con éxito el hilo virtual durante bloqueos, evitando la creación masiva e ineficiente de hilos nativos.
* **Sincronización Ligera:** Uso de estructuras no bloqueantes/atómicas (`AtomicInteger`) para coordinación de estado simple sin adquirir locks.

## Ejecución de los Ejemplos

Puedes ejecutar cualquiera de las clases de forma independiente mediante Maven desde el directorio raíz del proyecto:

```bash
# 1. Ejecutar la comparativa de rendimiento de hilos
mvn exec:java -Dexec.mainClass="org.example.App" -pl submodulos/virtual

# 2. Ejecutar la demostración de sincronización, pinning y ReentrantLock
mvn exec:java -Dexec.mainClass="org.example.VirtualThreadSynchronizationDemo" -pl submodulos/virtual

# 3. Ejecutar la comparativa de pool de conexiones (Platform vs Virtual)
mvn exec:java -Dexec.mainClass="org.example.ConnectionPoolDemo" -pl submodulos/virtual

# 4. Ejecutar la demostración de peligro en tareas CPU-Intensivas
mvn exec:java -Dexec.mainClass="org.example.CpuIntensiveTaskDemo" -pl submodulos/virtual

# 5. Ejecutar la comparativa de Servidor Web HTTP
mvn exec:java -Dexec.mainClass="org.example.HttpServerComparisonDemo" -pl submodulos/virtual
```

## Clases principales

* **Clase Ejecutable Principal (Comparativa de rendimiento):** [App.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/App.java)
* **Demostración de Pinning y Locks (Independiente):** [VirtualThreadSynchronizationDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/VirtualThreadSynchronizationDemo.java)
* **Comparativa de Pool de Conexiones (Independiente):** [ConnectionPoolDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/ConnectionPoolDemo.java)
* **Antipatrón de Tareas de CPU (Independiente):** [CpuIntensiveTaskDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/CpuIntensiveTaskDemo.java)
* **Comparativa en Servidor Web (Independiente):** [HttpServerComparisonDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/virtual/src/main/java/org/example/HttpServerComparisonDemo.java)
