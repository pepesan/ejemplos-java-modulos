package com.cursosdedesarrollo;

/**
 * Subclase final que extiende la clase sellada Account.
 * - Al estar marcada como 'final', cierra esta rama de la jerarquía de herencia.
 */
public final class SavingsAccount extends Account {

    private final double interestRate;

    public SavingsAccount(String owner, double balance, double interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }

    public double interestRate() { return interestRate; }
}
