package com.parcinfo.model;

public enum RoleType {
    USER,
    ADMIN;

    public String getValue() {
        return this.name();
    }
} 