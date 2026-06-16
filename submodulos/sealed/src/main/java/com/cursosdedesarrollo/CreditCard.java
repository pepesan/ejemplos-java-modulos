package com.cursosdedesarrollo;

/**
 * Implementación de la interfaz sellada PaymentMethod utilizando un Record.
 * - En Java, los Records son implícitamente clases 'final' inmutables.
 * - Por tanto, al implementar una interfaz sellada (sealed interface), satisfacen de manera
 *   natural y limpia la restricción del compilador sin necesidad de declarar 'final' explícitamente.
 */
public record CreditCard(String cardNumber, String holder) implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.printf("Pagando %.2f€ con tarjeta de crédito %s (Titular: %s)%n", amount, cardNumber, holder);
    }
}
