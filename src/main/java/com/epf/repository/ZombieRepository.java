package com.epf.repository;

import com.epf.model.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieRepository {
    // CRUD Operations
    List<Zombie> findAll();  // Récupérer tous les zombies
    Optional<Zombie> findById(Long id);  // Récupérer un zombie par son ID
    Zombie save(Zombie zombie);  // Ajouter un nouveau zombie
    Zombie update(Long id, Zombie zombie);  // Mettre à jour un zombie existant
    boolean delete(Long id);  // Supprimer un zombie par son ID

    // Méthode spécifique : Récupérer les zombies par ID de map
    List<Zombie> findByMapId(Long idMap);  // Récupérer tous les zombies pour une carte donnée
}
