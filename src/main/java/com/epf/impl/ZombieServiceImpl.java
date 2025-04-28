package com.epf.impl;

import com.epf.model.Zombie;
import com.epf.repository.ZombieRepository;
import com.epf.service.ZombieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZombieServiceImpl implements ZombieService {

    private final ZombieRepository zombieRepository;

    public ZombieServiceImpl(ZombieRepository zombieRepository) {
        this.zombieRepository = zombieRepository;
    }

    @Override
    public List<Zombie> findAll() {
        return zombieRepository.findAll();
    }

    @Override
    public Optional<Zombie> findById(Long id) {
        return zombieRepository.findById(id);
    }

    @Override
    public Zombie save(Zombie zombie) {
        return zombieRepository.save(zombie);
    }

    @Override
    public Zombie update(Long id, Zombie zombie) {
        return zombieRepository.update(id, zombie);
    }

    @Override
    public boolean delete(Long id) {
        return zombieRepository.delete(id);
    }

    @Override
    public List<Zombie> findByMapId(Long idMap) {
        return zombieRepository.findByMapId(idMap);
    }
}
