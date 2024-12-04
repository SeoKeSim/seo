package team.beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QnADAO {
    public boolean insertQnA(QnADTO qna) {
        String sql = "INSERT INTO QnA (user_id, title, question) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, qna.getUserId());
            pstmt.setString(2, qna.getTitle());
            pstmt.setString(3, qna.getQuestion());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //문의 조회
    public List<QnADTO> getAllQnA() {
        String sql = "SELECT * FROM QnA";
        List<QnADTO> qnaList = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                QnADTO qna = new QnADTO();
                qna.setUniqueId(rs.getInt("unique_id"));
                qna.setUserId(rs.getString("user_id"));
                qna.setTitle(rs.getString("title"));
                qna.setQuestion(rs.getString("question"));
                qna.setAnswer(rs.getString("answer"));
                qnaList.add(qna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qnaList;
    }
    
    public List<QnADTO> getAllQnAByUserId(String userId) {
    	  String sql = "SELECT * FROM QnA WHERE user_id = ?";
    	  List<QnADTO> qnaList = new ArrayList<>();
    	  try (Connection conn = JDBCUtil.getConnection();
    	       PreparedStatement pstmt = conn.prepareStatement(sql)) {
    	    pstmt.setString(1, userId);  
    	    try (ResultSet rs = pstmt.executeQuery()) {
    	      while (rs.next()) {
    	        QnADTO qna = new QnADTO();
    	        qna.setUniqueId(rs.getInt("unique_id"));
    	        qna.setUserId(rs.getString("user_id")); 
    	        qna.setTitle(rs.getString("title"));
    	        qna.setQuestion(rs.getString("question"));
    	        qna.setAnswer(rs.getString("answer"));
    	        qnaList.add(qna);
    	      }
    	    }
    	  } catch (SQLException e) {
    	    e.printStackTrace();
    	  }
    	  return qnaList;  
    	}
    
    public boolean updateAnswer(int uniqueId, String answer) {
        String sql = "UPDATE QnA SET answer = ? WHERE unique_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, answer);
            pstmt.setInt(2, uniqueId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //답변 저장
    public boolean saveAnswer(int uniqueId, String answer) {
        String sql = "UPDATE QnA SET answer = ? WHERE unique_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, answer);
            pstmt.setInt(2, uniqueId);
            int result = pstmt.executeUpdate();
            return result > 0; // 성공 여부 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}