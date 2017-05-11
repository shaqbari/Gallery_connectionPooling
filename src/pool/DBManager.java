package pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author user1
 * 커넥션 객체를 필요로 하는 외부 객체들에게 Connection을 제공해주는 메니저
 * javaSE에서 사용했더 메니저와는 틀리다
 * 어떤 부분에서 틀리나?
 * 이메니저는 JNDI로 부터 커넥션을 얻어올 것이다.
 * 
 * 공통점: 싱글톤으로 개발예정
 * 
 * 
 */
public class DBManager {	
	
	private static DBManager instance;
	
	InitialContext context; //검색객체
	DataSource ds; //커넥션을 얻기 위한 객체 javax.sql.에 속한것이어야 한다.
	
	
	//1. new 할수 없게 생성자를 막는다.
	private DBManager(){
		try {
			context=new InitialContext();
			ds=(DataSource)context.lookup("java:comp/env/jdbc/myoracle");//server.xml을 검색하는 검색어로 검색한다.
								
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static DBManager getInstance() {
		if (instance==null) {
			instance=new DBManager();
		}
		
		return instance;
	}
	
	//커넥션 얻어오는 메소드	
	public Connection getConnection(){
		Connection con=null;
		
		try {
			con=ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};	
		
		return con;
	}
	
	//커넥션 반납
	public void freeConnection(Connection con, PreparedStatement pstmt){
		if (pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//rs를 추가 이용하는 경우 오버로딩
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs){
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		if (pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}










