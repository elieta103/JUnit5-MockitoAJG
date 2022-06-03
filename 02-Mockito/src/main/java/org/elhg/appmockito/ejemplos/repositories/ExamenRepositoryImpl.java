package org.elhg.appmockito.ejemplos.repositories;

import org.elhg.appmockito.ejemplos.Datos;
import org.elhg.appmockito.ejemplos.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryImpl implements ExamenRepository{
    @Override
    public Examen guardar(Examen examen) {
        System.out.println("ExamenRepositoryImpl.guardar");
        return Datos.EXAMEN;
    }

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepositoryImpl.findAll()");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Datos.EXAMENES;
    }

}
