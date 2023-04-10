package com.github.permissiondog.webhomework3;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {
    public static void json(HttpServletResponse res, String json) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        res.getWriter().print(json);
    }
}
