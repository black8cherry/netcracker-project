package com.source.project.service;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.User;

public interface RatingService {
    void save(String userId, Integer parentId, Float value);
    void rerate(String userId, Integer parentId, Float value);
    boolean findByObjectsAndUser(Integer objId, String userId);
    String getRate(Integer id);
    //void init();
}
