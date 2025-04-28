package com.epf.repository;

import com.epf.model.Zombie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ZombieRepositoryImpl implements ZombieRepository {

    private final JdbcTemplate jdbcTemplate;

    public ZombieRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper pour convertir les résultats SQL en objets Java
    private final RowMapper<Zombie> rowMapper = (rs, rowNum) -> new Zombie(
            rs.getLong("id_zombie"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getBigDecimal("attaque_par_seconde").doubleValue(),
            rs.getInt("degat_attaque"),
            rs.getBigDecimal("vitesse_de_deplacement").intValue(),
            rs.getString("chemin_image"),
            rs.getLong("id_map")
    );

    @Override
    public List<Zombie> findAll() {
        String sql = "SELECT * FROM zombie";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Zombie> findById(Long id) {
        String sql = "SELECT * FROM zombie WHERE id_zombie = ?";
        List<Zombie> zombies = jdbcTemplate.query(sql, rowMapper, id);
        return zombies.stream().findFirst();
    }

    @Override
    public Zombie save(Zombie zombie) {
        String sql = "INSERT INTO zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, zombie.getNom(), zombie.getPointDeVie(), zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(), zombie.getVitesseDeDeplacement(), zombie.getCheminImage(), zombie.getIdMap());
        return result > 0 ? zombie : null;
    }

    @Override
    public Zombie update(Long id, Zombie zombie) {
        String sql = "UPDATE zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?";
        int result = jdbcTemplate.update(sql, zombie.getNom(), zombie.getPointDeVie(), zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(), zombie.getVitesseDeDeplacement(), zombie.getCheminImage(), zombie.getIdMap(), id);
        return result > 0 ? zombie : null;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM zombie WHERE id_zombie = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public List<Zombie> findByMapId(Long idMap) {
        String sql = "SELECT * FROM zombie WHERE id_map = ?";
        return jdbcTemplate.query(sql, rowMapper, idMap);
    }
}
