package com.tui.proof.pilotes.dto;

import org.springframework.data.annotation.Id;

public class Role {
    @Id
    private String id;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}