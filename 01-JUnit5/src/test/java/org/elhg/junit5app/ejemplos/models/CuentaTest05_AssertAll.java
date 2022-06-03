package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

class CuentaTest05_AssertAll {

    @Test
    void testRelacionBancoCuentasAssertAll() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Eliel", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        // () -> {}, ()->{}, ()->{}, ()->{}
        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                        () -> "El saldo de la cuenta2, no es el esperado"),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                        () -> "El saldo de la cuenta1, no es el esperado"),
                () -> assertEquals(2, banco.getCuentas().size(),
                        () -> "Banco no tiene las cuentas esperadas"),
                () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                () -> assertEquals("Eliel", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Eliel"))
                        .findFirst()
                        .get()
                        .getPersona()),
                () -> assertTrue(banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Eliel"))
                        .findFirst().isPresent()),
                () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Eliel"))));
    }

}