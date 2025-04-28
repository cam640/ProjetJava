package com.epf.service;

import com.epf.model.Zombie;
import com.epf.repository.ZombieRepository;
import com.epf.impl.ZombieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)  // Permet d'utiliser Spring avec JUnit 5
@Transactional  // Les modifications sont annulées à la fin de chaque test
class ZombieServiceImplTest {

    @Autowired
    private ZombieRepository zombieRepository;

    @Autowired
    private ZombieServiceImpl zombieService;

    private Zombie zombie;

    @BeforeEach
    void setUp() {
        // Préparer les données avant chaque test (évite de spécifier des IDs manuellement si auto-générés)
        zombie = new Zombie(null, "Zombie1", 100, 1.5, 20, 3, "zombie.png", 2L);
        zombieRepository.save(zombie);  // Ajouter le zombie dans la base de données
    }


    @Test
    void findAll_ShouldReturnZombieList() {
        List<Zombie> zombies = zombieService.findAll();
        assertNotNull(zombies);
        assertFalse(zombies.isEmpty());
    }

    @Test
    void findById_ShouldReturnZombie() {
        Zombie foundZombie = zombieService.findById(1L).orElse(null);
        assertNotNull(foundZombie);
        assertEquals("Zombie de base", foundZombie.getNom()); // Et PAS "Zombie1" !
    }

    @Test
    void save_ShouldReturnSavedZombie() {
        Zombie newZombie = new Zombie(2L, "Zombie2", 200, 1.8, 25, 4, "zombie2.png", 3L);
        Zombie savedZombie = zombieService.save(newZombie);
        assertNotNull(savedZombie);
        assertEquals("Zombie2", savedZombie.getNom());
    }

    @Test
    void update_ShouldReturnUpdatedZombie() {
        zombie.setNom("ZombieUpdated");
        Zombie updatedZombie = zombieService.update(1L, zombie);
        assertNotNull(updatedZombie);
        assertEquals("ZombieUpdated", updatedZombie.getNom());
    }

    @Test
    void delete_ShouldReturnTrue() {
        boolean isDeleted = zombieService.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void findByMapId_ShouldReturnZombiesForMap() {
        List<Zombie> zombies = zombieService.findByMapId(2L);

        assertFalse(zombies.isEmpty());
        assertEquals(2, zombies.size());  // ici attendre 2 et plus 1
    }
}
