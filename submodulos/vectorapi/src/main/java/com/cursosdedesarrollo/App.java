package com.cursosdedesarrollo;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import java.util.Arrays;
import java.util.random.RandomGenerator;

/**
 * Ejemplos de la Vector API (JEP 517 — incubator en Java 25).
 *
 * <p>La Vector API permite expresar operaciones SIMD (Single Instruction,
 * Multiple Data) de forma portable: el compilador JIT las traduce a
 * instrucciones vectoriales nativas (AVX-512, SVE, NEON…) cuando el hardware
 * lo soporta.
 *
 * <p>Conceptos clave:
 * <ul>
 *   <li>{@code VectorSpecies} — describe el tipo de elemento y el tamaño del vector.</li>
 *   <li>{@code SPECIES_PREFERRED} — elige automáticamente el ancho óptimo para la CPU actual.</li>
 *   <li>{@code loopBound} — calcula cuántos elementos caben en iteraciones "completas".</li>
 *   <li>{@code VectorMask} + {@code indexInRange} — maneja el tramo final del array
 *       cuando su longitud no es múltiplo del ancho del vector.</li>
 * </ul>
 *
 * <p>Compilar con: {@code --add-modules jdk.incubator.vector}
 */
public class App {

    // SPECIES_PREFERRED delega en la JVM la elección del ancho óptimo
    private static final VectorSpecies<Integer> INT_SPECIES   = IntVector.SPECIES_PREFERRED;
    private static final VectorSpecies<Float>   FLOAT_SPECIES = FloatVector.SPECIES_PREFERRED;

    public static void main(String[] args) {
        System.out.println("Vector API — JEP 517 (incubator, Java 25)");
        System.out.printf("  Ancho preferido int:   %d bits (%d elementos)%n",
                INT_SPECIES.vectorBitSize(), INT_SPECIES.length());
        System.out.printf("  Ancho preferido float: %d bits (%d elementos)%n%n",
                FLOAT_SPECIES.vectorBitSize(), FLOAT_SPECIES.length());

        demoSumaArrays();
        demoProductoPunto();
        demoMaximoConMascara();
        demoBenchmark();
    }

    // --- Suma de dos arrays --------------------------------------------------
    // Patrón básico: bucle "completo" con vectores + tramo final con máscara.
    static void demoSumaArrays() {
        System.out.println("=== Suma de arrays (int) ===");
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] b = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int[] escalar   = sumaEscalar(a, b);
        int[] vectorial = sumaVectorial(a, b);

