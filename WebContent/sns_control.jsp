<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mysns.sns.*, mysns.member.*, java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="msg" class="mysns.sns.Message"/>
<jsp:useBean id="msgDao" class="mysns.sns.MessageDAO"/>
<jsp:useBean id="reply" class="mysns.sns.Reply"/>
<jsp:setProperty name="msg" property="*"/>
<jsp:setProperty name="reply" property="*"/>

<%
	String action = request.getParameter("action");
	String cnt = request.getParameter("cnt");
	String suid = request.getParameter("suid");
	request.setAttribute("curmsg", request.getParameter("curmsg"));
	String home;
	int mcnt;
	if((cnt != null) && (suid != null)) {
		home = "sns_control.jsp?action=getall&cnt=" + cnt + "&suid=" + suid;
		mcnt = Integer.parseInt(request.getParameter("cnt"));
	} else {
		home = "sns_control.jsp?action=getall";
		mcnt = 5;
	}

	switch(action) {
		case "getall":
			ArrayList<MessageSet> messageList = msgDao.getAll(mcnt, suid);
			MemberDAO memDao = new MemberDAO();
			ArrayList<String> newmemlist = new MemberDAO().getNewMembers();
			
			request.setAttribute("messageList", messageList);
			request.setAttribute("newmemlist", newmemlist);
			request.setAttribute("suid", suid);
			request.setAttribute("cnt", mcnt);
			pageContext.forward("sns_main.jsp");
			break;
		case "newmsg":
			if(msgDao.newMsg(msg)) {
				response.sendRedirect(home);
			} else {
				throw new Exception("메시지 등록 오류");
			}
			break;
		case "delmsg":
			if(msgDao.delMsg(msg.getMid())) {
				response.sendRedirect(home);
			} else {
				throw new Exception("메시지 등록 오류");
			}
			break;
		case "newreply":
			if(msgDao.newReply(reply)) {
				pageContext.forward(home);
			} else {
				throw new Exception("메시지 등록 오류");
			}
			break;
		case "delreply":
			if(msgDao.delReply(reply.getRid())) {
				pageContext.forward(home);
			} else {
				throw new Exception("메시지 등록 오류");
			}
			break;
		case "fav":
			msgDao.favorite(msg.getMid());
			pageContext.forward(home);
			break;
	}	
%>