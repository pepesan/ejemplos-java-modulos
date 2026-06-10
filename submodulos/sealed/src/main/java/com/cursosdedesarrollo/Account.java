package com.cursosdedesarrollo;

// sealed: solo SavingsAccount, CheckingAccount e InvestmentAccount
// pueden extender esta clase. Cualquier otra clase lo tiene prohibido
// en tiempo de compilación.
// abstract: no se puede instanciar Account directamente.
public sealed abstract class Account permits SavingsAccount, CheckingAccount, InvestmentAccount {

    private final String owner;
    private double balance;

    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public String owner()   { return owner; }
    public double balance() { return balance; }

    // Intento de extensión fuera de la lista permits:
    //   class FraudAccount extends Account { }  <-- ERROR de compilación
}
