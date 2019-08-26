package com.source.project.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String type;

    /*@OneToMany(mappedBy = "type")
    private Set<Objects> objects = new HashSet<Objects>();*/

    public Type() {}

    public Type(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
