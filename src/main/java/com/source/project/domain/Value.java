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
    private Atribute atributes;


    private String value;




    public Value() {}

    public Value(Objects objects, Atribute atributes, String value) {
        this.objects = objects;
        this.atributes = atributes;
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

    public Atribute getAtributes() {
        return atributes;
    }

    public void setAtributes(Atribute atributes) {
        this.atributes = atributes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
