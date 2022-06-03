package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;


class CuentaTest10_Assumptions {


    @Test
    @DisplayName("Assumption. Prueba para el saldo de la cuenta")
    void testSaldoCuentaDev() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev); // Si se cumple, ejecuta el test, sino se deshabilita
        assertNotNull(cuenta.getSaldo());  // Que el saldo no sea nulo
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);  //Saldo > 0, es true, porque es +

    }

    @Test
    @DisplayName("AssumptionThat . Prueba para el saldo de la cuenta")
    void testSaldoCuentaDev2() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        System.out.println(System.getProperty("ENV"));
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        boolean esProd = "prod".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            System.out.println("Dentro del bloque assumingThat dev");
            assertNotNull(cuenta.getSaldo());  // Que el saldo no sea nulo
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        });
        assumingThat(esProd, () -> {
            System.out.println("Dentro del bloque assumingThat prod");
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);  //Saldo > 0, es true, porque es +
        });

    }
}