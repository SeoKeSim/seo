<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, team.beans.QnADAO, team.beans.QnADTO" %>
<%
    QnADAO qnaDAO = new QnADAO();
    List<QnADTO> qnaList = qnaDAO.getAllQnA();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 확인</title>
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="css/adminsidebar.css">
</head>
<body>
    <%@ include file="module/adminsidebar.jsp" %>

    <main>
        <section id="adminQnA">
            <h2>문의 확인</h2>
            <p>문의 내용을 확인하고 답변을 작성합니다.</p>

            <!-- 문의 목록 테이블 -->
            <table border="1">
                <thead>
                    <tr>
                        <th>문의 ID</th>
                        <th>사용자 ID</th>
                        <th>제목</th>
                        <th>문의 내용</th>
                        <th>답변</th>
                        <th>조치</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (QnADTO qna : qnaList) {
                    %>
                    <tr>
                        <td><%= qna.getUniqueId() %></td>
                        <td><%= qna.getUserId() %></td>
                        <td><%= qna.getTitle() %></td>
                        <td><%= qna.getQuestion() %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/saveAnswer.do" method="post">
                                <input type="hidden" name="uniqueId" value="<%= qna.getUniqueId() %>">
                                <textarea name="answer" rows="2" cols="30"><%= qna.getAnswer() %></textarea>
                                <button type="submit">저장</button>
                            </form>
                        </td>
                        <td>
                            <% if (qna.getAnswer() != null && !qna.getAnswer().isEmpty()) { %>
                                <span class="status-completed">답변 완료</span>
                            <% } else { %>
                                <span class="status-pending">대기 중</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>