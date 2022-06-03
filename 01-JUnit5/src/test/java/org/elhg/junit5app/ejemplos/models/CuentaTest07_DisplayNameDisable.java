package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest07_DisplayNameDisable {

    @Disabled
    @Test
    @DisplayName("Probando nombre de la cuenta")
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal(1000));
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
    }

    @Disabled
    @Test
    @DisplayName("Probando saldo de la cuenta")
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);  //Saldo < 0, es false, porque es +
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);   //Saldo > 0, es true, porque es +
    }

    @Disabled
    @Test
    @DisplayName("Probando referencia de la cuenta")
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1000.123456"));
        //assertNotEquals(cuenta2, cuenta1);  //OK, mientras no se implemente el equals
        assertEquals(cuenta2, cuenta1);       //OK, mientras se implemente el equals
    }

}