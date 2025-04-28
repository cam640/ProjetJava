package com.epf.controller;

import com.epf.model.Plante;
import com.epf.service.PlanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanteController.class)  // Cette annotation charge uniquement le contrôleur
public class PlanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // Simulation de PlanteService pour le test
    private PlanteService planteService;

    private Plante plante;

    @BeforeEach
    void setUp() {
        // Préparer une plante avant chaque test
        plante = new Plante(1L, "Rose", 100, 5.5, 30, 50, 2.0, "normal", "images/rose.png");
    }

    @Test
    void createPlante_ShouldReturnCreatedPlante() throws Exception {
        // Simulation du comportement du service
        when(planteService.save(any(Plante.class))).thenReturn(plante);

        String planteJson = "{ \"nom\": \"Rose\", \"pointDeVie\": 100, \"attaqueParSeconde\": 5.5, \"degatAttaque\": 30, \"cout\": 50, \"soleilParSeconde\": 2.0, \"effet\": \"normal\", \"cheminImage\": \"images/rose.png\" }";

        mockMvc.perform(post("/plantes")
                        .contentType("application/json")
                        .content(planteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPlante").value(plante.getIdPlante()))  // Assurez-vous que l'id de la plante est retourné
                .andExpect(jsonPath("$.nom").value(plante.getNom()))
                .andExpect(jsonPath("$.pointDeVie").value(plante.getPointDeVie()))
                .andExpect(jsonPath("$.attaqueParSeconde").value(plante.getAttaqueParSeconde()))
                .andExpect(jsonPath("$.degatAttaque").value(plante.getDegatAttaque()))
                .andExpect(jsonPath("$.cout").value(plante.getCout()))
                .andExpect(jsonPath("$.soleilParSeconde").value(plante.getSoleilParSeconde()))
                .andExpect(jsonPath("$.effet").value(plante.getEffet()))
                .andExpect(jsonPath("$.cheminImage").value(plante.getCheminImage()));
    }

    @Test
    void getPlanteById_ShouldReturnPlante_WhenPlanteExists() throws Exception {
        // Simulation du service pour trouver une plante
        when(planteService.findById(1L)).thenReturn(java.util.Optional.of(plante));

        mockMvc.perform(get("/plantes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPlante").value(plante.getIdPlante()))
                .andExpect(jsonPath("$.nom").value(plante.getNom()))
                .andExpect(jsonPath("$.pointDeVie").value(plante.getPointDeVie()))
                .andExpect(jsonPath("$.attaqueParSeconde").value(plante.getAttaqueParSeconde()))
                .andExpect(jsonPath("$.degatAttaque").value(plante.getDegatAttaque()))
                .andExpect(jsonPath("$.cout").value(plante.getCout()))
                .andExpect(jsonPath("$.soleilParSeconde").value(plante.getSoleilParSeconde()))
                .andExpect(jsonPath("$.effet").value(plante.getEffet()))
                .andExpect(jsonPath("$.cheminImage").value(plante.getCheminImage()));
    }

    @Test
    void getPlanteById_ShouldReturnNotFound_WhenPlanteDoesNotExist() throws Exception {
        // Simulation d'une plante non trouvée
        when(planteService.findById(999L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/plantes/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePlante_ShouldReturnUpdatedPlante() throws Exception {
        Plante updatedPlante = new Plante(1L, "Tulipe", 200, 6.0, 50, 70, 3.0, "slow medium", "images/tulipe.png");

        // Simulation de la mise à jour de la plante avec matchers pour les arguments
        when(planteService.update(eq(1L), any(Plante.class))).thenReturn(updatedPlante);

        String updatedPlanteJson = "{ \"nom\": \"Tulipe\", \"pointDeVie\": 200, \"attaqueParSeconde\": 6.0, \"degatAttaque\": 50, \"cout\": 70, \"soleilParSeconde\": 3.0, \"effet\": \"slow medium\", \"cheminImage\": \"images/tulipe.png\" }";

        mockMvc.perform(put("/plantes/{id}", 1L)
                        .contentType("application/json")
                        .content(updatedPlanteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPlante").value(updatedPlante.getIdPlante()))
                .andExpect(jsonPath("$.nom").value(updatedPlante.getNom()))
                .andExpect(jsonPath("$.pointDeVie").value(updatedPlante.getPointDeVie()))
                .andExpect(jsonPath("$.attaqueParSeconde").value(updatedPlante.getAttaqueParSeconde()))
                .andExpect(jsonPath("$.degatAttaque").value(updatedPlante.getDegatAttaque()))
                .andExpect(jsonPath("$.cout").value(updatedPlante.getCout()))
                .andExpect(jsonPath("$.soleilParSeconde").value(updatedPlante.getSoleilParSeconde()))
                .andExpect(jsonPath("$.effet").value(updatedPlante.getEffet()))
                .andExpect(jsonPath("$.cheminImage").value(updatedPlante.getCheminImage()));
    }


    @Test
    void deletePlante_ShouldReturnNoContent_WhenPlanteExists() throws Exception {
        // Simulation de la suppression de la plante
        when(planteService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/plantes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePlante_ShouldReturnNotFound_WhenPlanteDoesNotExist() throws Exception {
        // Simulation de l'absence de la plante à supprimer
        when(planteService.delete(999L)).thenReturn(false);

        mockMvc.perform(delete("/plantes/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
