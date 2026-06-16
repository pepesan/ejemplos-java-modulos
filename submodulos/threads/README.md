# threads — Hilos tradicionales y Coordinación en Java

Este submódulo ejemplifica el uso tradicional de hilos (Platform Threads) y los mecanismos clásicos y modernos de coordinación y sincronización entre ellos en Java.

## Qué cubre

El submódulo aborda las siguientes áreas esenciales de la concurrencia en Java:

1. **Creación y Ciclo de Vida de Hilos** ([ThreadCreationDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/threads/src/main/java/com/cursosdedesarrollo/threads/ThreadCreationDemo.java)):
   - Creación de hilos heredando de `Thread`.
   - Creación mediante la interfaz funcional `Runnable`.
   - Uso de expresiones lambda y bloques anónimos.
   - Creación mediante el constructor de hilos de plataforma (`Thread.ofPlatform()`).
   - Gestión de hilos demonio (`Daemon Threads`).
   - Interrupción de hilos (`interrupt()` y captura de `InterruptedException`).
   - Control con `start()` y `join()`.

2. **Coordinación Clásica: Productor-Consumidor** ([ClassicProducerConsumer.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/threads/src/main/java/com/cursosdedesarrollo/threads/ClassicProducerConsumer.java)):
   - Sincronización intrínseca de Java utilizando la palabra clave `synchronized`.
   - Control de flujo utilizando la coordinación clásica de monitores con `wait()` y `notifyAll()`.
   - Evitación de despertares espurios (*spurious wakeups*) mediante la evaluación continua de condiciones en un bucle `while`.

3. **Coordinación Moderna: Locks y Condiciones** ([LockConditionDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/threads/src/main/java/com/cursosdedesarrollo/threads/LockConditionDemo.java)):
   - Sincronización explícita utilizando la interfaz `Lock` y su implementación `ReentrantLock`.
   - Uso de múltiples condiciones independientes (`Condition` creadas vía `lock.newCondition()`) asociadas a un mismo bloqueo, permitiendo una señalización más eficiente y selectiva (`await()` y `signal()`) en lugar de despertar a todos los hilos indiscriminadamente.
   - Garantía de liberación del bloqueo a través de bloques `try-finally`.

4. **Ayudantes de Concurrencia de Alto Nivel** ([ConcurrencyHelpersDemo.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/threads/src/main/java/com/cursosdedesarrollo/threads/ConcurrencyHelpersDemo.java)):
   - **CountDownLatch**: Para sincronizar el arranque o la inicialización coordinando varios hilos que deben terminar antes de que el hilo controlador continúe (`latch.await()` y `latch.countDown()`).
   - **CyclicBarrier**: Para sincronizar fases o etapas de un grupo fijo de hilos, obligándoles a esperarse en un punto común de barrera antes de continuar todos juntos (`barrier.await()`).
   - **Semaphore**: Para limitar el acceso concurrente a un recurso escaso o pool finito de recursos mediante la adquisición y liberación de permisos (`semaphore.acquire()` y `semaphore.release()`).

## Ejecución del Ejemplo

Para compilar y ejecutar todo el flujo secuencial de demostraciones, utiliza los siguientes comandos de Maven desde el directorio raíz del proyecto:

```bash
# Compilar el submódulo
mvn compile -pl submodulos/threads

# Ejecutar la clase principal
mvn exec:java -Dexec.mainClass="com.cursosdedesarrollo.threads.App" -pl submodulos/threads
```

La clase principal del submódulo es [App.java](file:///home/pepesan/IdeaProjects/ejemplos-java-modulosv2/submodulos/threads/src/main/java/com/cursosdedesarrollo/threads/App.java).
