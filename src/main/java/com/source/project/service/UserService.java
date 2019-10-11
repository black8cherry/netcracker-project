package com.source.project.service;

import com.source.project.domain.User;

public interface UserService {
    User findByUsername(String username);
    User findById(Integer id);
    void save(User user);
}
