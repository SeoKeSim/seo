<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>나이계산 서비스</title>
</head>
<body>
	<h1>10년 후 나이 계에에에에에에산</h1>
	<h2>오 폼 태그 넣으니까 서브밋 이랑 리셋 작동 하네</h2>
	<form action="calc2.do" method="post">
		현재 나이 입력: <input type="number" id="" class="a1" name="age"><br>
		성별: 남자 <input type="radio" name="gender" value="g1"> 여자 <input type="radio" name="gender" value="g2"> <br>
		지역: 서울 <input type="radio" name="local"> 부산 <input type="radio" name="local"> 대전 <input type="radio" name="local"> <br>
		<input type="submit" value="확인"><input type="reset" value="취소">
	</form>
	<input type="checkbox"> 로그인 유지
</body>
</html>