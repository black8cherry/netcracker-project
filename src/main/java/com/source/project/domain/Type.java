package com.source.project.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_gen")
    @SequenceGenerator(name = "type_gen", sequenceName = "type_gen", allocationSize = 1)
    private Integer id;
    private Integer parentId;
    private String typename;

    public Type() {}

    public Type(Integer parentId, String typename) {
        this.parentId = parentId;
        this.typename = typename;
    }

    public Type(String typename) {
        this.typename = typename;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
