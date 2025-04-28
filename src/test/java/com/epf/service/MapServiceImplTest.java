package com.epf.service;

import com.epf.model.Map;
import com.epf.repository.MapRepository;
import com.epf.impl.MapServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional  // Annule les modifications dans la base après chaque test
class MapServiceImplTest {

    @Autowired
    private MapServiceImpl mapService;

    @Autowired
    private MapRepository mapRepository;

    private Map map;

    private Long mapId;

    @BeforeEach
    void setUp() {
        // Préparer les données pour chaque test
        map = new Map(null, 15, 15, "map1.png");
        mapRepository.save(map);  // Sauvegarder la map dans la base de données
        mapId = map.getIdMap();  // Récupérer l'ID généré
    }

    // Test de la méthode findAll()
    @Test
    void findAll_ShouldReturnListOfMaps() {
        List<Map> maps = mapService.findAll();  // Appel à la méthode du service
        assertNotNull(maps);  // Vérifie que la liste des maps n'est pas vide
        assertFalse(maps.isEmpty());  // Vérifie que la liste contient des éléments
    }

    // Test de la méthode findById()
    @Test
    void findById_ShouldReturnMapWhenFound() {
        Long id = 1L;  // Par exemple, l'ID 1
        Optional<Map> result = mapService.findById(id);
        assertTrue(result.isPresent(), "La carte avec l'ID 1 devrait être trouvée.");
        assertEquals(id, result.get().getIdMap(), "L'ID de la carte doit correspondre.");
        assertEquals(5, result.get().getLigne(), "La ligne de la carte doit être 5.");
        assertEquals(9, result.get().getColonne(), "La colonne de la carte doit être 9.");
        assertEquals("images/map/gazon.png", result.get().getCheminImage(), "Le chemin de l'image doit être 'images/map/gazon.png'.");
    }




    @Test
    void findById_ShouldReturnEmptyWhenNotFound() {
        Optional<Map> result = mapService.findById(999L);  // Chercher une map avec un ID inexistant
        assertFalse(result.isPresent());  // Vérifie qu'aucune map n'est trouvée
    }

    // Test de la méthode save()
    @Test
    void save_ShouldReturnSavedMap() {
        Map newMap = new Map(100L, 10, 10, "map2.png");  // ID fixé à 100
        Map savedMap = mapService.save(newMap);
        assertNotNull(savedMap, "La map sauvegardée ne devrait pas être null");
        assertEquals(100L, savedMap.getIdMap(), "L'ID de la map doit être 100");
        assertEquals(10, savedMap.getLigne(), "Le nombre de lignes doit être 10");
        assertEquals(10, savedMap.getColonne(), "Le nombre de colonnes doit être 10");
        assertEquals("map2.png", savedMap.getCheminImage(), "Le chemin de l'image doit être 'map2.png'");
    }


    // Test de la méthode update()
    @Test
    void update_ShouldReturnUpdatedMap() {
        Map existingMap = new Map(1L, 5, 9, "images/map/gazon.png");
        Map updatedMap = new Map(1L, 20, 20, "images/map/gazon_updated.png");
        Map result = mapService.update(1L, updatedMap);
        assertNotNull(result, "La map mise à jour ne devrait pas être null");
        assertEquals(1L, result.getIdMap(), "L'ID de la map doit rester le même");
        assertEquals(20, result.getLigne(), "Le nombre de lignes doit être 20");
        assertEquals(20, result.getColonne(), "Le nombre de colonnes doit être 20");
        assertEquals("images/map/gazon_updated.png", result.getCheminImage(), "Le chemin de l'image doit être mis à jour");
    }



    @Test
    void update_ShouldReturnNullWhenMapNotFound() {
        Map updatedMap = new Map(999L, 20, 20, "non_existent_map.png");  // Essayer de mettre à jour une map inexistante
        Map result = mapService.update(999L, updatedMap);
        assertNull(result);  // Vérifie que la mise à jour échoue
    }

    // Test de la méthode delete()
    @Test
    void delete_ShouldReturnTrueWhenDeleted() {
        Map newMap = new Map(1L, 10, 10, "map_to_delete.png");
        mapService.save(newMap);
        boolean result = mapService.delete(1L);
        assertTrue(result, "La suppression de la map doit retourner true");
        Optional<Map> deletedMap = mapService.findById(1L);
        assertFalse(deletedMap.isPresent(), "La map devrait être supprimée et ne pas être trouvée dans la base");
    }



    @Test
    void delete_ShouldReturnFalseWhenNotDeleted() {
        boolean result = mapService.delete(999L);  // Essayer de supprimer une map inexistante
        assertFalse(result);  // Vérifie que la suppression échoue
    }
}
