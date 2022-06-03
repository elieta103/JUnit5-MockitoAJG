package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.models.Examen;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest_02_BeforeEach {

    ExamenRepository repository;
    PreguntaRepository preguntaRepository;
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepository.class);
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {
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
        List<Examen> datos = Collections.emptyList();

        when(repository.findAll()).thenReturn(datos);

        Optional <Examen> optionalExamen = service.findExamenPorNombre("Matematicas");

        assertFalse(optionalExamen.isPresent());

    }


    @Test
    void testPreguntaExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));
    }
}