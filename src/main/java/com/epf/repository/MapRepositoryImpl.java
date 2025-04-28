package com.epf.repository;

import com.epf.model.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MapRepositoryImpl implements MapRepository {

    private final JdbcTemplate jdbcTemplate;

    public MapRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Map> rowMapper = (rs, rowNum) -> new Map(
            rs.getLong("id_map"),
            rs.getInt("ligne"),
            rs.getInt("colonne"),
            rs.getString("chemin_image")
    );

    @Override
    public List<Map> findAll() {
        String sql = "SELECT * FROM map";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Map> findById(Long id) {
        String sql = "SELECT * FROM map WHERE id_map = ?";
        List<Map> maps = jdbcTemplate.query(sql, rowMapper, id);
        return maps.stream().findFirst();
    }

    @Override
    public Map save(Map map) {
        String sql = "INSERT INTO map (ligne, colonne, chemin_image) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, map.getLigne(), map.getColonne(), map.getCheminImage());
        return result > 0 ? map : null;
    }

    @Override
    public Map update(Long id, Map map) {
        String sql = "UPDATE map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?";
        int result = jdbcTemplate.update(sql, map.getLigne(), map.getColonne(), map.getCheminImage(), id);
        return result > 0 ? map : null;
    }

    @Override
    public boolean delete(Long id) {
        String deleteZombiesSql = "DELETE FROM zombie WHERE id_map = ?";
        jdbcTemplate.update(deleteZombiesSql, id);
        String deleteMapSql = "DELETE FROM map WHERE id_map = ?";
        return jdbcTemplate.update(deleteMapSql, id) > 0;
    }

}
