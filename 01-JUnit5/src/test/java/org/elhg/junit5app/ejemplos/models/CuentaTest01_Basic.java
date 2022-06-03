package org.elhg.junit5app.ejemplos.models;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest01_Basic {
    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal(1000));
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);   //Saldo > 0, es true, porque es +
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
        //assertNotEquals(cuenta2, cuenta1);  //OK, mientras no se implemente el equals
        assertEquals(cuenta2, cuenta1);       //OK, mientras se implemente el equals
    }


}