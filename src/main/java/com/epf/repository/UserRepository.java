package com.epf.repository;

import com.epf.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    int save(User user);
    int update(User user);
    int delete(Long id);
}

