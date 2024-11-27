package team.beans;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet("/user")
public class UserCheck extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id"); // URL 파라미터에서 'id' 값 가져오기

        try {
            
        	UserDAO uDAO = new UserDAO(); // DAO 객체 생성
            UserDTO uDTO = uDAO.UserCheck(userId);

            if (uDTO != null) {
                // 사용자 정보가 있으면 request에 세팅 후 JSP로 포워딩
                request.setAttribute("user", uDTO);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/userInfo.jsp");
                dispatcher.forward(request, response);
            } else {
            	response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('사용자를 찾을 수 없습니다.');");
                response.getWriter().println("window.history.back();");  // 이전 페이지로 돌아가게 할 수도 있음
                response.getWriter().println("</script>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("데이터베이스 오류 발생.");
        }
    }
}