package team.beans;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/user")
public class UserLoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id"); // URL 파라미터에서 'id' 값 가져오기
        String password = request.getParameter("password");
        
        UserDAO uDAO = new UserDAO(); // DAO 객체 생성
        boolean loginCheck = uDAO.login(id, password);
        
        if (loginCheck && id.equals("admin")) {
            request.setAttribute("loginResult", loginCheck);
            HttpSession session = request.getSession();
            session.setAttribute("idKey", id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminPage/admin.jsp");
            dispatcher.forward(request, response);
        } else if (loginCheck) {
            request.setAttribute("loginResult", loginCheck);
            HttpSession session = request.getSession();
            session.setAttribute("idKey", id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } else {
            // 로그인 실패 시 오류 메시지만 설정하고 아이디와 비밀번호는 초기화
            request.setAttribute("loginError", "아이디나 비밀번호가 일치하지 않습니다.");
            // 아이디와 비밀번호를 초기화하여 전달하지 않음
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
