<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*,examinator.entity.Exam"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>
	<h1>Result</h1>
	<hr>
	<ul>
		<c:forEach items="${answerList}" var="answer">
			<li>${answer.choice.title} is ${answer.choice.correct}</li>
		</c:forEach>
	</ul>

</body>
</html>