package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest04_BancoCuentas {

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Eliel", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Eliel", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

        assertEquals(2, banco.getCuentas().size());  // Prueba que el banco tiene 2 cuentas
        assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());
        //Los sig. assert son equivalentes
        assertEquals("Eliel", banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Eliel"))
                .findFirst()
                .get()
                .getPersona());
        assertTrue(banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Eliel"))
                .findFirst().isPresent());
        assertTrue(banco.getCuentas().stream()
                .anyMatch(c -> c.getPersona().equals("Eliel")));
    }

}