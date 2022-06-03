package org.elhg.junit5app.ejemplos.models;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest06_Mensajes {

    @Test
    void testNombreCuenta() {
        //La expression Lamda no consume espacio, solo se ejecuta si falla
        Cuenta cuenta = new Cuenta("Eliel", new BigDecimal(1000));
        String esperado = "Andres";
        String real = cuenta.getPersona();

        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre de cuenta no es el esperado : " + esperado
                + " sin embargo fue : " + real);
        assertTrue(real.equals("Eliel"), () -> "Nombre de cuenta esperada debe ser igual a la real");
    }
}