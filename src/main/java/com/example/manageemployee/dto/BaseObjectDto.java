package com.example.manageemployee.dto;

import java.util.UUID;

public class BaseObjectDto {

    private UUID id;

    public BaseObjectDto() {
        this.id = UUID.randomUUID();
    }

    public BaseObjectDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
