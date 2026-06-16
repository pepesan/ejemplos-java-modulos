package com.cursosdedesarrollo;

import java.util.List;

public class App {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. Jerarquía sealed con clases (Account)
        //   Account (sealed abstract)
        //     ├── SavingsAccount   (final)
        //     ├── CheckingAccount  (final)
        //     └── InvestmentAccount (non-sealed → se puede seguir extendiendo)
        // ----------------------------------------------------------------
        System.out.println("--- 1. Jerarquía de clases selladas (Account) ---");
        List<Account> cuentas = List.of(
                new SavingsAccount("Ana",  5000.0, 0.03),
                new CheckingAccount("Luis", 1200.0, 500.0),
                new InvestmentAccount("Marta", 20000.0, "Renta Variable")
        );

        for (Account cuenta : cuentas) {
            System.out.println(describirCuenta(cuenta));
        }

        // Exhaustividad en switch con clases selladas
        System.out.println("\n--- Exhaustividad en switch (sin default) ---");
        Account cuenta = new SavingsAccount("Pedro", 3000.0, 0.025);
        double comision = switch (cuenta) {
            case SavingsAccount s  -> s.balance() * 0.001;
            case CheckingAccount c -> 5.0;
            case InvestmentAccount i -> i.balance() * 0.005;
        };
        System.out.printf("Comisión anual de %s: %.2f€%n", cuenta.owner(), comision);

        // Subtipo de clase non-sealed
        System.out.println("\n--- Subtipo de non-sealed (EtfAccount) ---");
        Account etf = new EtfAccount("Rosa", 8000.0, "ETF Global", "IE00B4L5Y983");
        System.out.println(describirCuenta(etf));


        // ----------------------------------------------------------------
        // 2. Jerarquía sealed con interfaces y records (PaymentMethod)
        //   PaymentMethod (sealed interface)
        //     ├── CreditCard   (record - implícitamente final)
        //     ├── Paypal       (final class)
        //     └── BankTransfer (non-sealed class)
        // ----------------------------------------------------------------
        System.out.println("\n--- 2. Jerarquía de interfaces selladas y records (PaymentMethod) ---");
        List<PaymentMethod> metodosPago = List.of(
                new CreditCard("1234-5678-9012-3456", "Juan Pérez"),
                new Paypal("juan.perez@email.com"),
                new BankTransfer("ES12 3456 7890 1234 5678 9012")
        );

        for (PaymentMethod metodo : metodosPago) {
            metodo.pay(100.0);
            System.out.println("Detalles: " + describirMetodoPago(metodo));
        }
    }

    // Switch con comprobación exhaustiva del compilador sobre una sealed class
    static String describirCuenta(Account a) {
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

    // Switch con comprobación exhaustiva del compilador sobre una sealed interface
    // Al cubrir Paypal, CreditCard y BankTransfer, el switch es exhaustivo (no requiere default).
    static String describirMetodoPago(PaymentMethod pm) {
        return switch (pm) {
            case CreditCard cc -> "Tarjeta de Crédito: " + cc.cardNumber();
            case Paypal p -> "Cuenta de Paypal: " + p.email();
            case BankTransfer bt -> "Transferencia Bancaria (IBAN): " + bt.iban();
        };
    }
}
