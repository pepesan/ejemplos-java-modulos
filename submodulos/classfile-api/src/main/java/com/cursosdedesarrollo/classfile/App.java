package com.cursosdedesarrollo.classfile;

import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.FieldModel;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Ejemplos de la Class-File API (JEP 484 — estable en Java 24).
 *
 * <p>Proporciona una API estándar para leer, construir y transformar ficheros
 * {@code .class} sin dependencias externas (ASM, Javassist, BCEL).
 * Está en {@code java.base}, por lo que no requiere flags adicionales.
 *
 * <p>Tres operaciones principales:
 * <ol>
 *   <li><b>Parse</b> — leer un {@code .class} y navegar su modelo de objetos.</li>
 *   <li><b>Build</b> — generar un {@code .class} completo desde cero.</li>
 *   <li><b>Transform</b> — modificar un {@code .class} existente de forma incremental.</li>
 * </ol>
 */
public class App {

    public static void main(String[] args) throws Exception {
        demoParse();
        demoBuild();
        demoTransform();
    }

    // -------------------------------------------------------------------------
    // 1. PARSE — leer el .class de esta misma clase y mostrar su estructura
    // -------------------------------------------------------------------------
    static void demoParse() throws Exception {
        System.out.println("=== Parse: inspeccionar App.class ===");

        // Localiza el .class compilado de esta propia clase
        String ruta = App.class.getName().replace('.', '/') + ".class";
        byte[] bytecode = App.class.getClassLoader().getResourceAsStream(ruta).readAllBytes();

        ClassFile cf = ClassFile.of();
        ClassModel modelo = cf.parse(bytecode);

        System.out.println("Clase:       " + modelo.thisClass().name().stringValue());
        System.out.println("Versión:     " + modelo.majorVersion()
                + " (Java " + (modelo.majorVersion() - 44) + ")");
        System.out.println("Superclase:  " + modelo.superclass()
                .map(e -> e.name().stringValue()).orElse("ninguna"));

        System.out.println("Campos:");
        for (FieldModel f : modelo.fields()) {
            System.out.println("  " + f.flags().flags() + " " +
                    f.fieldName().stringValue() + " : " + f.fieldType().stringValue());
        }

        System.out.println("Métodos:");
        for (MethodModel m : modelo.methods()) {
            System.out.println("  " + m.methodName().stringValue()
                    + m.methodType().stringValue());
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 2. BUILD — generar desde cero un .class con un método que imprime texto
    //
    // Genera el equivalente bytecode de:
    //
    //   public class SaludoGenerado {
    //       public static void saludar(String nombre) {
    //           System.out.println("Hola, " + nombre + "!");
    //       }
    //   }
    // -------------------------------------------------------------------------
    static void demoBuild() throws Exception {
        System.out.println("=== Build: generar SaludoGenerado.class ===");

        ClassDesc cdSaludo     = ClassDesc.of("com.cursosdedesarrollo.classfile.SaludoGenerado");
        ClassDesc cdSystem     = ClassDesc.of("java.lang.System");
        ClassDesc cdPrintStream = ClassDesc.of("java.io.PrintStream");
        ClassDesc cdString     = ClassDesc.of("java.lang.String");

        MethodTypeDesc firmaMain    = MethodTypeDesc.of(ConstantDescs.CD_void, cdString);
        MethodTypeDesc firmaPrintln = MethodTypeDesc.of(ConstantDescs.CD_void, cdString);

        ClassFile cf = ClassFile.of();
        byte[] bytes = cf.build(cdSaludo, clb -> {
            clb.withFlags(AccessFlag.PUBLIC);
            clb.withMethod("saludar", firmaMain, ClassFile.ACC_PUBLIC | ClassFile.ACC_STATIC, mb ->
                mb.withCode(cb -> {
                    // StringBuilder sb = new StringBuilder();
                    cb.new_(ClassDesc.of("java.lang.StringBuilder"));
                    cb.dup();
                    cb.invokespecial(ClassDesc.of("java.lang.StringBuilder"), "<init>",
                            MethodTypeDesc.of(ConstantDescs.CD_void));

                    // sb.append("Hola, ")
                    cb.ldc("Hola, ");
                    cb.invokevirtual(ClassDesc.of("java.lang.StringBuilder"), "append",
                            MethodTypeDesc.of(ClassDesc.of("java.lang.StringBuilder"), cdString));

                    // sb.append(nombre)
                    cb.aload(0);
                    cb.invokevirtual(ClassDesc.of("java.lang.StringBuilder"), "append",
                            MethodTypeDesc.of(ClassDesc.of("java.lang.StringBuilder"), cdString));

                    // sb.append("!")
                    cb.ldc("!");
                    cb.invokevirtual(ClassDesc.of("java.lang.StringBuilder"), "append",
                            MethodTypeDesc.of(ClassDesc.of("java.lang.StringBuilder"), cdString));

                    // String resultado = sb.toString()
                    cb.invokevirtual(ClassDesc.of("java.lang.StringBuilder"), "toString",
                            MethodTypeDesc.of(cdString));

                    // System.out.println(resultado)
                    cb.getstatic(cdSystem, "out", cdPrintStream);
                    cb.swap();
                    cb.invokevirtual(cdPrintStream, "println", firmaPrintln);
                    cb.return_();
                })
            );
        });

        // Carga dinámicamente la clase generada y llama al método
        var loader = new ByteArrayClassLoader(bytes);
        Class<?> clazz = loader.loadClass("com.cursosdedesarrollo.classfile.SaludoGenerado");
        clazz.getMethod("saludar", String.class).invoke(null, "Mundo");

        // También la guardamos en disco para poder inspeccionarla
        Path destino = Path.of(System.getProperty("java.io.tmpdir"), "SaludoGenerado.class");
        Files.write(destino, bytes);
        System.out.println("Clase generada (" + bytes.length + " bytes) guardada en: " + destino);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 3. TRANSFORM — eliminar de una clase todos los métodos privados
    //
    // Lee el .class de esta misma App, aplica una transformación que descarta
    // los MethodModel con el flag PRIVATE, y verifica el resultado.
    // -------------------------------------------------------------------------
    static void demoTransform() throws Exception {
        System.out.println("=== Transform: eliminar métodos privados de App.class ===");

        String ruta = App.class.getName().replace('.', '/') + ".class";
        byte[] original = App.class.getClassLoader().getResourceAsStream(ruta).readAllBytes();

        ClassFile cf = ClassFile.of();

        // ClassTransform que descarta los elementos que sean métodos privados
        ClassTransform sinPrivados = ClassTransform.dropping(
            e -> e instanceof MethodModel m && m.flags().has(AccessFlag.PRIVATE)
        );

        byte[] transformado = cf.transformClass(cf.parse(original), sinPrivados);

        // Comparar número de métodos antes y después
        long antesPrivados  = cf.parse(original).methods().stream()
                .filter(m -> m.flags().has(AccessFlag.PRIVATE)).count();
        long despuesPrivados = cf.parse(transformado).methods().stream()
                .filter(m -> m.flags().has(AccessFlag.PRIVATE)).count();

        System.out.println("Tamaño original:    " + original.length    + " bytes");
        System.out.println("Tamaño transformado: " + transformado.length + " bytes");
        System.out.println("Métodos privados antes:  " + antesPrivados);
        System.out.println("Métodos privados después: " + despuesPrivados);
        System.out.println("Errores de verificación: " + cf.verify(transformado).size());
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // ClassLoader auxiliar para cargar clases generadas en memoria
    // -------------------------------------------------------------------------
    static class ByteArrayClassLoader extends ClassLoader {
        private final byte[] bytes;
        ByteArrayClassLoader(byte[] bytes) { this.bytes = bytes; }

        @Override
        protected Class<?> findClass(String name) {
            return defineClass(name, bytes, 0, bytes.length);
        }
    }
}
