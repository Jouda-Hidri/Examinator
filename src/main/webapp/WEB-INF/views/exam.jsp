<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,examinator.entity.Exam"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>List of exams</h1>
	<hr>
	<hr>
	<hr>
	${test}
	<ul>
		<c:forEach items="${listExams}" var="exam">
			<li>${exam.title}</li>
		</c:forEach>
	</ul>
</body>
</html>