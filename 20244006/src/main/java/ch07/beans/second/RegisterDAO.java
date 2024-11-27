package ch07.beans.second;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterDAO {
	
	String jdbcDriver="jdbc:mysql://localhost:3306/jspdb?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8"; //DB서버,db명
	String dbUser="root"; //계정
	String dbPass="dongyang"; //비번
	Connection conn=null;
	PreparedStatement pstmt=null;
	/* 기본설정? */
	
	public RegisterDAO(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} /* 1단계 */
	
	public void insertMember(RegisterDTO mem) throws SQLException {
		try{

			conn=DriverManager.getConnection( jdbcDriver , dbUser , dbPass);
			pstmt=conn.prepareStatement("insert into membertbl values ( ?, ?, ?, ?);");
			pstmt.setString(1, mem.getMemberid()  );
			pstmt.setString(2, mem.getPassword() );
			pstmt.setString(3, mem.getName() );
			pstmt.setString(4, mem.getEmail() );
			pstmt.executeUpdate();
			
		} catch(Exception e){
			
		} finally{
			conn.close();
			pstmt.close();
		}	
		
	}

	public ArrayList<RegisterDTO> selectMemberList() throws SQLException {
		ResultSet rs=null;;
		ArrayList<RegisterDTO> aList=new ArrayList<RegisterDTO>();
		try{

			conn=DriverManager.getConnection( jdbcDriver , dbUser , dbPass);
			pstmt=conn.prepareStatement("select * from membertbl;");
			rs=pstmt.executeQuery();
	
			while(rs.next()) {
				RegisterDTO rd=new RegisterDTO();
				rd.setMemberid( rs.getString("memberid") );
				rd.setPassword( rs.getString("password") );
				rd.setName( rs.getString("name") );
				rd.setEmail( rs.getString("email") );
				aList.add(rd);
			}
		
		} catch(Exception e){
			
		} finally{
			conn.close();
			pstmt.close();
		} /* 4단계 */
		
		return aList;
		
	}		

}






