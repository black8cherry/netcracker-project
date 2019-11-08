package com.source.project.service;

import com.source.project.domain.resources.MessageConnector;

import java.util.List;

public interface MessageService {
    List<MessageConnector> getListMessages(Integer parentId);
    void create(Integer parentId, List<String> label, List<String> value);
    void delete(Integer objId);
}
