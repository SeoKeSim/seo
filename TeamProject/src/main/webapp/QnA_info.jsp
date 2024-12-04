<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ page import="team.beans.QnADAO" %>  
<%@ page import="team.beans.QnADTO" %>
<%@ page import="java.util.List" %>
</head>
<body>
	<%
	String mem_id = (String)session.getAttribute("idKey");
	QnADAO qnaDAO = new QnADAO();
	List<QnADTO> qnaList = qnaDAO.getAllQnAByUserId(mem_id);
	%>
	
	<table>
	  <tr>
	    <th>제목</th>
	    <th>문의 내용</th>    
	    <th>답변</th>
	  </tr>
	<% if (!qnaList.isEmpty()) { %>
	  <% for (QnADTO qna : qnaList) { %>
	    <tr>
	      <td><%= qna.getTitle() %></td>
	      <td><%= qna.getQuestion() %></td>
	      <td><%= qna.getAnswer() != null ? qna.getAnswer() : "답변 대기 중" %></td>
	    </tr>
	  <% } %>
	<% } else { %>
	  <tr>
	    <td colspan="3">No QnA records found.</td>
	  </tr>
	<% } %>
	</table>
</body>
</html>

