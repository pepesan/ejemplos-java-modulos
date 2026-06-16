package com.cursosdedesarrollo;

/**
 * Clase non-sealed (no sellada) que implementa la interfaz sellada PaymentMethod.
 * - Al declararse 'non-sealed', permite que cualquier otra clase del proyecto
 *   o de librerías externas herede de ella libremente.
 */
public non-sealed class BankTransfer implements PaymentMethod {
    private final String iban;

    public BankTransfer(String iban) {
        this.iban = iban;
    }

    public String iban() {
        return iban;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Pagando %.2f€ mediante transferencia bancaria al IBAN %s%n", amount, iban);
    }
}
