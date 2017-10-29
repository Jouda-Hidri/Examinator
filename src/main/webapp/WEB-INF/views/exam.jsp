<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,examinator.entity.Exam"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${exam.title}</h1>
	<hr>

	<ul>
		<c:forEach items="${exam.questions}" var="question">
			<li><a href="../question/${question.id}">Question :
					${question.title}</a></li>

		</c:forEach>
	</ul>
	<hr>
	<p><a href="../first/${exam.id}">start</a><p>
</body>
</html>