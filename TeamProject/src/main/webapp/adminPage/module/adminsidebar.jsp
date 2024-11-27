<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="sidebar">
    <h1><a href="<%= request.getContextPath() %>/adminPage/admin.jsp">관리자 페이지</a></h1>
    <nav>
        <ul>
            <!-- 사용자 관리 -->
            <li>
                <form action="<%= request.getContextPath() %>/AdminUserController" method="get">
                    <input type="hidden" name="action" value="viewUsers">
                    <button type="submit">사용자 관리</button>
                </form>
            </li>

            <!-- 문의 확인 -->
            <li>
                <form action="<%= request.getContextPath() %>/adminPage/QnA.jsp" method="get">
                    <button type="submit">문의 확인</button>
                </form>
            </li>

            <!-- 로그 확인 -->
            <li>
                <form action="<%= request.getContextPath() %>/adminPage/log.jsp" method="get">
                    <button type="submit">로그 확인</button>
                </form>
            </li>
        </ul>
    </nav>
</aside>