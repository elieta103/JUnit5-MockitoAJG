package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.models.Examen;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Forma 02 => Habilitar annotations para este test
class ExamenServiceImplTest_05_AnnotationsInjection {

    @Mock
    ExamenRepository repository;

    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        // Forma 01 => Habilitar annotations para este test
        //MockitoAnnotations.openMocks(this);
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