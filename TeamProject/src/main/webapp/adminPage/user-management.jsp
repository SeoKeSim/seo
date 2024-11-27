<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="team.beans.UserDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>사용자 관리</title>
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="css/adminsidebar.css">
</head>
<body>
    <%@ include file="module/adminsidebar.jsp" %>

    <main>
        <section id="user-management">
            <h2>사용자 관리</h2>

            <!-- 성공/실패 메시지 표시 -->
            <%
                String successMessage = (String) request.getAttribute("successMessage");
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (successMessage != null) {
            %>
                <p style="color: green;"><%= successMessage %></p>
            <%
                }
                if (errorMessage != null) {
            %> 
                <p style="color: red;"><%= errorMessage %></p>
            <%
                }
            %>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>상태</th>
                        <th>액션</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<UserDTO> userList = (List<UserDTO>) request.getAttribute("userList");
                        if (userList != null) {
                            for (UserDTO user : userList) {
                    %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getPassword() %></td>
                        <td>
                            <form action="AdminUserController" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="<%= user.getId() %>">
                                <input type="text" name="name" value="<%= user.getName() %>">
                                <input type="text" name="password" value="<%= user.getPassword() %>">
                                <input type="email" name="email" value="<%= user.getEmail() %>">
                                <button type="submit">수정</button>
                            </form>
                            <form action="AdminUserController" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= user.getId() %>">
                                <button type="submit">삭제</button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>