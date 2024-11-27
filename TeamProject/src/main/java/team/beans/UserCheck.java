package team.beans;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet("/user")
public class UserCheck extends HttpServlet {

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String id = request.getParameter("id"); // URL 파라미터에서 'id' 값 가져오기
        String password = request.getParameter("password");
             
        UserDAO uDAO = new UserDAO(); // DAO 객체 생성
        boolean loginCheck = uDAO.login(id, password);
            
            if(loginCheck){
        request.setAttribute("loginResult", loginCheck);
    HttpSession session = request.getSession();
    session.setAttribute("idKey", id);
    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
    dispatcher.forward(request, response);

    }else{
          response.sendRedirect("LogError.jsp");
    }
       

      
    }


    
}