package com.github.permissiondog.webhomework3;

public enum CourseName {
    WEB("Web 开发"),
    JAVA("面向对象程序设计"),
    ANDROID("安卓开发"),
    IOS("IOS 开发"),
    BIG_DATA("大数据概论");

    private final String displayName;
    CourseName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
