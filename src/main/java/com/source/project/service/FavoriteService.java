package com.source.project.service;

import com.source.project.domain.ObjEntity;

import java.util.List;

public interface FavoriteService {
    //void init();
    void save(String userId, String movieId);
    void delete(String userId, String movieId);
    boolean check(String userId, String movieId);
    void create(String userId);
    List<ObjEntity> get(String userId);
}
