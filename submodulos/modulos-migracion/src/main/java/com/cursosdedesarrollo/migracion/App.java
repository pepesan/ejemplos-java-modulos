package com.cursosdedesarrollo.migracion;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulamiento fuerte (Strong Encapsulation) y migración de librerías.
 *
 * CONTEXTO HISTÓRICO
 * ──────────────────
 * Antes de Java 9 cualquier código podía usar reflexión para acceder
 * a campos y métodos privados de CUALQUIER clase, incluidas las del JDK.
 * Frameworks como Hibernate, Jackson, Spring o Lombok lo explotaban
 * masivamente para:
 *   - Serializar/deserializar objetos sin getters/setters
 *   - Inyectar dependencias en campos privados (@Autowired)
 *   - Generar código en tiempo de compilación (Lombok)
 *   - Instrumentar bytecode (agentes Java)
 *
 * EVOLUCIÓN DEL ENCAPSULAMIENTO EN JAVA
 * ──────────────────────────────────────
 * Java  9–15  Encapsulamiento fuerte disponible pero NO por defecto.
 *             Se emitían advertencias (WARNING) al acceder a internos del JDK.
 *             --illegal-access=warn (por defecto) / deny / debug / permit
 *
 * Java 16+    Encapsulamiento fuerte ACTIVADO por defecto.
 *             --illegal-access eliminado. Ya no se emiten advertencias:
 *             el acceso simplemente lanza InaccessibleObjectException.
 *
 * Java 17+    Sin opción de retroceso. La única vía es --add-opens.
 *
 * SOLUCIONES
 * ──────────
 * 1. --add-opens  (parche temporal, línea de comandos o pom.xml)
 *       Abre un paquete del JDK para reflexión desde código sin módulo.
 *       Ejemplo: --add-opens java.base/java.util=ALL-UNNAMED
 *
 * 2. opens en module-info.java  (solución limpia para código modular)
 *       Si TU código ya tiene module-info.java puedes declarar
 *       qué paquetes propios abres para reflexión de otros módulos.
 *       No controla paquetes del JDK; eso sigue requiriendo --add-opens.
 *
 * 3. Actualizar la librería  (solución definitiva)
 *       Las versiones modernas de Hibernate (6+), Jackson (2.12+),
 *       Spring (6+) y Lombok (1.18.22+) ya no dependen de reflexión
 *       sobre internos del JDK.
 */
public class App {

    public static void main(String[] args) {
        demoAccesoPrivadoClasePropia();
        demoAccesoPrivadoClaseJDK();
        demoAccesoClaseNoExportada();
        demoSimulacionFramework();
    }

    // ====================================================================
    // ESCENARIO 1: reflexión sobre clase propia (unnamed module)
    // ====================================================================
    // El código sin module-info.java vive en el "módulo sin nombre"
    // (unnamed module). Ese módulo está abierto a sí mismo para reflexión,
    // por lo que acceder a campos privados de tus propias clases SIEMPRE
    // funciona, igual que antes de Java 9.
    // ====================================================================
    static void demoAccesoPrivadoClasePropia() {
        System.out.println("=== 1. Reflexión sobre clase propia (siempre funciona) ===");

        try {
            Producto p = new Producto("Teclado", 49.99);

            Field campoNombre = Producto.class.getDeclaredField("nombre");
            campoNombre.setAccessible(true); // OK: clase en unnamed module
            System.out.println("Campo 'nombre' leído por reflexión: " + campoNombre.get(p));

            Field campoPrecio = Producto.class.getDeclaredField("precio");
            campoPrecio.setAccessible(true);
            campoPrecio.set(p, 39.99); // modificación del campo privado
            System.out.println("Campo 'precio' modificado por reflexión: " + campoPrecio.get(p));

        } catch (Exception e) {
            System.out.println("ERROR inesperado: " + e.getMessage());
        }
    }

