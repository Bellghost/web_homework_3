package com.github.permissiondog.webhomework3;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "CourseServlet", value = "/api/course")
public class CourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 已申请课程查询
        var r = Database.getInstance().getAllCourses().stream().map(Course::toString).collect(Collectors.joining(","));
        Util.json(response, "[" + r + "]");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 课程申请
        var name = request.getParameter("name");
        if (name == null) {
            Util.json(response, "{\"ok\":false}");
            return;
        }
        Database.getInstance().setCourse(new Course(UUID.randomUUID(), name, CourseStatus.DEFAULT, "", LocalDateTime.now()));
        Util.json(response, "{\"ok\":true}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 课程修改
        var id = request.getParameter("id");
        log(id);
        var name = request.getParameter("name");
        log(name);
        if (id == null || name == null) {
            Util.json(response, "{\"ok\":false}");
            return;
        }
        var oldCourse = Database.getInstance().getCourse(UUID.fromString(id));
        if (oldCourse == null) {
            Util.json(response, "{\"ok\":false}");
            return;
        }
        Database.getInstance().setCourse(new Course(oldCourse.id(), name, oldCourse.status(), oldCourse.comment(), oldCourse.creationTime()));
        Util.json(response, "{\"ok\":true}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 课程删除
        var id = request.getParameter("id");
        var course = Database.getInstance().getCourse(UUID.fromString(id));
        if (course == null) {
            Util.json(response, "{\"ok\":false}");
            return;
        }
        Database.getInstance().deleteCourse(UUID.fromString(id));
        Util.json(response, "{\"ok\":true}");
    }


}
