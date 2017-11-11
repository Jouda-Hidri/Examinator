<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${sessionScope.evaluation.id}</title>
</head>
<body>
	<c:choose>
		<c:when test="${question==''}">
			<p>
				<a href="../result">see result</a>
			</p>
			<br />
		</c:when>
		<c:otherwise>
			<h1>${question.title}</h1>
			<hr>
			<form action="../next/${question.id}" method="post">
				<c:forEach items="${question.choices}" var="choice">
					<input type="radio" name="choice_id" value="${choice.id}">Choice : ${choice.title}<br>
				</c:forEach>
				<input type="submit" value="next">
			</form>
			<br />
		</c:otherwise>
	</c:choose>
</body>
</html>