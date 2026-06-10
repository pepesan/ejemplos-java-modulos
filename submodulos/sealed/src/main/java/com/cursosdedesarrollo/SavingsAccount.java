package com.cursosdedesarrollo;

// final: nadie puede extender SavingsAccount
public final class SavingsAccount extends Account {

    private final double interestRate;

    public SavingsAccount(String owner, double balance, double interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }

    public double interestRate() { return interestRate; }
}
