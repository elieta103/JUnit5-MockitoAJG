package org.elhg.appmockito.ejemplos.repositories;

import org.elhg.appmockito.ejemplos.Datos;

import java.util.List;

public class PreguntaRepositoryImpl implements  PreguntaRepository{

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaRepositoryImpl.guardarVarias()");

    }

    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        System.out.println("PreguntaRepositoryImpl.findPreguntasPorExamenId");
        return Datos.PREGUNTAS;
    }
}
