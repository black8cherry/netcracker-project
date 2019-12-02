package com.source.project.service;

import com.source.project.domain.User;

public interface UserService {
    User findById(Integer id);
    void save(User user);
    User getUser();
    boolean registration(User user);
}
