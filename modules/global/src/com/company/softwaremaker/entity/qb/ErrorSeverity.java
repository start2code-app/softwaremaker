package com.company.softwaremaker.entity.qb;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ErrorSeverity implements EnumClass<String> {


    FATAL("FATAL"),
    WARNING("WARNING");

    private String id;

    ErrorSeverity(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ErrorSeverity fromId(String id) {
        for (ErrorSeverity at : ErrorSeverity.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}