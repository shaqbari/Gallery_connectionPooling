<%@page import="Common.file.FileManager"%>
<%@page import="gallery.model.Gallery"%>
<%@page import="java.io.File"%>
<%@page import="gallery.model.GalleryDAO"%>
<%@ page contentType="text/html;charset=utf-8"%><!-- include하려면 띄어쓰기까지 똑같아야 한다! 복사 붙여넣기 하자  -->
<%@ include file="/inc/message.jsp" %>
<%!
	GalleryDAO dao=new GalleryDAO();

%>
<%
	//클라이언트가 전송한 gallery_id파라미터를 이용하여
	//db1건 삭제 후 실제 파일도 지우고, list.jsp요청
	
	String gallery_id=request.getParameter("gallery_id");
	String ext=request.getParameter("ext");

	int result=dao.delete(Integer.parseInt(gallery_id));	
	if(result!=0){ //삭제 성공했다면
		out.print(showMsgURL("삭제 성공", "/board/list.jsp"));
		
		//실제 파일도 지워야 한다.
		String realPath=application.getRealPath("/data/"+gallery_id+"."+ext);
		out.print(realPath);
		
		/* Gallery dto=dao.select(Integer.parseInt(gallery_id));	
		String ext=FileManager.getExt(dto.getUser_filename());
	
		String fileName=gallery_id+"."+ext; ext도 넘겨받자*/		
		//String path=Integer.parseInt(gallery_id)+File.separator+fileName;
		
		File file=new File(realPath);
		file.delete();
		
	
	}else{ //실패했다면
		out.print(showMsgBack("삭제 실패"));
		
	}

%>