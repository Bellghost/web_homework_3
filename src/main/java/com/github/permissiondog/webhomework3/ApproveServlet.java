package com.github.permissiondog.webhomework3;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "ApproveServlet", value = "/api/approve")
public class ApproveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 查询审批
        var r = Database.getInstance().getAllCourses().stream().map(Course::toString).collect(Collectors.joining(","));
        Util.json(response, "[" + r + "]");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 同意/驳回
        var id = request.getParameter("id");
        var type = request.getParameter("type");
        var comment = request.getParameter("comment");
        boolean approve;
        switch (type) {
            case "approve" -> approve = true;
            case "reject" -> approve = false;
            default -> {
                Util.json(response, "{\"ok\":false}");
                return;
            }
        }

        var oldCourse = Database.getInstance().getCourse(UUID.fromString(id));
        if (oldCourse == null || !oldCourse.status().equals(CourseStatus.DEFAULT)) {
            Util.json(response, "{\"ok\":false}");
            return;
        }
        Database.getInstance().setCourse(new Course(oldCourse.id(), oldCourse.name(), approve ? CourseStatus.APPROVED : CourseStatus.REJECTED, comment, oldCourse.creationTime()));
        Util.json(response, "{\"ok\":true}");
    }


}
