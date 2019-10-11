package com.source.project.service.implementations;

import com.source.project.domain.Objects;
import com.source.project.repos.ObjectsRep;
import com.source.project.service.ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectsServiceImpl implements ObjectsService {

    @Autowired
    private ObjectsRep objectsRep;

    @Override
    public List<Objects> findByNameOrderByName(String name) {
        return objectsRep.findByNameOrderByName(name);
    }

    @Override
    public Objects findById(Integer id) {
        return objectsRep.findById(id);
    }

    @Override
    public void removeById(Integer id) {
        objectsRep.removeById(id);
    }

    @Override
    public List<Objects> findAll() {
        return objectsRep.findAll();
    }

    @Override
    public void save(Objects objects) {
        objectsRep.save(objects);
    }

    @Override
    public List<Objects> findAll(Sort sort) {
        return objectsRep.findAll(sort);
    }
}
