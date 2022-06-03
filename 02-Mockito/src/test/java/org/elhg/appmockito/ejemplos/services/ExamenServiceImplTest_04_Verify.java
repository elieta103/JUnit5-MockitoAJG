package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.models.Examen;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ExamenServiceImplTest_04_Verify {

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
    void testPreguntaExamenVerify() {
        //Given
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        //When
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        //Then
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));

        //Verify
        verify(repository).findAll(); //Si se llamó el method findAll de repository
        verify(preguntaRepository).findPreguntasPorExamenId(5L); //Solo se llama solo si hay examenes
    }

    @Test
    void testNoExisteExamenVerify() {
        //Given
        when(repository.findAll()).thenReturn(Datos.EXAMENES_VACIO);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        //When
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        //Then
        assertNull(examen);

        //Verify
        verify(repository).findAll(); //Si se llamó el method findAll de repository
        verify(preguntaRepository).findPreguntasPorExamenId(5L); //Solo se llama solo si hay examenes
    }
}