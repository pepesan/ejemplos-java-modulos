package com.cursosdedesarrollo;

// final: nadie puede extender CheckingAccount
public final class CheckingAccount extends Account {

    private final double overdraftLimit;

    public CheckingAccount(String owner, double balance, double overdraftLimit) {
        super(owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double overdraftLimit() { return overdraftLimit; }
}
