package com.epf.service;

import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;
import com.epf.impl.PlanteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanteServiceImplTest {

    @Autowired
    private PlanteServiceImpl planteService;

    @Autowired
    private PlanteRepository planteRepository;

    private Plante plante;

    @BeforeEach
    void setUp() {
        // Créer une plante initiale à tester
        plante = new Plante(null, "Tournesol", 100, 1.5, 20, 10, 1.5, "normal", "image.png");

        // Sauvegarder la plante dans la base de données réelle
        planteRepository.save(plante);
    }


    @Test
    void findAll_ShouldReturnPlantes() {
        List<Plante> plantes = planteService.findAll();

        assertNotNull(plantes);
        assertFalse(plantes.isEmpty());
    }

    @Test
    void findById_ShouldReturnPlanteWhenFound() {
        Optional<Plante> result = planteService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdPlante());
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotFound() {
        Optional<Plante> result = planteService.findById(9999L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldReturnSavedPlante() {
        Plante newPlante = new Plante(null, "Tournesol", 100, 0.0, 0, 50, 25.0, "normal", "tournesol.png");
        Plante savedPlante = planteService.save(newPlante);

        assertNotNull(savedPlante);
        assertEquals("Tournesol", savedPlante.getNom());
    }

    @Test
    void update_ShouldReturnUpdatedPlante() {
        // Trouver une plante existante dans la base de données
        Plante planteExistante = planteRepository.findById(1L).orElseThrow(() -> new RuntimeException("Plante non trouvée"));

        //  Modifier les propriétés de la plante existante
        planteExistante.setNom("Tournesol Modifié");
        planteExistante.setPointDeVie(120);
        planteExistante.setAttaqueParSeconde(1.0);
        planteExistante.setDegatAttaque(10);
        planteExistante.setCout(60);
        planteExistante.setSoleilParSeconde(30.0);
        planteExistante.setEffet("slow medium");
        planteExistante.setCheminImage("images/plante/tournesol_modifie.png");

        // Appeler la méthode `update` pour mettre à jour la plante dans la base de données
        Plante planteMiseAJour = planteService.update(planteExistante.getIdPlante(), planteExistante);

        // Vérifier que la plante a bien été mise à jour
        assertNotNull(planteMiseAJour);
        assertEquals("Tournesol Modifié", planteMiseAJour.getNom());
        assertEquals(120, planteMiseAJour.getPointDeVie());
        assertEquals(1.0, planteMiseAJour.getAttaqueParSeconde());
        assertEquals(10, planteMiseAJour.getDegatAttaque());
        assertEquals(60, planteMiseAJour.getCout());
        assertEquals(30.0, planteMiseAJour.getSoleilParSeconde());
        assertEquals("slow medium", planteMiseAJour.getEffet());
        assertEquals("images/plante/tournesol_modifie.png", planteMiseAJour.getCheminImage());
    }

    @Test
    void delete_ShouldReturnTrueWhenDeleted() {
        Plante planteToDelete = new Plante(1L, "Plante à supprimer", 100, 1.0, 5, 25, 1.5, "normal", "delete.png");
        Plante savedPlante = planteService.save(planteToDelete);
        boolean deleted = planteService.delete(savedPlante.getIdPlante());
        assertTrue(deleted);
        assertFalse(planteService.findById(savedPlante.getIdPlante()).isPresent());
    }






    @Test
    void delete_ShouldReturnFalseWhenNotDeleted() {
        boolean deleted = planteService.delete(9999L);

        assertFalse(deleted);
    }
}
