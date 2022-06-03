package org.elhg.appmockito.ejemplos.repositories;

import org.elhg.appmockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen guardar (Examen examen);
    List<Examen> findAll();
}