        System.out.println("Escalar:   " + Arrays.toString(escalar));
        System.out.println("Vectorial: " + Arrays.toString(vectorial));
        System.out.println("Iguales:   " + Arrays.equals(escalar, vectorial));
        System.out.println();
    }

    static int[] sumaEscalar(int[] a, int[] b) {
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    static int[] sumaVectorial(int[] a, int[] b) {
        int[] c   = new int[a.length];
        int   len = a.length;
        int   i   = 0;

        // Bucle principal: procesa tantos elementos como caben en el vector
        for (int bound = INT_SPECIES.loopBound(len); i < bound; i += INT_SPECIES.length()) {
            IntVector va = IntVector.fromArray(INT_SPECIES, a, i);
            IntVector vb = IntVector.fromArray(INT_SPECIES, b, i);
            va.add(vb).intoArray(c, i);
        }

        // Tramo final: elementos sobrantes (array no múltiplo del ancho del vector)
        if (i < len) {
            VectorMask<Integer> mask = INT_SPECIES.indexInRange(i, len);
            IntVector.fromArray(INT_SPECIES, a, i, mask)
                     .add(IntVector.fromArray(INT_SPECIES, b, i, mask))
                     .intoArray(c, i, mask);
        }
        return c;
    }

    // --- Producto punto (dot product) de float[] ----------------------------
    // Multiplica elemento a elemento y reduce (suma) el vector resultante.
    static void demoProductoPunto() {
        System.out.println("=== Producto punto (float) ===");
        float[] u = {1f, 2f, 3f, 4f};
        float[] v = {4f, 3f, 2f, 1f};

        float escalar   = productoPuntoEscalar(u, v);
        float vectorial = productoPuntoVectorial(u, v);

        System.out.printf("Escalar:   %.1f%n", escalar);
        System.out.printf("Vectorial: %.1f%n", vectorial);
        System.out.printf("Iguales:   %b%n%n", escalar == vectorial);
    }

    static float productoPuntoEscalar(float[] a, float[] b) {
        float suma = 0f;
        for (int i = 0; i < a.length; i++) suma += a[i] * b[i];
        return suma;
    }

    static float productoPuntoVectorial(float[] a, float[] b) {
        int   len  = a.length;
        int   i    = 0;
        float suma = 0f;

        for (int bound = FLOAT_SPECIES.loopBound(len); i < bound; i += FLOAT_SPECIES.length()) {
            FloatVector va = FloatVector.fromArray(FLOAT_SPECIES, a, i);
            FloatVector vb = FloatVector.fromArray(FLOAT_SPECIES, b, i);
            // reduceLanes(ADD) suma todos los carriles del vector en un escalar
            suma += va.mul(vb).reduceLanes(VectorOperators.ADD);
        }
        // Tramo final escalar
        for (; i < len; i++) suma += a[i] * b[i];
        return suma;
    }

    // --- Máximo con máscara --------------------------------------------------
    // Calcula el máximo de un array cuya longitud no es múltiplo del vector.
    static void demoMaximoConMascara() {
        System.out.println("=== Máximo con máscara (int) ===");
        // Array de longitud "irregular" para forzar el uso de máscara
        int[] datos = {3, 7, 1, 9, 4, 6, 2, 8, 5};

        int escalar   = maximoEscalar(datos);
        int vectorial = maximoVectorial(datos);

        System.out.println("Escalar:   " + escalar);
        System.out.println("Vectorial: " + vectorial);
        System.out.println("Iguales:   " + (escalar == vectorial));
        System.out.println();
    }

    static int maximoEscalar(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int v : a) if (v > max) max = v;
        return max;
    }

    static int maximoVectorial(int[] a) {
        int len = a.length;
        int i   = 0;
        // Vector inicializado con el menor valor posible
        IntVector vmax = IntVector.broadcast(INT_SPECIES, Integer.MIN_VALUE);

        for (int bound = INT_SPECIES.loopBound(len); i < bound; i += INT_SPECIES.length()) {
            IntVector v = IntVector.fromArray(INT_SPECIES, a, i);
            vmax = vmax.max(v);
        }
        // Reducción: máximo de todos los carriles
        int max = vmax.reduceLanes(VectorOperators.MAX);

        // Tramo final escalar
        for (; i < len; i++) if (a[i] > max) max = a[i];
        return max;
    }

    // --- Benchmark simplificado ---------------------------------------------
    // Compara el tiempo de suma de dos arrays grandes (escalar vs vectorial).
    //
    // NOTA: en una suma simple el JIT aplica auto-vectorización al bucle escalar,
    // por lo que los tiempos pueden ser similares o incluso invertidos en este
    // microbenchmark naive. Para medir con precisión usar JMH.
    // El beneficio real de la Vector API se aprecia en operaciones más complejas
    // que el JIT no puede auto-vectorizar de forma automática.
    static void demoBenchmark() {
        System.out.println("=== Benchmark: suma de 1.000.000 ints ===");
        int n   = 1_000_000;
        var rng = RandomGenerator.getDefault();
        int[] a = new int[n], b = new int[n];
        for (int i = 0; i < n; i++) { a[i] = rng.nextInt(100); b[i] = rng.nextInt(100); }

        // Calentamiento JIT
        for (int w = 0; w < 5; w++) { sumaEscalar(a, b); sumaVectorial(a, b); }

        long t0 = System.nanoTime();
        int[] re = sumaEscalar(a, b);
        long te = System.nanoTime() - t0;

        long t1 = System.nanoTime();
        int[] rv = sumaVectorial(a, b);
        long tv = System.nanoTime() - t1;

        System.out.printf("Escalar:   %,d µs%n", te / 1_000);
        System.out.printf("Vectorial: %,d µs%n", tv / 1_000);
        System.out.printf("Factor:    %.1fx%n", (double) te / tv);
        System.out.println("Resultados iguales: " + Arrays.equals(re, rv));
    }
}
