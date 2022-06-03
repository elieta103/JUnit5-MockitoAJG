package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest15_testInfo {
    Cuenta cuenta;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        this.cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
        testReporter.publishEntry("Iniciando metodo...");
        testReporter.publishEntry("Ejecutando : "+testInfo.getDisplayName()+" "+testInfo.getTestMethod().get().getName()
                +" con las etiquetas "+testInfo.getTags());

    }

    @Tag("cuenta")
    @Nested
    @DisplayName("Probando los atributos de la cuenta")
    class CuentaTestNombreSaldo {
        @Test
        @DisplayName("Prueba para nombre de cuenta")
        void testNombreCuenta() {
            //fail(); //Introduce una falla en el metodo
            testReporter.publishEntry(testInfo.getTags().toString());
            if(testInfo.getTags().contains("cuenta")){
                testReporter.publishEntry("hacer algo con la etiqueta cuenta");
            }
            String esperado = "Eliel";
            String real = cuenta.getPersona();
            assertNotNull(real, () -> "La cuenta no puede ser nula");
            assertEquals(esperado, real, () -> "El nombre de cuenta no es el esperado : " + esperado
                    + " sin embargo fue : " + real);
            assertTrue(real.equals("Eliel"), () -> "Nombre de cuenta esperada debe ser igual a la real");
        }

        @Test
        @DisplayName("Prueba para el saldo de la cuenta")
        void testSaldoCuenta() {
            assertNotNull(cuenta.getSaldo());  // Que el saldo no sea nulo
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);  //Saldo > 0, es true, porque es +

        }

        @Test
        @DisplayName("Prueba que las referencias sean iguales con el metodo eqauals")
        void testReferenciaCuenta() {
            cuenta = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
            Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
//        assertNotEquals(cuentaReal, cuentaEsperado);
            assertEquals(cuenta2, cuenta);
        }

    }
}