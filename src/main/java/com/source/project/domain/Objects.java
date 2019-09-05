package com.source.project.domain;

import com.source.project.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Objects {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

    private String filename;

    /*@OneToMany(mappedBy = "objects")
    private Set<Value> values = new HashSet<Value>();*/

    public Objects(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Objects() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
