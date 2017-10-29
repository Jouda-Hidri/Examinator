<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${question.title}</h1>
	<hr>
	<form action="../result" method="post">
		<c:forEach items="${question.choices}" var="choice">
			<input type="radio" name="choiceId" value="${choice.id}">Choice : ${choice.title}<br>
		</c:forEach>
		<input type="submit" value="submit">
	</form>
</body>
</html>