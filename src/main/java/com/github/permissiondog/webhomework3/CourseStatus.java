package com.github.permissiondog.webhomework3;

public enum CourseStatus {
    DEFAULT("待审批"),
    APPROVED("已通过"),
    REJECTED("已拒绝");
    private final String displayName;
    CourseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
