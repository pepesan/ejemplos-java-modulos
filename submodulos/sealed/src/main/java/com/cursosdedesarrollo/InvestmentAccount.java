package com.cursosdedesarrollo;

/**
 * Subclase non-sealed (no sellada) que extiende la clase sellada Account.
 * - Al declararse 'non-sealed', rompe la restricción de sellado a partir de esta clase.
 * - Cualquier clase puede extender libremente de InvestmentAccount (como hace EtfAccount),
 *   permitiendo puntos de extensión abiertos en la jerarquía si fuera necesario.
 */
public non-sealed class InvestmentAccount extends Account {

    private final String portfolio;

    public InvestmentAccount(String owner, double balance, String portfolio) {
        super(owner, balance);
        this.portfolio = portfolio;
    }

    public String portfolio() { return portfolio; }
}
