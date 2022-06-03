package org.elhg.junit5app.ejemplos.models;

import org.elhg.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest03_OperacionesException {
    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Eliel", new BigDecimal("1000.12345"));
        Exception ex = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));  //Metodo que va lanzar la exception
        });

        String actual = ex.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }


}