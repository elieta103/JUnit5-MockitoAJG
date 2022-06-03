package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CuentaTest11_Nested {

    @Nested
    @DisplayName("Probando variables de ambiente")
    class VariablesAmbienteTest {
        @Test
        void testImprimirVariablesAmbiente() {
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k, v) -> System.out.println(k + " = " + v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-14.0.2.*")
        void testJavaHome() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
        void testProcesadores() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
        void testEnv() {
        }

    }

    @Nested
    @DisplayName("Probando attributos de la cuenta")
    class CuentaOperacionesTest {
        @Tag("cuenta")
        @Test
        void testDebitoCuenta() {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }

        @Tag("cuenta")
        @Test
        void testCreditoCuenta() {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue());
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Tag("cuenta")
        @Tag("banco")
        @Test
        void testTransferirDineroCuentas() {
            Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
            Cuenta cuenta2 = new Cuenta("Eliel", new BigDecimal("1500.8989"));
            Banco banco = new Banco();
            banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }

    }


}