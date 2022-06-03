package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.models.Examen;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepositoryImpl;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepository;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Forma 02 => Habilitar annotations para este test
class ExamenServiceImplTest_11_Spy {

    @Mock
    ExamenRepositoryImpl repository;

    @Mock
    PreguntaRepositoryImpl preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
    }




    @Test
    void testSpy() {
        //Spy invoca implementaciones reales, No utilizar when con spy
        ExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);

        ExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);
        List<String> preguntas = Arrays.asList("aritmetica");
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);

        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = examenService.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(5, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));

        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }


}