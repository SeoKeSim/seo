<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 페이지</title>
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
    <header>
        <h1>관리자 페이지</h1>
        <nav>
            <ul>
                <li><a href="#dashboard">대시보드</a></li>
                <li><a href="#user-management">사용자 관리</a></li>
                <li><a href="#content-management">콘텐츠 관리</a></li>
                <li><a href="#settings">설정</a></li>
            </ul>
        </nav>
    </header>
    <main>
        <section id="dashboard">
            <h2>대시보드</h2>
            <p>여기에 통계 정보를 표시합니다.</p>
        </section>
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
        <section id="content-management">
            <h2>콘텐츠 관리</h2>
            <p>콘텐츠 추가 및 수정 작업을 여기에 구현합니다.</p>
        </section>
        <section id="settings">
            <h2>설정</h2>
            <p>시스템 설정을 변경하는 섹션입니다.</p>
        </section>
    </main>
    <footer>
        <p>&copy; 2024 관리자 페이지</p>
    </footer>
</body>
</html>