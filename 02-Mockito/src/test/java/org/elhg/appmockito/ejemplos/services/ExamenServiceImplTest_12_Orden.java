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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Forma 02 => Habilitar annotations para este test
class ExamenServiceImplTest_12_Orden {

    @Mock
    ExamenRepository repository;

    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @Test
    void testOrdenDeInvocaciones2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);

        service.findExamenPorNombreConPreguntas("Matematicas");
        service.findExamenPorNombreConPreguntas("Lenguaje");

        InOrder inOrder = inOrder(repository, preguntaRepository);
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(5L);
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(6L);
    }

}