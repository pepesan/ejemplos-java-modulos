import module java.base;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Java25ExerciseTest {

    @Test
    public void testFlexibleConstructor() {
        // Test de inicialización correcta
        Java25Exercise.CuentaBancariaLimitada cuenta = new Java25Exercise.CuentaBancariaLimitada(500.0, 50.0);
        assertEquals(500.0, cuenta.getSaldoInicial());
        assertEquals(100.0, cuenta.getLimiteCredito()); // El constructor debe ajustarlo a 100

        // Test de excepción lanzada antes de super()
        assertThrows(IllegalArgumentException.class, () -> {
            new Java25Exercise.CuentaBancariaLimitada(-10.0, 200.0);
        });
    }

    @Test
    public void testStructuredConcurrency() throws Exception {
        String resultado = Java25Exercise.ejecutarTareasEstructuradas();
        assertEquals("Servicio A & Servicio B", resultado);
    }

    @Test
    public void testInstanciaSaludar() {
        Java25Exercise app = new Java25Exercise();
        assertEquals("Hola desde Java 25", app.saludarDesdeInstancia());
    }
}
