package com.cursosdedesarrollo;

/**
 * Subclase final que extiende la clase sellada Account.
 * - Al ser declarada 'final', no puede ser heredada por ninguna otra clase.
 * - Esta es una de las tres opciones obligatorias para los subtipos de una jerarquía sellada
 *   (final, sealed o non-sealed).
 */
public final class CheckingAccount extends Account {

    private final double overdraftLimit;

    public CheckingAccount(String owner, double balance, double overdraftLimit) {
        super(owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double overdraftLimit() { return overdraftLimit; }
}
