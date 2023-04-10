package com.github.permissiondog.webhomework3;

import java.time.LocalDateTime;
import java.util.UUID;

public record Course(UUID id, String name, CourseStatus status, String comment, LocalDateTime creationTime) {
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"status\":\"" + status + "\",\"comment\":\"" + comment + "\"}";
    }
}