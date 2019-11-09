package com.source.project.service;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.resources.FilmListConnector;
import com.source.project.domain.resources.MessageConnector;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ObjEntityService {
    ObjEntity findById(Integer id);
    List<ObjEntity> getObjEntitiesByTypeInOrderByName(Collection<Type> type);
    List<ObjEntity> findByNameIsContaining(String filter);
    void save(String name, Integer typeId, MultipartFile file, String uploadPath) throws IOException;
    void removeById(Integer id);
    void edit(String objectName, List<String> label, List<String> value, Integer id);
    MessageConnector showAttributes(ObjEntity objEntity);

}
