package com.source.project.domain;

import javax.persistence.*;

@Entity
public class TypeAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_gen")
    @SequenceGenerator(name = "primary_gen", sequenceName = "primary_gen", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "att_id")
    private Attribute attribute;

    TypeAttribute() {}

    public TypeAttribute(Type type, Attribute attribute) {
        this.type = type;
        this.attribute = attribute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
