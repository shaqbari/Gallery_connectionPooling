<%@page import="Common.file.FileManager"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="gallery.model.GalleryDAO"%>
<%@page import="gallery.model.Gallery"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/inc/message.jsp" %>
<%!
	GalleryDAO dao=new GalleryDAO();
%>
<%
	request.setCharacterEncoding("utf-8");

	//넘겨받은 파라미터 값들을 이용하여 db에 insert시키고, 파일도 저장(upload)한다.	
	ServletFileUpload upload=new ServletFileUpload(new DiskFileItemFactory());
	upload.setHeaderEncoding("utf-8");
	
	List<FileItem> list=upload.parseRequest(request); //요청 분석
	
	FileItem fileItem=null;
	
	Gallery dto=new Gallery(); //비어있는 dto생성
	//파라미터 갯수만큼 반복만 돌리면서, 조건을 따져보고 파일인지 아닌지  조건을 판단한다.
	for(int i=0; i<list.size(); i++){
		FileItem item=list.get(i);
		if(item.isFormField()){//text 파라미터인 경우
			String name=item.getFieldName();
			out.print("html name is "+name+"<br>");
			//dto에 담을예정이므로 누가 누군지 구분해야 한다.
			String value=item.getString("UTF-8");//getString에 utf-8을 주자	
			
			if(name.equals("writer")){
				dto.setWriter(value);
				
			}else if(name.equals("title")){
				dto.setTitle(value);
				
			}else if(name.equals("content")){
				dto.setContent(value);
				
			}
					
			
		}else{//파일 파라미터인 경우
			//String user_fileName=FilenameUtils.getName(item.getName());
			dto.setUser_filename(item.getName());
			
			fileItem=item;
			
			
			
		}
		
		
	}
	out.print(dto.getContent());
	out.print(dto.getWriter());	
	out.print(dto.getTitle());
	out.print(dto.getUser_filename());
	
	//insert 수행 여기서는 반환값으로 방금 입력된 seq가 온다.
	int result=dao.insert(dto);
	
	//방금 insert된 레코드가 사용하는 시퀀스 값을 파일명에 적용해보자!
	String realPath=application.getRealPath("/data");
	
	//업로드된 파일명으로부터 확장자 구하기
	String ext=FileManager.getExt(dto.getUser_filename());
	String fileName=result+"."+ext;
	
	String path=realPath+File.separator+fileName;
	
	
	InputStream is=fileItem.getInputStream();
	FileOutputStream fos=new FileOutputStream(path);
	
	//Byte[] b=new Byte[1024];소문자로 써야한다.
	int flag;
	byte[] b=new byte[1024];
	while(true){
		flag=is.read(b);
		if(flag==-1)break;
		fos.write(b);
	}
	
	if(fos!=null){
		fos.close();
	}
	if(is!=null){
		fos.close();
	}
	
	
	if(result!=0){//성공한다면
		out.print(showMsgURL(result+"등록 성공", "/board/list.jsp"));
		
	}else{
		out.print(showMsgBack("등록실패"));
		
	}
	
	
%>






