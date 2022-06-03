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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Forma 02 => Habilitar annotations para este test
class ExamenServiceImplTest_10_doAlgo {

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
    void testDoThrow() {
        //Metodos cuyo tipo de retorno es void, ejem. guardarVarias es void
        doThrow(IllegalArgumentException.class).when(preguntaRepository).guardarVarias(anyList());

        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);
        assertThrows(IllegalArgumentException.class, ()->{
            service.guardar(examen);
        });
    }

    @Test
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        //Equivale a la linea anterior comentada
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id==5L?Datos.PREGUNTAS: Collections.emptyList();
        }).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5L, examen.getId());
        assertTrue(examen.getPreguntas().contains("integrales"));
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(5L, examen.getPreguntas().size());

        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }


    @Test
    void testDoAnswerGuardarExamen() {
        //Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        doAnswer(new Answer<Examen>() {

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        }).when(repository).guardar(any(Examen.class));

        //When
        Examen examen = service.guardar(newExamen);

        //Then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }


    @Test
    void testDoCallRealMethod() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        //Mock
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        //Metodo real, en el fondo es la llamada a un metodo que no es Mock
        doCallRealMethod().when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(5L,examen.getId());
        assertEquals("Matematicas",examen.getNombre());
    }
}