package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest14_Tag {

    @Tag("param") // Scope Class or method,  Execute only tag, Edit Configurations tags => param
    @Nested
    class PruebasParametrizadasTest{
        @ParameterizedTest(name = "numero {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "500", "700", "1000.12345"})
            //@ValueSource(doubles = {100, 200, 300, 500, 700, 1000.12345})
        void testDebitoCuentaValueSource(String monto) {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000.12345"})
        void testDebitoCuentaCsvSource(String index,String monto) {
            System.out.println(index +" -> "+ monto);
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"200,100,Jhon,Andres", "250,200,Pepe,Pepe", "300,300,Maria,maria", "510,500,Pepa,Pepa", "750,700,Lucas,Luca", "1000.12345,1000.12345,Ray,Ray"})
        void testDebitoCuentaCsvSource2(String saldo, String monto, String esperado, String actual) {
            System.out.println(saldo +" -> "+ monto);
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            cuenta.setPersona(actual);

            assertNotNull(cuenta.getPersona());
            assertEquals(esperado, actual);
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "numero del *.csv {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data.csv")
        void testDebitoCuentaCsvFileSource(String monto) {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero del *.csv {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data2.csv")
        void testDebitoCuentaCsvFileSource2(String saldo, String monto, String esperado, String actual) {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            cuenta.setPersona(actual);

            assertNotNull(cuenta.getPersona());
            assertEquals(esperado, actual);
            assertNotNull(cuenta.getSaldo());

            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }
        @ParameterizedTest(name = "method numero {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @MethodSource("montoList")
        void testDebitoCuentaMethodSource(String monto) {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        static List<String> montoList(){
            return Arrays.asList("100", "200", "300", "500", "700", "1000.12345");
        }

    }



    @Tag("cuenta")
    @Nested
    @DisplayName("Probando los atributos de la cuenta")
    class CuentaTestNombreSaldo {
        @Test
        // @Disabled  //Inhabilita el metodo, salta la ejecucion de esta prueba
        @DisplayName("Prueba para nombre de cuenta")
        void testNombreCuenta() {
            //fail(); //Introduce una falla en el metodo
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
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
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            assertNotNull(cuenta.getSaldo());  // Que el saldo no sea nulo
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);  //Saldo > 0, es true, porque es +

        }

        @Test
        @DisplayName("Prueba que las referencias sean iguales con el metodo eqauals")
        void testReferenciaCuenta() {
            Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
            Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
//        assertNotEquals(cuentaReal, cuentaEsperado);
            assertEquals(cuenta2, cuenta);
        }

    }

}