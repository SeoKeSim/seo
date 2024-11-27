package team.beans;

import java.sql.*;


public class UserDAO {

    public UserDTO UserCheck(String id) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserDTO uDTO = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT id, name, email FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
            	uDTO = new UserDTO();
            	uDTO.setId(rs.getString("id"));
            	uDTO.setName(rs.getString("name"));
            	uDTO.setEmail(rs.getString("email"));
            }
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return uDTO;
    }
 // 아이디 중복 체크 메서드
    public boolean idCheck(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isDuplicate = false;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                isDuplicate = (rs.getInt(1) > 0);
            }
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return isDuplicate;
    }

    // 회원가입 메서드
    public boolean insertUser(UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isInserted = false;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO users (id, name, password, email) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());

            int rowsAffected = stmt.executeUpdate();
            isInserted = (rowsAffected > 0);
        } finally {
            JDBCUtil.close(stmt, conn);
        }

        return isInserted;
    }
   //로그인 메서드
    public boolean login(String id, String password) {
        Connection conn = null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          boolean loginCon = false;
          try {
              conn = JDBCUtil.getConnection();
              String sql = "SELECT id, password FROM users WHERE id = ? and password = ?";
              
              stmt = conn.prepareStatement(sql);
              stmt.setString(1, id);
              stmt.setString(2, password);
              rs = stmt.executeQuery();
              loginCon = rs.next();
          } catch (Exception ex) {
              System.out.println("Exception" + ex);
          } finally {
          JDBCUtil.close(rs, stmt, conn);
          }
          return loginCon;
      }
    //정보수정 메서드
    public boolean updateUserInfo(UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isUpdated = false;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getId());
            
            int rowsAffected = stmt.executeUpdate();
            isUpdated = (rowsAffected > 0);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
        
        return isUpdated;
    }
}

