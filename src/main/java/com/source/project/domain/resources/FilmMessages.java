package com.source.project.domain.resources;

public class FilmMessages {
    private Integer ido;
    private String username;
    private String message;

    public FilmMessages(Integer ido, String username, String message) {
        this.ido = ido;
        this.username = username;
        this.message = message;
    }

    public Integer getIdo() {
        return ido;
    }

    public void setIdo(Integer ido) {
        this.ido = ido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
