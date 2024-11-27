package team.beans;

import java.sql.*;


public class UserDAO {

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
            
            
}