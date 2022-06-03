package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest13_Parametrized {

    @Nested
    class PruebasParametrizadasTest{

        @ParameterizedTest(name = "No. de prueba  {index}, ejecutando con valor {0} - {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "500", "700", "1000.12345"})//@ValueSource(doubles = {100, 200, 300, 500, 700, 1000.12345})
        void testDebitoCuentaValueSource(String monto) {
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1001.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "No. de prueba {index}, ejecutando con valor {0} - {argumentsWithNames}")
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
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            cuenta.setPersona(actual);

            assertNotNull(cuenta.getPersona());
            assertEquals(esperado, actual);
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "Numero del *.csv {index}, ejecutando con valor {0} - {argumentsWithNames}")
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
            Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        static List<String> montoList(){
            return Arrays.asList("100.50", "200", "300", "500", "700", "1000.12345");
        }

    }


}