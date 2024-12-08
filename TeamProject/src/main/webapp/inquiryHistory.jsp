<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>문의 내역</title>
    <link rel="stylesheet" href="css/inquiryHistory.css">
</head>
<body>
    <div class="inquiry-container">
        <h2>문의 내역</h2>

        <table class="inquiry-table">
            <thead>
                <tr>
                    <th>문의 제목</th>
                    <th>문의 내용</th>
                    <th>관리자 답변</th>
                </tr>
            </thead>
            <tbody>
                <!-- 문의 내역이 없을 때 -->
                <tr>
                    <td colspan="3" style="text-align:center; color: #999;">아직 문의 내역이 없습니다.</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
