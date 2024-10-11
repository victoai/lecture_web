package time_table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleDAO {
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String user="scott";
	String password="tiger";
	
	public Connection dbcon() {		
		Connection con=null;
		try {
			Class.forName(driver);
			con  =DriverManager.getConnection(url, user, password);
			if( con != null) System.out.println("db ok");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;		
	}
	
	public void close( AutoCloseable ...a) {
		for( AutoCloseable  item : a) {
		   try {
			item.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
//// 쿼리문을 통해 Subject 가져오기
	public Subject loadSubject(String dbTableName) {
		String sql = "S__Q__L" + dbTableName;	//입력값 활용 SQL문 작성
		Connection con = dbcon();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Subject subject = new Subject();
		
		// 구현중
		
		close(rs, pst, con);
		return subject;
	}
	
	
//// 쿼리문을 통해 테이블에 맞는 !!스케쥴용 SUBJECT 리스트!! 가져오기
	public ArrayList<Subject> loadSubjectList(String dbTableName) {
		String sql = "S__Q__L" + dbTableName;	//입력값 활용 SQL문 작성
		Connection con = dbcon();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		ArrayList<Subject> list = new ArrayList<Subject>();
		
		// 구현중
		
		close(rs, pst, con);
		return list;
	}
	
	
	
	
	//(구현)메소드: 과목정보 불러오기 (Class table)
	
	//(구현)메소드: 코스정보 불러오기 (Course table)
	
	//(구현)메소드: 교수정보 불러오기 (Professor table)
	
	//(구현)메소드: 

}
