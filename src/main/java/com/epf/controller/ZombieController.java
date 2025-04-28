package com.epf.controller;

import com.epf.model.Zombie;
import com.epf.service.ZombieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/zombies")
public class ZombieController {

    private final ZombieService zombieService;

    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    // Récupérer tous les zombies
    @GetMapping
    public ResponseEntity<List<Zombie>> getAllZombies() {
        return ResponseEntity.ok(zombieService.findAll());
    }

    // Récupérer un zombie par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Zombie> getZombieById(@PathVariable Long id) {
        Optional<Zombie> zombie = zombieService.findById(id);
        return zombie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajouter un nouveau zombie
    @PostMapping
    public ResponseEntity<String> createZombie(@RequestBody Zombie zombie) {
        Zombie createdZombie = zombieService.save(zombie);
        return (createdZombie != null)
                ? ResponseEntity.ok("Zombie créé avec succès !")
                : ResponseEntity.badRequest().body("Erreur lors de la création !");
    }

    // Mettre à jour un zombie
    @PutMapping("/{id}")
    public ResponseEntity<String> updateZombie(@PathVariable Long id, @RequestBody Zombie zombie) {
        zombie.setIdZombie(id); // Assurer que l'ID correspond à l'URL
        Zombie updatedZombie = zombieService.update(id, zombie);
        return (updatedZombie != null)
                ? ResponseEntity.ok("Zombie mis à jour avec succès !")
                : ResponseEntity.badRequest().body("Erreur lors de la mise à jour !");
    }

    // Supprimer un zombie
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteZombie(@PathVariable Long id) {
        boolean isDeleted = zombieService.delete(id);
        return isDeleted
                ? ResponseEntity.ok("Zombie supprimé avec succès !")
                : ResponseEntity.badRequest().body("Erreur lors de la suppression !");
    }

    // Récupérer tous les zombies d'une carte spécifique
    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<Zombie>> getZombiesByMap(@PathVariable Long mapId) {
        return ResponseEntity.ok(zombieService.findByMapId(mapId));
    }
}


