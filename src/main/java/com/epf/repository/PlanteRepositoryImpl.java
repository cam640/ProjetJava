package com.epf.repository;

import com.epf.model.Plante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanteRepositoryImpl implements PlanteRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlanteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper pour transformer une ligne SQL en objet Plante
    private final RowMapper<Plante> rowMapper = (rs, rowNum) -> new Plante(
            rs.getLong("id_plante"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getBigDecimal("attaque_par_seconde").doubleValue(),
            rs.getInt("degat_attaque"),
            rs.getInt("cout"),
            rs.getBigDecimal("soleil_par_seconde").doubleValue(),
            rs.getString("effet"),
            rs.getString("chemin_image")
    );

    // Récupérer toutes les plantes
    @Override
    public List<Plante> findAll() {
        String sql = "SELECT * FROM plante";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Récupérer une plante par ID
    @Override
    public Optional<Plante> findById(Long id) {
        String sql = "SELECT * FROM plante WHERE id_plante = ?";
        List<Plante> plantes = jdbcTemplate.query(sql, rowMapper, id);
        return plantes.isEmpty() ? Optional.empty() : Optional.of(plantes.get(0));
    }

    // Ajouter une plante et retourner l'objet créé
    @Override
    public Plante save(Plante plante) {
        String sql = "INSERT INTO plante ( nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql,
                plante.getNom(),
                plante.getPointDeVie(),
                plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(),
                plante.getCout(),
                plante.getSoleilParSeconde(),
                plante.getEffet(),
                plante.getCheminImage());

        return result > 0 ? plante : null;
    }


    // Mettre à jour une plante et retourner l'objet mis à jour
    @Override
    public Plante update(Long id, Plante plante) {
        String sql = "UPDATE plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?";
        int result = jdbcTemplate.update(sql, plante.getNom(), plante.getPointDeVie(), plante.getAttaqueParSeconde(),
                plante.getDegatAttaque(), plante.getCout(), plante.getSoleilParSeconde(), plante.getEffet(),
                plante.getCheminImage(), id);
        return result > 0 ? plante : null;
    }





    //Supprimer une plante et retourner `true` si suppression réussie
    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM plante WHERE id_plante = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
