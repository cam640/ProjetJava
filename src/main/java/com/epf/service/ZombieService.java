package com.epf.service;

import com.epf.model.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieService {
    List<Zombie> findAll();
    Optional<Zombie>findById(Long id);
    Zombie save(Zombie zombie);
    Zombie update(Long id, Zombie zombie);
    boolean delete(Long id);
    List<Zombie> findByMapId(Long idMap);
}

