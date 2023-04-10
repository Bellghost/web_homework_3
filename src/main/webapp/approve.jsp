<%@ page import="com.github.permissiondog.webhomework3.Database" %>
<%@ page import="com.github.permissiondog.webhomework3.Course" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审批</title>
    <style>
        body {
            width: 100%;
            height: 100%;
            margin: 0;

            display: flex;
            flex-direction: column;
            justify-content : center;
            align-items: center;
        }
        #course-list {
            flex: 1;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-items: center;
        }
        .course-row {
            margin: 5px;
        }
    </style>
    <script>
        const approve = async (id, accept) => {
            const comment = prompt('请输入备注', '无')
            const data = new FormData()
            data.set('id', id)
            data.set('comment', comment)
            data.set('type', accept ? 'approve' : 'reject')
            const res = await fetch('api/approve', {
                method: 'post',
                body: new URLSearchParams(data),
            })
            const result = await res.json()
            if (result.ok) {
                alert('审批成功')
                location.reload()
            } else {
                alert('审批失败')
            }
        }
    </script>
</head>
<body>
<table id="course-list">
    <tr>
        <td>审批名称</td>
        <td>状态</td>
        <td>备注</td>
        <td>创建时间</td>
        <td>操作</td>
    </tr>
    <%
        List<Course> courses = Database.getInstance().getAllCourses().stream().sorted(
                Comparator.comparing(Course::creationTime).reversed()
        ).toList();
        for (Course course : courses) {
    %>
    <tr class="course-row">
        <td><%=course.name()%></td>
        <td><div><%=course.status().getDisplayName() %></div></td>
        <td><div><%=course.comment()%></div></td>
        <td><div><%=course.creationTime().format(DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss"))%></div></td>
        <td><input type="button" value="同意" onclick="approve('<%=course.id()%>', true)" style="margin-right: 5px">
        <input type="button" value="拒绝" onclick="approve('<%=course.id()%>', false)"> </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
