package com.cursosdedesarrollo;

import java.util.List;

public class App {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // Jerarquía sealed:
        //   Account (sealed abstract)
        //     ├── SavingsAccount   (final)
        //     ├── CheckingAccount  (final)
        //     └── InvestmentAccount (non-sealed → se puede seguir extendiendo)
        // ----------------------------------------------------------------

        List<Account> cuentas = List.of(
                new SavingsAccount("Ana",  5000.0, 0.03),
                new CheckingAccount("Luis", 1200.0, 500.0),
                new InvestmentAccount("Marta", 20000.0, "Renta Variable")
        );

        for (Account cuenta : cuentas) {
            System.out.println(describir(cuenta));
        }

        // ----------------------------------------------------------------
        // Exhaustividad: el compilador conoce todos los subtipos permitidos
        // y garantiza que el switch los cubre todos.
        // Si añadimos un nuevo subtipo en 'permits' y NO lo añadimos aquí,
        // el código NO compilará. Eso es el valor principal de sealed.
        // ----------------------------------------------------------------
        System.out.println("\n--- Exhaustividad sin default ---");
        Account cuenta = new SavingsAccount("Pedro", 3000.0, 0.025);
        // El switch siguiente NO necesita 'default': el compilador sabe
        // que solo existen SavingsAccount, CheckingAccount e InvestmentAccount.
        double comision = switch (cuenta) {
            case SavingsAccount s  -> s.balance() * 0.001;
            case CheckingAccount c -> 5.0;
            case InvestmentAccount i -> i.balance() * 0.005;
        };
        System.out.printf("Comisión anual de %s: %.2f€%n", cuenta.owner(), comision);

        // ----------------------------------------------------------------
        // non-sealed en acción: InvestmentAccount puede ser extendida
        // por cualquier clase externa a la jerarquía sellada.
        // ----------------------------------------------------------------
        System.out.println("\n--- Subtipo de non-sealed ---");
        Account etf = new EtfAccount("Rosa", 8000.0, "ETF Global", "IE00B4L5Y983");
        // En el switch sobre Account, EtfAccount coincide con el case InvestmentAccount
        // porque es un subtipo de ella.
        System.out.println(describir(etf));
    }

    static String describir(Account a) {
        return switch (a) {
            case SavingsAccount s ->
                    String.format("[Ahorro] %s | saldo: %.2f€ | interés: %.1f%%",
                            s.owner(), s.balance(), s.interestRate() * 100);
            case CheckingAccount c ->
                    String.format("[Corriente] %s | saldo: %.2f€ | descubierto hasta: %.2f€",
                            c.owner(), c.balance(), c.overdraftLimit());
            case InvestmentAccount i ->
                    String.format("[Inversión] %s | saldo: %.2f€ | cartera: %s",
                            i.owner(), i.balance(), i.portfolio());
        };
    }
}
