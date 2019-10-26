package com.source.project.service;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.resources.FilmList;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ObjEntityService {
    ObjEntity findById(Integer id);
    List<ObjEntity> getObjEntitiesByTypeInOrderByName(Collection<Type> type);
    List<ObjEntity> findAll();
    List<ObjEntity> findByNameOrderByName(String name);
    List<ObjEntity> findAll(Sort sort);
    List<ObjEntity> findAllByFilenameNotNull();
    List<ObjEntity> findAllByFilenameNotNull(Sort sort);
    List<ObjEntity> findAllByNameLike(String filter);
    List<ObjEntity> getAllByNameIsLike(String filter);
    void save(String name, String type, MultipartFile file, String uploadPath) throws IOException;
    void removeById(Integer id);
    void edit(String objectName, List<String> label, List<String> value, Integer id);
    List<FilmList> showAttributes(ObjEntity objEntity);

}
