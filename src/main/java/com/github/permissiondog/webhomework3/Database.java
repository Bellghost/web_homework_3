package com.github.permissiondog.webhomework3;

public class Database {
    private Database() {

    }

    private static final Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }


}
