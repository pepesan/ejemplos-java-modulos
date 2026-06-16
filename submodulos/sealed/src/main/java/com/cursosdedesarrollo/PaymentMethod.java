package com.cursosdedesarrollo;

/**
 * Ejemplo de Sealed Interface (Java 17 - JEP 409).
 * - Permite restringir qué clases o interfaces pueden implementarla.
 * - Los implementadores pueden ser:
 *   - final (no extensibles).
 *   - non-sealed (abiertos a extensión).
 *   - sealed (restringiendo a su vez sus sub-implementaciones).
 * - Los 'records' pueden implementar interfaces selladas. Al ser implícitamente final,
 *   los records satisfacen la restricción de sellado de forma limpia y directa.
 */
public sealed interface PaymentMethod permits CreditCard, Paypal, BankTransfer {

    void pay(double amount);
}
