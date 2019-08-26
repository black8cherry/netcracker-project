package com.source.project.domain;

import com.source.project.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Atribute {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String label;

    @OneToMany(mappedBy = "atributes")
    private Set<Value> values = new HashSet<Value>();

    public Atribute() {}

    public Atribute(String label, Set<Value> values) {
        this.label = label;
        this.values = values;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }
}
