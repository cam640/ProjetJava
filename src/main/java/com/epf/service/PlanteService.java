package com.epf.service;

import com.epf.model.Plante;
import java.util.List;
import java.util.Optional;

public interface PlanteService {
    List<Plante> findAll();
    Optional<Plante> findById(Long id);
    Plante save(Plante plante);
    Plante update(Long id, Plante plante);
    boolean delete(Long id);
}



