package com.epf.repository;

import com.epf.model.Plante;
import com.epf.repository.PlanteRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
class PlanteRepositoryImplTest {

    private PlanteRepositoryImpl planteRepository;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate();
        planteRepository = new PlanteRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testSave() {
        Plante plante = new Plante(
                6L, // L'ID sera généré automatiquement
                "Plante Test",
                100,
                1.5,
                10,
                50,
                0.2,
                "normal",
                "path/to/image.png"
        );

        Plante savedPlante = planteRepository.save(plante);

        assertNotNull(savedPlante, "La plante devrait être sauvegardée");
        assertEquals("Plante Test", savedPlante.getNom(), "Le nom de la plante devrait être 'Plante Test'");
    }
}


