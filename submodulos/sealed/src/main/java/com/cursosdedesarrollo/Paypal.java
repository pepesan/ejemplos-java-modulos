package com.cursosdedesarrollo;

/**
 * Clase final que implementa la interfaz sellada PaymentMethod.
 * - Al declararse 'final', impide cualquier tipo de extensión de esta clase.
 */
public final class Paypal implements PaymentMethod {
    private final String email;

    public Paypal(String email) {
        this.email = email;
    }

    public String email() {
        return email;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Pagando %.2f€ con cuenta de Paypal %s%n", amount, email);
    }
}
