<%@page import="team.beans.InquiryHistoryDAO"%>
<%@page import="team.beans.QnADAO"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- ArrayList 클래스 임포트 -->
<%@ page import="team.beans.QnADTO" %> <!-- QnADTO 클래스 임포트 -->
<!DOCTYPE html>
<html lang="ko">  
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>문의 내역</title>
    <link rel="stylesheet" href="css/inquiryHistory.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>
	<%@include file="module/headermain.jsp" %>
	
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
                <% 
                    // request 객체에서 "inquiryList" 속성을 가져오고, null일 경우 빈 리스트로 초기화
                    InquiryHistoryDAO inquiryhistoryDAO = new InquiryHistoryDAO();
                	String userId = (String)session.getAttribute("idKey");
                    List<QnADTO> inquiryList = inquiryhistoryDAO.getInquiryHistory(userId);
                    if (inquiryList == null) {
                        inquiryList = new ArrayList<>();  // null일 경우 빈 리스트로 초기화
                    }

                    // 문의 내역이 없는 경우
                    if (inquiryList.isEmpty()) {
                %>
                    <tr>
                        <td colspan="3" style="text-align:center; color: #999;">아직 문의 내역이 없습니다.</td>
                    </tr>
                <% 
                    } else {
                        // 문의 내역 출력
                        for (QnADTO inquiry : inquiryList) {
                %>
                    <tr>
                        <td><%= inquiry.getTitle() %></td> <!-- 문의 제목 -->
                        <td><%= inquiry.getQuestion() %></td> <!-- 문의 내용 -->
                        <td>
                            <% 
                                // 관리자 답변이 없는 경우 "답변 대기 중" 표시
                                if (inquiry.getAnswer() == null || inquiry.getAnswer().isEmpty()) {
                                    out.print("답변 대기 중");
                                } else {
                                    out.print(inquiry.getAnswer());
                                }
                            %>
                        </td>
                    </tr>
                <% 
                        } // for 문 끝
                    } // else 끝
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
