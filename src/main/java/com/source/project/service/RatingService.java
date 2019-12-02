package com.source.project.service;

public interface RatingService {
    void rate(String userId, Integer parentId, Float value);
    boolean check(Integer objId, String userId);
    String getRate(Integer id);
}
