package com.source.project.domain;

import javax.persistence.*;

@Entity
public class ObjEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "obj_entity_gen")
    @SequenceGenerator(name = "obj_entity_gen", sequenceName = "obj_entity_gen", allocationSize = 1)
    private Integer id;

    private String name;

    private Integer parentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

    public ObjEntity() {

    }

    public ObjEntity(Type type) {
        this.type = type;
    }

    public ObjEntity(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public ObjEntity(Integer parentId, Type type) {
        this.parentId = parentId;
        this.type = type;
    }

    public ObjEntity(String name, Integer parentId, Type type) {
        this.name = name;
        this.parentId = parentId;
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
