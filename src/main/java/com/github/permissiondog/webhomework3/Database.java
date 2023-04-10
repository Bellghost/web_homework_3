package com.github.permissiondog.webhomework3;

import java.util.*;

public class Database {
    private Database() {
    }

    private static final Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }

    private final Map<UUID, Course> courses = new HashMap<>();

    public Course getCourse(UUID id) {
        return courses.get(id);
    }

    public void setCourse(Course course) {
        courses.put(course.id(), course);
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    public void deleteCourse(UUID id) {
        courses.remove(id);
    }

}
