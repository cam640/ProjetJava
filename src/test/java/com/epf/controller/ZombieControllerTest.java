package com.epf.controller;

import com.epf.model.Zombie;
import com.epf.repository.ZombieRepository;
import com.epf.service.ZombieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ZombieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ZombieRepository zombieRepository;

    private Zombie zombie;

    @Autowired
    private ZombieService zombieService;


    @BeforeEach
    void setUp() {

        // Ajouter un zombie pour le test
        zombie = new Zombie(1L, "Zombie Test", 150, 1.0, 15, 1, "images/zombie/test.png", 1L);


        zombieRepository.save(zombie);
    }

    @Test
    void testCreateZombie() throws Exception {
        String zombieJson = "{ \"nom\": \"Zombie Nouveau\", \"point_de_vie\": 120, \"attaque_par_seconde\": 0.9, \"degat_attaque\": 10, \"vitesse_de_deplacement\": 0.5, \"chemin_image\": \"images/zombie/nouveau.png\", \"id_map\": 1 }";

        mockMvc.perform(post("/zombies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(zombieJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Zombie créé avec succès !"));

    }

    @Test
    void testGetAllZombies() throws Exception {
        int nombreZombiesEnBase = zombieService.findAll().size(); // on compte les zombies existants

        mockMvc.perform(get("/zombies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(nombreZombiesEnBase));
    }


    @Test
    void testGetZombieById() throws Exception {
        // 1. Préparer un vrai zombie existant dans la base
        List<Zombie> zombies = zombieService.findAll();
        Zombie zombieExist = zombies.get(0);
        mockMvc.perform(get("/zombies/{id}", zombieExist.getIdZombie()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value(zombieExist.getNom()))
                .andExpect(jsonPath("$.pointDeVie").value(zombieExist.getPointDeVie()));
    }


    @Test
    void testUpdateZombie() throws Exception {
        String updatedZombieJson = "{ \"nom\": \"Zombie Mis à Jour\", \"point_de_vie\": 180, \"attaque_par_seconde\": 1.2, \"degat_attaque\": 15, \"vitesse_de_deplacement\": 0.7, \"chemin_image\": \"images/zombie/updated.png\", \"id_map\": 1 }";

        mockMvc.perform(put("/zombies/{id}", zombie.getIdZombie())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedZombieJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Zombie mis à jour avec succès !"));

        // Vérifier que le zombie a bien été mis à jour dans la base
        Zombie updatedZombie = zombieRepository.findById(zombie.getIdZombie()).orElseThrow();
        assert updatedZombie.getNom().equals("Zombie Mis à Jour");
        assert updatedZombie.getPointDeVie() == 180;
    }

    @Test
    void testDeleteZombie() throws Exception {
        Long zombieId = zombie.getIdZombie();  // Récupère l'ID du zombie créé dans setUp()

        // Vérifie que le zombie existe avant de tenter la suppression
        assertTrue(zombieRepository.findById(zombieId).isPresent(), "Le zombie devrait exister avant la suppression");

        // Effectuer la requête de suppression
        mockMvc.perform(delete("/zombies/{id}", zombieId))
                .andExpect(status().isOk())  // Vérifie que le statut est OK (200)
                .andExpect(content().string("Zombie supprimé avec succès !"));  // Vérifie le message de succès

        // Vérifie que le zombie a bien été supprimé de la base de données
        assertTrue(zombieRepository.findById(zombieId).isEmpty(), "Le zombie devrait être supprimé après la requête");

        // Essayer de supprimer à nouveau (devrait échouer, retourner un 400)
        mockMvc.perform(delete("/zombies/{id}", zombieId))
                .andExpect(status().isBadRequest())  // Vérifie que ça retourne un BadRequest (400)
                .andExpect(content().string("Erreur lors de la suppression !"));  // Vérifie le message d'erreur
    }




}
