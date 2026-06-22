// Tarea 3: Usar importación de módulo (Module Imports)
import module java.base;

public class Java25Exercise {

    // Tarea 1: Flexible Constructor Bodies
    // Permitir validación/cálculo de parámetros antes de llamar a super(...)
    public static class CuentaBancaria {
        private final double saldoInicial;

        public CuentaBancaria(double saldoInicial) {
            this.saldoInicial = saldoInicial;
        }

        public double getSaldoInicial() {
            return saldoInicial;
        }
    }

    public static class CuentaBancariaLimitada extends CuentaBancaria {
        private final double limiteCredito;

        public CuentaBancariaLimitada(double saldoInicial, double limiteCredito) {
            // Lógica de validación antes del super constructor (flexible constructors en Java 22/23/25)
            if (saldoInicial < 0) {
                throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
            }
            if (limiteCredito < 100) {
                // Ajustamos el límite antes de invocar a super
                limiteCredito = 100;
            }
            super(saldoInicial); // Invocación obligatoria pero retrasada
            this.limiteCredito = limiteCredito;
        }

        public double getLimiteCredito() {
            return limiteCredito;
        }
    }

    /**
     * Tarea 2: Structured Concurrency.
     * Ejecuta dos llamadas concurrentes simuladas y concatena sus resultados.
     */
    public static String ejecutarTareasEstructuradas() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            var subtask1 = scope.fork(() -> {
                Thread.sleep(10);
                return "Servicio A";
            });
            var subtask2 = scope.fork(() -> {
                Thread.sleep(15);
                return "Servicio B";
            });

            // Une e invoca a join(), que lanza si alguna falla
            scope.join();

            return subtask1.get() + " & " + subtask2.get();
        }
    }

    /**
     * Tarea 4: Nuevo método main de instancia implícitamente declarado o con firma simplificada.
     * Retorna una cadena simple.
     */
    public String saludarDesdeInstancia() {
        return "Hola desde Java 25";
    }
}
