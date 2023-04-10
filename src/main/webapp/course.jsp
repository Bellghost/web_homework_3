<%@ page import="com.github.permissiondog.webhomework3.Database" %>
<%@ page import="com.github.permissiondog.webhomework3.Course" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.github.permissiondog.webhomework3.CourseName" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程选择</title>
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
        #course-select {
            height: 50px;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
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
        const apply = async () => {
            const course = document.querySelector('#course-selected').value
            const data = new FormData()
            data.set('name', course)
            const res = await fetch('api/course', {
                method: 'post',
                body: new URLSearchParams(data),
            })
            const result = await res.json()
            if (result.ok) {
                alert('申请成功')
                location.reload()
            } else {
                alert('选课失败')
            }
        }
        const editCourse = async (id, newName) => {
            const data = new FormData()
            data.set('id', id)
            data.set('name', newName)

            const res = await fetch('api/course?' + new URLSearchParams(data), {
                method: 'put',
            })

            const result = await res.json()
            if (result.ok) {
                alert('修改成功')
                location.reload()
            } else {
                alert('修改失败')
            }
            console.log(id, newName)
        }

        const deleteCourse = async (id) => {
            const data = new FormData()
            data.set('id', id)

            const res = await fetch('api/course?' + new URLSearchParams(data), {
                method: 'delete',
            })

            const result = await res.json()
            if (result.ok) {
                alert('删除成功')
                location.reload()
            } else {
                alert('删除失败')
            }
            console.log(id)
        }
    </script>
</head>
<body>
    <div id="course-select">
        <label>
            课程选择:
            <select id="course-selected">
                <%
                    for (CourseName cn : CourseName.values()) {
                %>
                <option><%=cn.getDisplayName()%></option>
                <%
                    }
                %>
            </select>
        </label>
        <input type="button" style="margin-left: 10px;" value="申请" onclick="apply()">
    </div>
    <table id="course-list">
        <tr>
            <td>课程名称</td>
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
                <td><input value="<%=course.name()%>" onblur="editCourse('<%=course.id()%>', this.value)"></td>
                <td><div><%=course.status().getDisplayName() %></div></td>
                <td><div><%=course.comment()%></div></td>
                <td><div><%=course.creationTime().format(DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss"))%></div></td>
                <td><input type="button" value="删除" onclick="deleteCourse('<%=course.id()%>')"> </td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
