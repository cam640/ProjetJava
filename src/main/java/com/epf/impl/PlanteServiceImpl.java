package com.epf.impl;

import com.epf.model.Plante;
import com.epf.repository.PlanteRepository;
import com.epf.service.PlanteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanteServiceImpl implements PlanteService {

    private final PlanteRepository planteRepository;

    public PlanteServiceImpl(PlanteRepository planteRepository) {
        this.planteRepository = planteRepository;
    }

    @Override
    public List<Plante> findAll() {
        return planteRepository.findAll();
    }

    @Override
    public Optional<Plante> findById(Long id) {
        return planteRepository.findById(id);  // Plus de double Optional<Optional<Plante>>
    }

    @Override
    public Plante save(Plante plante) {
        return planteRepository.save(plante);  // Retourne directement l'objet Plante
    }

    @Override
    public Plante update(Long id, Plante plante) {  // Ajout du paramètre `id`
        return planteRepository.update(id, plante);
    }

    @Override
    public boolean delete(Long id) {
        return planteRepository.delete(id);  // `delete` retourne déjà un boolean
    }
}
