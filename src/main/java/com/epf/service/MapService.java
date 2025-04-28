package com.epf.service;

import com.epf.model.Map;
import java.util.List;
import java.util.Optional;

public interface MapService {
    List<Map> findAll();
    Optional<Map> findById(Long id);
    Map save(Map map);
    Map update(Long id, Map map);
    boolean delete(Long id);  //
}

