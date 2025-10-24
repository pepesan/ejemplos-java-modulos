package com.cursosdedesarrollo;

/// # Calculadora
/// Utilidad **sencilla** para operaciones aritméticas.
///
/// - Soporta suma y operaciones triviales.
/// - Documentación en _Markdown_ dentro del comentario.
/// - Enlace: [JEP 467](https://openjdk.org/jeps/467)
///
/// ## Uso rápido
/// ```java
/// var calc = new Calculadora();
/// int r = calc.sumar(2, 3); // 5
/// ```
///
/// @author David Vaquero
/// @since 23
public class Calculadora {

    /// ## Constructor
    /// Crea una calculadora sin estado.
    public Calculadora() { }

    /// ## sumar
    /// Suma dos enteros y devuelve el resultado.
    ///
    /// **Detalles**
    /// - ✔ Sin comprobación de *overflow* (igual que `+`).
    /// - Ejemplo:
    ///   ```java
    ///   new Calculadora().sumar(10, 5); // 15
    ///   ```
    ///
    /// @param a primer sumando
    /// @param b segundo sumando
    /// @return la suma de `a` y `b`
    /// @see Calculadora#sumar(int, int)
    public int sumar(int a, int b) {
        return a + b;
    }

    /// ## doble
    /// Duplica el valor indicado.
    ///
    /// Lista de comprobación
    /// - • Entrada válida (cualquier `int`)
    /// - • Operación determinista
    ///
    /// @param x valor a duplicar
    /// @return `x * 2`
    public int doble(int x) {
        return x * 2;
    }
}
