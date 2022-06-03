package org.elhg.test.springboot.app;

import org.elhg.test.springboot.app.models.Cuenta;
import org.elhg.test.springboot.app.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// I  M  P  O  R  T  A  N  T  E
// MARCAR CARPETA test/resources como : Test Resources Root

@DataJpaTest
public class SpringbootTestRepositories {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andrés",cuenta.orElseThrow().getPersona());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andrés");
        assertTrue(cuenta.isPresent());
        assertEquals("Andrés",cuenta.orElseThrow().getPersona());
        assertEquals("1000.00",cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByPersonaThrowException() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Rod");
        //assertThrows(NoSuchElementException.class, cuenta::orElseThrow);  //Lo mismo abreviado
        assertThrows(NoSuchElementException.class, ()->{
            cuenta.orElseThrow();
        });
        assertFalse(cuenta.isPresent());
        assertTrue(!cuenta.isPresent());
    }

    @Test
    void testFindAll(){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty()); //Que no este vacio
        assertEquals(2,cuentas.size());
    }

    @Test
    void testSave() {
        //Given  Dado un contexto
        Cuenta cuentaPepe = new Cuenta(null, "Pepe",  new BigDecimal("3000"));
        cuentaRepository.save(cuentaPepe);

        //When  Cuando
        Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();

        //Then Entonces
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
        //assertEquals(3, cuenta.getId());  //No recomendable porque pueden variar los id guardados en BD
    }

    @Test
    void testUpdate() {
        //Given  Dado un contexto
        Cuenta cuentaPepe = new Cuenta(null, "Pepe",  new BigDecimal("3000"));
        cuentaRepository.save(cuentaPepe);

        //When  Cuando
        Cuenta cuenta = cuentaRepository.findByPersona("Pepe").orElseThrow();

        //Then Entonces
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
        //assertEquals(3, cuenta.getId());  //No recomendable porque pueden variar los id guardados en BD

        //When
        cuenta.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        //Then Entonces
        assertEquals("Pepe", cuentaActualizada.getPersona());
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());

    }


    @Test
    void testDelete() {
        //Given
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Jhon", cuenta.getPersona());

        //When
        cuentaRepository.delete(cuenta);

        //Then
        assertThrows(NoSuchElementException.class, ()->{
           // cuentaRepository.findById(2L).orElseThrow();
            cuentaRepository.findByPersona("Jhon").orElseThrow();
        });
        assertEquals(1, cuentaRepository.findAll().size());
    }
}
