package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CuentaTest12_Repeated {
    @DisplayName("Probando repetir metodo")
    @RepeatedTest(value = 5, name = "{displayName} Repetición {currentRepetition} de {totalRepetitions}")
    void testDebitoCuentaRepetir(RepetitionInfo info) {
        if (info.getCurrentRepetition() == 3) {
            System.out.println("En repeticion 3: " + info.getCurrentRepetition());
        }
        Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }
}