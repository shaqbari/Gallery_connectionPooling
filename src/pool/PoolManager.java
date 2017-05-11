package pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolManager {
	private static PoolManager instance;
	
	InitialContext context;
	DataSource ds;
	
	private PoolManager(){
		try {
			context=new InitialContext();
			ds=(DataSource) context.lookup("java:comp/env/jdbc/myoracle");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static PoolManager getInstance() {
		/*if (instance!=null) {
			instance=new PoolManager();
		}*/
		if (instance==null) {//null�϶� �����. ��ȿ���˻�� �򰥸��� ����
			instance=new PoolManager();
		}
		
		return instance;
	}
	
	public Connection getConnection(){
		Connection con=null;
		try {
			con=ds.getConnection();
			
			if (con!=null) {
				System.out.println("PoolManager���� ���Ӽ���");
			}else{
				System.out.println("PoolManager���� ���ӽ���");
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void freeConnection(Connection con, PreparedStatement pstmt){
		if (con!=null) {
			try {
				con.close();
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
	}
	
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs){
		if (con!=null) {
			try {
				con.close();
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
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}






