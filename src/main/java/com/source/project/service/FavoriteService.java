package com.source.project.service;

public interface FavoriteService {
    //void init();
    void save(String userId, String movieId);
    void delete(String userId, String movieId);
    boolean check(String userId, String movieId);
    void create(String userId);
}
