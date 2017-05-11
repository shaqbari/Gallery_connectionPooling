package gallery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pool.DBManager;
import pool.PoolManager;

public class GalleryDAO {
	
	PoolManager manager=PoolManager.getInstance();
	//DBManager manager=DBManager.getInstance();
	
	public int insert(Gallery dto){
		Connection con=manager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		
		String sql="insert into GALLERY (GALLERY_ID, WRITER, TITLE, CONTENT, USER_FILENAME)";
		sql+="VALUES (seq_gallery.nextVal, ?, ?, ?, ?)";
		
		//con=manager.getConnection();
		if (con!=null) {
			System.out.println("GalleryDAO에서 접속 성공");
		}else{
			System.out.println("GalleryDAO에서 접속 실패");
						
		}
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getUser_filename());
			
			int exe=pstmt.executeUpdate();
			
			
			if(exe!=0){
				//dual테이블을 이용해 현재 seq를 조회할 수 있다.
				sql="select seq_gallery.currVal as seq from dual";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				if (rs.next()) {
					result=rs.getInt("seq");
				}
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		manager.freeConnection(con, pstmt, rs);
		
		return result;
	}

}
