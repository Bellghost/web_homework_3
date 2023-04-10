package com.github.permissiondog.webhomework3;

import java.util.UUID;

public record Course(UUID id, String name, String status, String comment) {
    public static final String DEFAULT = "default";
    public static final String APPROVED = "approved";
    public static final String REJECTED = "rejected";

    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"status\":\"" + status + "\",\"comment\":\"" + comment + "\"}";
    }
}