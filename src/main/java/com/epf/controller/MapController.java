package com.epf.controller;

import com.epf.model.Map;
import com.epf.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public List<Map> getAllMaps() {
        return mapService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getMapById(@PathVariable Long id) {
        Optional<Map> map = mapService.findById(id);
        return map.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map> createMap(@RequestBody Map map) {
        Map newMap = mapService.save(map);
        return ResponseEntity.ok(newMap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map> updateMap(@PathVariable Long id, @RequestBody Map map) {
        Map updatedMap = mapService.update(id, map);
        return ResponseEntity.ok(updatedMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMap(@PathVariable Long id) {
        boolean deleted = mapService.delete(id);
        return deleted ? ResponseEntity.ok("Map supprimée avec succès") : ResponseEntity.badRequest().body("Erreur lors de la suppression de la map");
    }
}
