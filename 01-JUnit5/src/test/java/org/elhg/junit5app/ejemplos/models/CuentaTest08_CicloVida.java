package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest08_CicloVida {
    Cuenta cuenta;

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll()... Iniciando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll()... Finalizando el test");
    }

    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        this.cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
        System.out.println("Inicializando metodo de prueba...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando metodo de prueba...");
    }

    @Test
    void testNombreCuenta(){
        String esperado = "Eliel";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
    }

    @Test
    void testSaldoCuenta(){

        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);   //Saldo > 0, es true, porque es +
    }
}