package com.source.project.domain;


import javax.persistence.*;

@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "obj_id")
    private Objects object;

    Favorites() {}

    public Favorites(User user, Objects object) {
        this.user = user;
        this.object = object;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Objects getObject() {
        return object;
    }

    public void setObject(Objects object) {
        this.object = object;
    }
}
