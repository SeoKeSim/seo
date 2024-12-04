<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 세션에서 로그인 여부 확인
    String mem_id = (String) session.getAttribute("idKey");
%>
<header>
    <div class="navbar">
        <ul>
            <li><a href="index.jsp">홈</a></li>
            <li><a href="char_info.jsp">캐릭터 정보</a></li>
            <li><a href="guide2.jsp">가이드</a></li>
            <% if (mem_id == null) { %>
                <!-- 로그인하지 않은 경우 -->
                <li class="right"><a href="login.jsp">로그인</a></li>
                <li><a href="signup.jsp">회원가입</a></li>
            <% } else { %>
                <!-- 로그인한 경우 -->
                <li class="right"><a href="logout.do">로그아웃</a></li>
                
            <% } %>
        </ul>
    </div>
</header>