    // ====================================================================
    // ESCENARIO 2: reflexión sobre campo privado de clase del JDK
    // ====================================================================
    // ArrayList.elementData es el array interno que almacena los elementos.
    // Antes de Java 16 frameworks como Kryo o serializers personalizados
    // accedían a él directamente para serializar colecciones sin copias.
    //
    // En Java 16+ lanza InaccessibleObjectException porque java.util
    // está en el módulo java.base, que no abre sus paquetes por defecto.
    //
    // Workaround en línea de comandos:
    //   java --add-opens java.base/java.util=ALL-UNNAMED -jar mi-app.jar
    //
    // Workaround en Maven (exec-maven-plugin, ver pom.xml):
    //   <jvmArg>--add-opens</jvmArg>
    //   <jvmArg>java.base/java.util=ALL-UNNAMED</jvmArg>
    // ====================================================================
    static void demoAccesoPrivadoClaseJDK() {
        System.out.println("\n=== 2. Reflexión sobre campo privado de clase del JDK ===");

        List<String> lista = new ArrayList<>();
        lista.add("Java");
        lista.add("Módulos");

        try {
            // ArrayList guarda sus elementos en un campo privado 'elementData'
            Field elementData = ArrayList.class.getDeclaredField("elementData");

            // En Java 16+ esta línea lanza InaccessibleObjectException
            // a menos que se añada: --add-opens java.base/java.util=ALL-UNNAMED
            elementData.setAccessible(true);

            Object[] array = (Object[]) elementData.get(lista);
            System.out.println("Acceso concedido. Contenido interno: ");
            for (Object o : array) {
                if (o != null) System.out.println("  " + o);
            }

        } catch (InaccessibleObjectException e) {
            // Este es el error que obtienen las librerías antiguas en Java 16+
            System.out.println("BLOQUEADO por encapsulamiento fuerte:");
            System.out.println("  " + e.getMessage());
            System.out.println();
            System.out.println("Workaround (temporal):");
            System.out.println("  java --add-opens java.base/java.util=ALL-UNNAMED -jar app.jar");
            System.out.println();
            System.out.println("Solución definitiva:");
            System.out.println("  Actualizar a una versión del framework que no use reflexión interna.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    // ====================================================================
    // ESCENARIO 3: acceso a paquete NO exportado por el JDK
    // ====================================================================
    // Los paquetes sun.*, com.sun.*, jdk.internal.* no están exportados.
    // En Java 8 eran accesibles directamente (aunque desaconsejados).
    // En Java 9+ son invisibles a menos que se usen flags especiales.
    //
    // --add-exports  → necesario para que el COMPILADOR vea el paquete
    // --add-opens    → necesario para que la REFLEXIÓN funcione en runtime
    //
    // Para compilar código que referencie sun.misc.Unsafe directamente
    // habría que añadir: --add-exports java.base/sun.misc=ALL-UNNAMED
    // Por eso aquí usamos Class.forName() para evitar la referencia en
    // tiempo de compilación y centrar el ejemplo en el error de runtime.
    // ====================================================================
    static void demoAccesoClaseNoExportada() {
        System.out.println("\n=== 3. Acceso a paquete no exportado (sun.misc) ===");

        try {
            // Class.forName localiza la clase en runtime sin referencia directa
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");

            // Para obtener la instancia de Unsafe hace falta acceder
            // a su campo privado 'theUnsafe', que requiere:
            //   --add-opens java.base/sun.misc=ALL-UNNAMED
            Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true); // InaccessibleObjectException en Java 16+

            Object unsafe = theUnsafe.get(null);
            System.out.println("sun.misc.Unsafe obtenido: " + unsafe.getClass().getName());
            System.out.println("(Esto era habitual en librerías de alto rendimiento)");

        } catch (InaccessibleObjectException e) {
            System.out.println("BLOQUEADO: " + e.getMessage());
            System.out.println("Requiere: --add-opens java.base/sun.misc=ALL-UNNAMED");
            System.out.println("Nota: sun.misc.Unsafe está disponible como");
            System.out.println("  java.lang.foreign (Panama API) desde Java 22.");

        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    // ====================================================================
    // ESCENARIO 4: simulación de lo que hace un framework de serialización
    // ====================================================================
    // Muestra el patrón exacto que usan Hibernate, Jackson y similares
    // para leer todos los campos de un objeto (incluidos privados)
    // sin requerir getters. Es el núcleo del problema de migración.
    //
    // Con clases propias sigue funcionando; el problema aparece cuando
    // el framework intenta hacer lo mismo con clases del JDK internamente.
    // ====================================================================
    static void demoSimulacionFramework() {
        System.out.println("\n=== 4. Simulación de serialización por reflexión (patrón framework) ===");

        Pedido pedido = new Pedido(101, "Ana García", List.of("Teclado", "Ratón"), 89.98);

        System.out.println("Objeto a serializar: " + pedido);
        System.out.println("Campos leídos por reflexión (como haría un framework):");

        // Recorre todos los campos declarados, incluidos los privados
        for (Field campo : Pedido.class.getDeclaredFields()) {
            try {
                campo.setAccessible(true); // funciona: Pedido está en unnamed module
                System.out.printf("  %-12s = %s%n", campo.getName(), campo.get(pedido));
            } catch (IllegalAccessException e) {
                System.out.println("  " + campo.getName() + " = [inaccesible]");
            }
        }

        System.out.println();
        System.out.println("Impacto real en librerías conocidas:");
        System.out.println("  Hibernate  < 5.4  → accedía a internos de java.lang.reflect");
        System.out.println("  Jackson    < 2.12 → usaba sun.reflect.ReflectionFactory");
        System.out.println("  Spring     < 5.3  → accedía a campos de java.util.Properties");
        System.out.println("  Lombok     < 1.18.22 → manipulaba el ClassLoader interno");
        System.out.println();
        System.out.println("Solución recomendada: actualizar a versiones modernas.");
        System.out.println("Solución temporal: añadir los --add-opens necesarios al arranque.");
    }

    // ====================================================================
    // REFERENCIA: directiva 'opens' en module-info.java
    // ====================================================================
    // Si TU librería tiene module-info.java y quieres que otros módulos
    // puedan acceder a tus paquetes por reflexión, declara:
    //
    //   module com.miempresa.milibreria {
    //       exports com.miempresa.api;          // acceso público normal
    //       opens   com.miempresa.modelo;       // abre para reflexión (a todos)
    //       opens   com.miempresa.interno       // abre solo a Jackson
    //           to com.fasterxml.jackson.databind;
    //   }
    //
    // 'exports' → API pública, acceso a tipos y miembros públicos
    // 'opens'   → acceso reflexivo completo (también privados/protegidos)
    //
    // Los módulos del JDK (java.base, java.sql, etc.) NO pueden modificarse,
    // por eso su apertura requiere --add-opens en línea de comandos.
    // ====================================================================

    // Clases auxiliares de ejemplo
    static class Producto {
        private String nombre;
        private double precio;

        Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }
    }

    record Pedido(int id, String cliente, List<String> articulos, double total) {}
}
