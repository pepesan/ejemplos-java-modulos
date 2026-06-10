package com.cursosdedesarrollo;

// non-sealed: rompe el sellado a partir de aquí.
// Cualquier clase puede extender InvestmentAccount libremente,
// lo que permite a terceros (librerías, frameworks) añadir subtipos.
public non-sealed class InvestmentAccount extends Account {

    private final String portfolio;

    public InvestmentAccount(String owner, double balance, String portfolio) {
        super(owner, balance);
        this.portfolio = portfolio;
    }

    public String portfolio() { return portfolio; }
}
