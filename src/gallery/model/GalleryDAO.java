package gallery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List select(){		
		Connection con=manager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//DAO만들때는 쿼리문부터 작성하는 것이 좋다.
		String sql="select * FROM GALLERY order by GALLERY_ID DESC";
		ArrayList<Gallery> list=new ArrayList<Gallery>();
		
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Gallery dto=new Gallery();
				dto.setGallery_id(rs.getInt("gallery_id"));
				dto.setContent(rs.getString("content"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
				dto.setUser_filename(rs.getString("user_filename"));
				
				list.add(dto);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			manager.freeConnection(con, pstmt, rs);			
			return list;
			
		}		
		
	}
	
	public Gallery select(int gallery_id){		
		Connection con=manager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//DAO만들때는 쿼리문부터 작성하는 것이 좋다.
		String sql="select * FROM GALLERY where gallery_id=?";
		Gallery dto=null;
		
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, gallery_id);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new Gallery();
				
				dto.setGallery_id(rs.getInt("gallery_id"));
				dto.setContent(rs.getString("content"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
				dto.setUser_filename(rs.getString("user_filename"));
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			manager.freeConnection(con, pstmt, rs);			
			return dto;
			
		}		
		
	}
	
	//레코드 한건 삭제
	public int delete(int gallery_id){
		String sql="delete from gallery where gallery_id=?";
		Connection con=manager.getConnection();
		PreparedStatement pstmt=null;
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, gallery_id);			
			result=pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			manager.freeConnection(con, pstmt);
			
		}
		
		
		return result;
	}
	

}
