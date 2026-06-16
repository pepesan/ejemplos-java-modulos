package com.cursosdedesarrollo;

/**
 * Clase que extiende de InvestmentAccount.
 * - Dado que InvestmentAccount fue declarada 'non-sealed', EtfAccount puede extenderla
 *   sin necesidad de declarar 'final', 'sealed' o 'non-sealed' (es una clase de herencia convencional).
 * - Aunque no forma parte del conjunto sellado original de Account, al ser un subtipo de
 *   InvestmentAccount, es capturada correctamente en las comprobaciones polimórficas.
 */
public class EtfAccount extends InvestmentAccount {

    private final String isin;

    public EtfAccount(String owner, double balance, String portfolio, String isin) {
        super(owner, balance, portfolio);
        this.isin = isin;
    }

    public String isin() { return isin; }
}
