package com.source.project.service;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Map<String, String>> getListMessages(Integer parentId);
    void create(Integer parentId, List<String> label, List<String> value);
    void delete(Integer objId);
}
