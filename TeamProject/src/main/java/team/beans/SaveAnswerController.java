package team.beans;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveAnswer.do")
public class SaveAnswerController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int uniqueId = Integer.parseInt(request.getParameter("uniqueId"));
        String answer = request.getParameter("answer");

        QnADAO qnaDAO = new QnADAO();
        boolean isUpdated = qnaDAO.saveAnswer(uniqueId, answer);

        if (isUpdated) {
            response.sendRedirect("adminPage/adminQnA.jsp?success=true");
        } else {
            response.sendRedirect("adminPage/adminQnA.jsp?error=true");
        }
    }
}