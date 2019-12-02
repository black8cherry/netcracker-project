package com.source.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Map<String, String>> getListMessages(Integer parentId);
    void create(Integer parentId, List<String> label, List<String> value, List<MultipartFile> file, String uploadPath);
    void delete(Integer messageId);
    void edit(Integer messageId, List<String> label, List<String> value, List<MultipartFile> file, String uploadPath);
}
