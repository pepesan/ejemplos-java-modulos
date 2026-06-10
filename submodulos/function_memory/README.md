# function_memory — Foreign Function & Memory API (Panama)

**Java 22** (estable)

## Qué cubre

La API Foreign Function & Memory (FFM) permite llamar a funciones nativas (C/C++) y gestionar memoria fuera del heap de la JVM directamente desde Java, sin JNI.

## Ejemplo incluido

Llamada a la función `strlen` de la librería C estándar desde Java:

```java
Linker linker = Linker.nativeLinker();
MethodHandle strlen = linker.downcallHandle(
    linker.defaultLookup().find("strlen").orElseThrow(),
    FunctionDescriptor.of(JAVA_LONG, ADDRESS)
);
try (Arena arena = Arena.ofConfined()) {
    MemorySegment cString = arena.allocateFrom("Hola FFM Java 22!");
    long len = (long) strlen.invoke(cString);
}
```

## Conceptos clave

| Concepto | Descripción |
|----------|-------------|
| `Linker` | Puente entre Java y el ABI nativo del sistema operativo |
| `SymbolLookup` | Localiza símbolos (funciones) en bibliotecas nativas |
| `FunctionDescriptor` | Describe la firma de la función C en términos de tipos Java |
| `MethodHandle` | Handle invocable que representa la función nativa |
| `Arena` | Gestiona el ciclo de vida de la memoria nativa (libera al cerrar) |
| `MemorySegment` | Bloque de memoria nativa tipado y con límites seguros |

## Clase de test

`com.cursosdedesarrollo.AppTest`
