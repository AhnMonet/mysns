<%@ tag body-content="scriptless" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form name="loginform" method="post" action="user_control.jsp">
<c:choose>
<c:when test="${uid != null }">
	<li><a href="#"> :: </a></li>
	<li><a href="sns_control.jsp?action=getall&suid=${uid }">${sessionScope.name }님 글 모아보기</a></li>
	<input type="hidden" name="action" value="logout">
	&nbsp;<input type="submit" value="로그아웃">
</c:when>
<c:otherwise>
	<li><a href="#"> :: </a></li>
	<li><a href="#">Login</a></li>&nbsp;
		<input type="hidden" name="action" value="login"><input type="text" name="cid" size="10">
		&nbsp;<input type="password" name="cpassword" size="10">&nbsp;&nbsp;<input type="submit" value="로그인">
</c:otherwise>
</c:choose>
</form>
