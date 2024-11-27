 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 페이지</title>
    <link rel="stylesheet" href="css/adminsidebar.css">
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
    <%-- 사이드바 포함 --%>
    <%@include file="module/adminsidebar.jsp" %>

    <main>
        <section id="user-management">
            <h2>사용자 관리</h2>
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
                    <tr>
                        <td>1</td>
                        <td>홍길동</td>
                        <td>hong@example.com</td>
                        <td>활성</td>
                        <td><button>수정</button> <button>삭제</button></td>
                    </tr>
                </tbody>
            </table>
        </section>
        <section id="QnA">
            <h2>문의 확인</h2>
            <p>문의를 답변함</p>
        </section>
        <section id="log">
            <h2>로그 확인</h2>
            <p>로그를 확인함</p>
        </section>
    </main>
</body>
</html>