package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.models.Examen;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepositoryImpl;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static  org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest_01_Basic {

    @Test
    void findExamenPorNombre() {
        ExamenRepository repository = mock(ExamenRepository.class);
        ExamenServiceImpl service = new ExamenServiceImpl(repository, null);
        List<Examen> datos =  Arrays.asList(new Examen(5L,"Matematicas"),
                new Examen(6L,"Lenguajes"),
                new Examen(7L,"Historia"));

        when(repository.findAll()).thenReturn(datos);

        Optional <Examen> optionalExamen = service.findExamenPorNombre("Matematicas");

        assertTrue(optionalExamen.isPresent());
        assertEquals(5L, optionalExamen.orElseThrow().getId());
        assertEquals("Matematicas", optionalExamen.orElseThrow().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {
        ExamenRepository repository = mock(ExamenRepository.class);
        ExamenServiceImpl service = new ExamenServiceImpl(repository, null);
        List<Examen> datos = Collections.emptyList();

        when(repository.findAll()).thenReturn(datos);

        Optional <Examen> optionalExamen = service.findExamenPorNombre("Matematicas");

        assertFalse(optionalExamen.isPresent());

    }
}