package com.source.project.domain;

import javax.persistence.*;

@Entity
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "value_gen")
    @SequenceGenerator(name = "value_gen", sequenceName = "value_gen", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private ObjEntity objEntity;

    @ManyToOne
    @JoinColumn(name = "atr_id")
    private Attribute attributes;

    private String value;

    public Value() {}

    public Value(ObjEntity objEntity, Attribute attributes, String value) {
        this.objEntity = objEntity;
        this.attributes = attributes;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ObjEntity getObjEntity() {
        return objEntity;
    }

    public void setObjEntity(ObjEntity objEntity) {
        this.objEntity = objEntity;
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

