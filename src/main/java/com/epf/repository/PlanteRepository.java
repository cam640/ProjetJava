package com.epf.repository;

import com.epf.model.Plante;
import java.util.List;
import java.util.Optional;

public interface PlanteRepository {
    // CRUD Operations
    List<Plante> findAll();
    Optional<Plante> findById(Long id);  // Récupérer une plante par son ID
    Plante save(Plante plante);
    Plante update(Long id, Plante plante);
    boolean delete(Long id);  // Supprimer une plante par son ID
}

