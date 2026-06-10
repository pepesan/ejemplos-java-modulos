# vectorapi — Vector API (SIMD)

**Java 25** (incubator → estable en proceso)

## Qué cubre

La Vector API permite expresar operaciones SIMD (Single Instruction, Multiple Data) que la JVM puede compilar a instrucciones vectoriales nativas del procesador (AVX2, AVX-512, ARM Neon), logrando sumas, productos y operaciones sobre arrays mucho más rápidas que los bucles escalares.

## Estado actual

El código de la Vector API está **comentado** en este módulo porque la API ha pasado por varias fases de incubator con cambios de paquete (`jdk.incubator.vector`). El ejemplo sirve como referencia del patrón de uso.

## Patrón del ejemplo (comentado)

```java
// Suma escalar (sin vectorización)
for (int i = 0; i < a.length; i++) c[i] = a[i] + b[i];

// Suma vectorizada (SIMD)
var upperBound = SPECIES.loopBound(a.length);
for (int i = 0; i < upperBound; i += SPECIES.length()) {
    var va = IntVector.fromArray(SPECIES, a, i);
    var vb = IntVector.fromArray(SPECIES, b, i);
    va.add(vb).intoArray(c, i);
}
// Resto escalar para elementos que no llenan un vector completo
```

## Clase principal

`com.cursosdedesarrollo.App`
