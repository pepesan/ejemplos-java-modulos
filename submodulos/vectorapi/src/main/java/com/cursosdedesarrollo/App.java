package com.cursosdedesarrollo;

/* import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;


 */
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App
{
    /*
    // ejemplo de suma de arrays
    public static int[] simpleSum(int[] a, int[] b) {
        var c = new int[a.length];
        for (var i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }    return c;
    }
    // ejemplo usando Vector API paralelizado
    private static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    public static int[] vectorSum(int[] a, int[] b) {
        var c = new int[a.length];
        var upperBound = SPECIES.loopBound(a.length);

        var i = 0;
        for (i = 0; i < upperBound; i += SPECIES.length()) {
            var va = IntVector.fromArray(SPECIES, a, i);
            var vb = IntVector.fromArray(SPECIES, b, i);
            var vc = va.add(vb);
            vc.intoArray(c, i);
        }    // Compute elements not fitting in the vector alignment.
        for (; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }

        return c;

    }
    public static void printArray(int [] a){
        for (int i :
                a) {
            System.out.println(i);
        }
    }

     */
    public static void main( String[] args )
    {
        System.out.println( "Vector API example" );
        // llamada a ejemplo normal
        int[] a = new int[2];
        Arrays.fill(a, 2);
        int[] b = new int[2];
        Arrays.fill(b, 1);
        /*
        int[] c = simpleSum(a, b);
        printArray(c);

         */

    }
}
