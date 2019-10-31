package com.source.project.service;

import com.source.project.domain.User;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findById(Integer id);
    void save(User user);
    User getUser();
    boolean registration(User user);
}
