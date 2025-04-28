package com.epf.impl;

import com.epf.model.Map;
import com.epf.repository.MapRepository;
import com.epf.service.MapService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapServiceImpl implements MapService {

    private final MapRepository mapRepository;

    public MapServiceImpl(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public List<Map> findAll() {
        return mapRepository.findAll();
    }

    @Override
    public Optional<Map> findById(Long id) {
        return mapRepository.findById(id);
    }

    @Override
    public Map save(Map map) {
        return mapRepository.save(map);
    }

    @Override
    public Map update(Long id, Map map) {
        return mapRepository.update(id, map);
    }

    @Override
    public boolean delete(Long id) {
        return mapRepository.delete(id);
    }
}
