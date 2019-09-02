package com.source.project.domain;

import com.source.project.*;
import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Value {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private Objects objects;

    @ManyToOne
    @JoinColumn(name = "atr_id")
    private Attribute attributes;

    private String value;


    public Value() {}

    public Value(Objects objects, Attribute attributes, String value) {
        this.objects = objects;
        this.attributes = attributes;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Objects getObjects() {
        return objects;
    }

    public void setObjects(Objects objects) {
        this.objects = objects;
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAtributes(Attribute atributes) {
        this.attributes = atributes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

