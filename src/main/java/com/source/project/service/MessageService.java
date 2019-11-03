package com.source.project.service;

import com.source.project.domain.resources.FilmMessages;

import java.util.List;

public interface MessageService {
    List<FilmMessages> getListMessages(Integer parentId);
    void save(String userId, Integer parentId, String message);
    void delete(Integer objId);
}
