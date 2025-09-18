package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import static java.lang.foreign.ValueLayout.*;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    void testFFM() {
        // java.lang.IllegalArgumentException: Cannot open library: c
        /*
        Linker linker = Linker.nativeLinker();
        SymbolLookup libc = SymbolLookup.libraryLookup("c", Arena.global());
        MethodHandle strlen = linker.downcallHandle(
                libc.find("strlen").orElseThrow(),
                FunctionDescriptor.of(JAVA_LONG, ADDRESS) // size_t strlen(const char*)
        );

        try (Arena arena = Arena.ofConfined()) {
            // Arena implementa SegmentAllocator
            MemorySegment cstr = arena.allocateFrom("hola");
                long len = (long) strlen.invoke(cstr);
            System.out.println(len); // 4
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

         */
    }
}
