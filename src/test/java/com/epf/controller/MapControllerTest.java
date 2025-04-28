package com.epf.controller;

import com.epf.model.Map;
import com.epf.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MapService mapService;

    private Long mapId;

    @BeforeEach
    void setUp() {
        // Crée une nouvelle carte avec un ID fixe (par exemple 1)
        Map map = new Map(1L, 5, 9, "images/map/gazon.png");
        Map savedMap = mapService.save(map);  // Sauvegarde la carte dans la base de données
        mapId = savedMap.getIdMap();  // Récupère l'ID de la carte sauvegardée
    }

    @Test
    void getMapById_ShouldReturnMap_WhenMapExists() throws Exception {
        // Vérifie que la carte est récupérée avec l'ID correct
        mockMvc.perform(get("/maps/{id}", mapId))
                .andExpect(status().isOk())  // Vérifie que la réponse HTTP est 200 (OK)
                .andExpect(jsonPath("$.idMap").value(mapId))  // Vérifie l'ID dans la réponse JSON
                .andExpect(jsonPath("$.ligne").value(5))  // Vérifie la ligne
                .andExpect(jsonPath("$.colonne").value(9))  // Vérifie la colonne
                .andExpect(jsonPath("$.cheminImage").value("images/map/gazon.png"));  // Vérifie le chemin de l'image
    }

    @Test
    void getMapById_ShouldReturnNotFound_WhenMapDoesNotExist() throws Exception {
        // Vérifie que la réponse est "Not Found" lorsque l'ID n'existe pas
        mockMvc.perform(get("/maps/{id}", 999L))  // Utilise un ID qui n'existe pas
                .andExpect(status().isNotFound());  // Vérifie que la réponse HTTP est 404 (Not Found)
    }

    @Test
    void createMap_ShouldReturnCreatedMap() throws Exception {
        // Crée une nouvelle carte et vérifie qu'elle est bien sauvegardée
        String mapJson = "{ \"ligne\": 10, \"colonne\": 10, \"cheminImage\": \"images/map/terre.png\" }";

        mockMvc.perform(post("/maps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapJson))
                .andExpect(status().isOk())  // Vérifie que la réponse est 200 (OK)
                .andExpect(jsonPath("$.ligne").value(10))
                .andExpect(jsonPath("$.colonne").value(10))
                .andExpect(jsonPath("$.cheminImage").value("images/map/terre.png"));
    }

    @Test
    void updateMap_ShouldReturnUpdatedMap() throws Exception {
        // Définir l'ID fixe
        Long fixedId = 1L;

        // Met à jour une carte existante et vérifie les changements
        String updatedMapJson = "{ \"ligne\": 12, \"colonne\": 12, \"cheminImage\": \"images/map/rock.png\" }";

        mockMvc.perform(put("/maps/{id}", fixedId)  // Utilise l'ID fixe ici
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedMapJson))
                .andExpect(status().isOk())  // Vérifie que la réponse est 200 (OK)
                .andExpect(jsonPath("$.idMap").value(fixedId))  // Vérifie l'ID fixe
                .andExpect(jsonPath("$.ligne").value(12))  // Vérifie la ligne mise à jour
                .andExpect(jsonPath("$.colonne").value(12))  // Vérifie la colonne mise à jour
                .andExpect(jsonPath("$.cheminImage").value("images/map/rock.png"));  // Vérifie le chemin mis à jour
    }


    @Test
    void deleteMap_ShouldReturnSuccessMessage_WhenMapExists() throws Exception {
        // Vérifie que la carte est supprimée avec succès
        mockMvc.perform(delete("/maps/{id}", mapId))
                .andExpect(status().isOk())  // Vérifie que la réponse est 200 (OK)
                .andExpect(content().string("Map supprimée avec succès"));
    }

    @Test
    void deleteMap_ShouldReturnErrorMessage_WhenMapDoesNotExist() throws Exception {
        // Vérifie que l'on reçoit un message d'erreur si la carte à supprimer n'existe pas
        mockMvc.perform(delete("/maps/{id}", 999L))  // Utilise un ID qui n'existe pas
                .andExpect(status().isBadRequest())  // Vérifie que la réponse est 400 (Bad Request)
                .andExpect(content().string("Erreur lors de la suppression de la map"));
    }
}
