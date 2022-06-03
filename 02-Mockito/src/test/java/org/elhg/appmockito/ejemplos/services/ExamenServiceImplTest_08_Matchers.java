package org.elhg.appmockito.ejemplos.services;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.repositories.ExamenRepository;
import org.elhg.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Forma 02 => Habilitar annotations para este test
class ExamenServiceImplTest_08_Matchers {

    @Mock
    ExamenRepository repository;

    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
    }


    @Test
    void testArgumentsMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        //Se realiza , para hacer mas especifico y personalizable en el comprobador de argumentos
        //verify(preguntaRepository).findPreguntasPorExamenId(5L);
        verify(preguntaRepository).findPreguntasPorExamenId(eq(5L));
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg->arg != null && arg.equals(5L)));
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg->arg != null && arg >= 5L));
    }

    @Test
    void testArgumentsMatchersClass() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        //Se realiza , para hacer mas especifico y personalizable en el comprobador de argumentos
        //verify(preguntaRepository).findPreguntasPorExamenId(5L);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentsMatchersExpLamda() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        //Se realiza , para hacer mas especifico y personalizable en el comprobador de argumentos
        //verify(preguntaRepository).findPreguntasPorExamenId(5L);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat((argument)->argument != null && argument > 0));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {
        private Long argument;
        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }
        @Override
        public String toString() {
            return "MiArgsMatchers{ Mensaje personalizado de error de la clase, " +
                    "se imprime cuando falla el test}  " +
                    argument+" debe ser positivo";
        }
    }
}