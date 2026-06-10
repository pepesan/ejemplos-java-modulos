package com.cursosdedesarrollo;

// Extiende InvestmentAccount (non-sealed) sin ninguna restricción.
// Esta clase está fuera de la jerarquía sellada original,
// pero puede participar en el switch porque es un subtipo de InvestmentAccount.
public class EtfAccount extends InvestmentAccount {

    private final String isin;

    public EtfAccount(String owner, double balance, String portfolio, String isin) {
        super(owner, balance, portfolio);
        this.isin = isin;
    }

    public String isin() { return isin; }
}
