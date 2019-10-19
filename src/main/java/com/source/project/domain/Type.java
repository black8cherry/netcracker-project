package com.source.project.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_gen")
    @SequenceGenerator(name = "primary_gen", sequenceName = "primary_gen", allocationSize = 1)
    private Integer id;

    private String typename;

    public Type() {}

    public Type(String typename) {
        this.typename = typename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return typename;
    }

    public void setType(String typename) {
        this.typename = typename;
    }
}
