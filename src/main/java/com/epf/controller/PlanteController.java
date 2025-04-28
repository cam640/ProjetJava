package com.epf.controller;

import com.epf.model.Plante;
import com.epf.service.PlanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plantes")
public class PlanteController {

    private final PlanteService planteService;

    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    //GET - Récupérer toutes les plantes
    @GetMapping
    public ResponseEntity<List<Plante>> getAllPlantes() {
        List<Plante> plantes = planteService.findAll();
        return ResponseEntity.ok(plantes);
    }

    //GET - Récupérer une plante par ID
    @GetMapping("/{id}")
    public ResponseEntity<Plante> getPlanteById(@PathVariable Long id) {
        Optional<Plante> plante = planteService.findById(id);
        return plante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //POST - Ajouter une nouvelle plante
    @PostMapping
    public ResponseEntity<Plante> createPlante(@RequestBody Plante plante) {
        Plante savedPlante = planteService.save(plante);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlante);
    }

    // PUT - Mettre à jour une plante existante
    @PutMapping("/{id}")
    public ResponseEntity<Plante> updatePlante(@PathVariable Long id, @RequestBody Plante plante) {
        Plante updatedPlante = planteService.update(id, plante);
        return ResponseEntity.ok(updatedPlante);
    }

    // DELETE - Supprimer une plante par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable Long id) {
        boolean deleted = planteService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

