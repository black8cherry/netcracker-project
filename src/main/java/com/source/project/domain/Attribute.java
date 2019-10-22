package com.source.project.domain;

import com.source.project.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_gen")
    @SequenceGenerator(name = "attribute_gen", sequenceName = "attribute_gen", allocationSize = 1)
    private Integer id;
    private String label;

    public Attribute() {}

    public Attribute(String label) {
        this.label = label;
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

}
