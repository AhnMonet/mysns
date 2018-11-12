<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  import="mysns.member.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="memDao" scope="page" class="mysns.member.MemberDAO"/>
<jsp:useBean id="member" class="mysns.member.Member"/>
<jsp:setProperty name="member" property="*"/>
<%
	String action = request.getParameter("action");
	switch(action) {
		case "new":
			if(memDao.addMember(member)) {
				out.println("<script>alert('정상적으로 등록 되었습니다. 로그인하세요.'); window.close();</script>");
			} else {
				out.println("<script>alert('같은 아이디가 존재합니다.'); history.go(-1);</script>");
			}
		break;
		case "login":
			if(memDao.login(member.getUid(), member.getPasswd())) {
				session.setAttribute("uid", member.getUid());
				Member mem = memDao.getMember(member.getUid());
				session.setAttribute("name", mem.getName());
				response.sendRedirect("sns_control.jsp?action=getall");
			} else {
				out.println("<script>alert('아이디나 비밀번호가 틀렸습니다.'); history.go(-1);</script>");
			}
			break;
		case "logout":
			session.removeAttribute("uid");
			response.sendRedirect("sns_control.jsp?action=getall");
			break;
}
%>
