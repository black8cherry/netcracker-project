package com.source.project.service;

import com.source.project.domain.Message;
import com.source.project.domain.Objects;

import java.util.List;

public interface MessageService {
    List<Message> findByObjects(Objects objects);
    void removeById(Integer id);
    void save(Message message);
}
