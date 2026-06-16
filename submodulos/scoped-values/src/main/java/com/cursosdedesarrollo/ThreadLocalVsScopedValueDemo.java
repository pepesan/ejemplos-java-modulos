package com.cursosdedesarrollo;

import java.lang.ScopedValue;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;

/**
 * Ejemplo comparativo de ThreadLocal vs ScopedValue en Java 21+.
 * 
 * Este programa demuestra:
 * 1. El uso y la mutabilidad/peligros de ThreadLocal.
 * 2. La inmutabilidad, seguridad y ligereza de ScopedValue.
 * 3. Explicación del impacto en la memoria de los hilos virtuales.
 */
public class ThreadLocalVsScopedValueDemo {

    // 1. ThreadLocal clásico (mutable, peligro de fuga si no se llama a .remove() en pools de hilos)
    private static final ThreadLocal<String> THREAD_LOCAL_CTX = new ThreadLocal<>();

    // 2. ScopedValue moderno (inmutable, ciclo de vida delimitado por el bloque de ejecución)
    private static final ScopedValue<String> SCOPED_VALUE_CTX = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("======================================================================");
        System.out.println("COMPARATIVA: THREADLOCAL VS SCOPEDVALUE");
        System.out.println("======================================================================\n");

        demostrarThreadLocalPeligroYMutacion();
        demostrarScopedValueSeguridad();
        explicarImpactoMemoriaHilosVirtuales();
    }

    /**
     * Demostración de ThreadLocal.
     * Muestra cómo los valores se pueden mutar inesperadamente a lo largo del flujo 
     * y el peligro de fuga de contexto si el hilo se reutiliza (como en pools de hilos tradicionales).
     */
    private static void demostrarThreadLocalPeligroYMutacion() throws InterruptedException {
        System.out.println("--- 1. Demostración de ThreadLocal (Peligro y Mutabilidad) ---");

        // Simulación de una petición en un pool de hilos
        Runnable requestTask = () -> {
            try {
                THREAD_LOCAL_CTX.set("Peticion-ID-999");
                System.out.println("  [ThreadLocal] Inicio de tarea. Contexto guardado: " + THREAD_LOCAL_CTX.get());

                // Mutabilidad: Cualquier método secundario puede modificar el valor accidentalmente
                mutarThreadLocalAccidentalmente();

                System.out.println("  [ThreadLocal] Fin de tarea. Contexto final: " + THREAD_LOCAL_CTX.get() 
                        + " (¡Ha cambiado inesperadamente!)");
                
                // NOTA CRÍTICA: Si olvidamos removerlo, el hilo vuelve al pool con este contexto adjunto.
                // THREAD_LOCAL_CTX.remove(); // Fuga de memoria si se comenta
            } finally {
                // Siempre se debe llamar a remove() en un bloque finally
                THREAD_LOCAL_CTX.remove();
            }
        };

        Thread t = new Thread(requestTask);
        t.start();
        t.join();
        System.out.println();
    }

    private static void mutarThreadLocalAccidentalmente() {
        // Un método de utilidades lejano modifica el ThreadLocal sin que la llamada principal lo prevea
        THREAD_LOCAL_CTX.set("VALOR-MUTADO-CORRUPTO");
    }

    /**
     * Demostración de ScopedValue.
     * Demuestra cómo la inmutabilidad evita modificaciones accidentales
     * y cómo la recolección de basura del contexto ocurre automáticamente al salir del ámbito (scope).
     */
    private static void demostrarScopedValueSeguridad() {
        System.out.println("--- 2. Demostración de ScopedValue (Seguridad e Inmutabilidad) ---");

        // Iniciamos el ámbito con un binding específico
        ScopedValue.where(SCOPED_VALUE_CTX, "Peticion-ID-555").run(() -> {
            System.out.println("  [ScopedValue] Inicio de ámbito. Contexto: " + SCOPED_VALUE_CTX.get());

            // Intentar modificar el ScopedValue no es posible porque no tiene método .set()!
            // Es intrínsecamente inmutable.
            intentarMutarScopedValue();

            System.out.println("  [ScopedValue] Fin de ámbito. Contexto final: " + SCOPED_VALUE_CTX.get() 
                    + " (Permanece intacto y seguro)");
        });

        // Fuera del bloque .run(), el ScopedValue ya no está ligado
        System.out.println("  [ScopedValue] Fuera del ámbito. ¿Está ligado?: " + SCOPED_VALUE_CTX.isBound());
        System.out.println();
    }

    private static void intentarMutarScopedValue() {
        // No hay método SCOPED_VALUE_CTX.set("otro");
        // Para cambiar el valor en sub-operaciones se debe realizar un "Rebinding" (sombreado/shadowing)
        // que solo afecta a la llamada anidada, sin alterar el ámbito original.
        ScopedValue.where(SCOPED_VALUE_CTX, "Peticion-ID-SOMBRA").run(() -> {
            System.out.println("    [ScopedValue-Sombra] Dentro del sub-ámbito: " + SCOPED_VALUE_CTX.get());
        });
    }

    /**
     * Explicación teórica del impacto en memoria cuando se combinan con Hilos Virtuales.
     */
    private static void explicarImpactoMemoriaHilosVirtuales() {
        System.out.println("--- 3. Impacto en Memoria con Hilos Virtuales (Teórico) ---");
        System.out.println("  * Con ThreadLocal:");
        System.out.println("    Cada hilo tiene su propio mapa ThreadLocalMap interno.");
        System.out.println("    Si creamos 1,000,000 de hilos virtuales y cada uno usa un ThreadLocal,");
        System.out.println("    se asignarán 1,000,000 de mapas de variables locales en memoria.");
        System.out.println("    Esto degrada drásticamente la memoria disponible y destruye la ligereza de Loom.");
        System.out.println();
        System.out.println("  * Con ScopedValue:");
        System.out.println("    No se asigna un mapa por hilo. Los valores se almacenan en una estructura compartida");
        System.out.println("    ligada a la ejecución del ámbito. Si varios hilos virtuales heredan o comparten");
        System.out.println("    el contexto de una operación, solo hay una referencia al valor en memoria.");
        System.out.println("    Esto hace que ScopedValue sea óptimo y seguro para aplicaciones a gran escala con Hilos Virtuales.");
    }
}
