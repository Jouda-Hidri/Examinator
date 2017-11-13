<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New exam</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.exam==null}">
			<h1>Create a new exam</h1>
			<form action="create" method="post">
				Exam title : <input type="text" name="title">
				<input type="submit" value="Create">
			</form>
		</c:when>
		<c:otherwise>
			<h1>${exam.title}</h1>
			<hr>
			<form action="save" method="post">
				<input type="submit" value="Save">
			</form>
			<hr>
			<form action="add" method="post">
				Question : <input type="text" name="title"> <br> <input
					type="radio" name="choice_id" value="choice_1" checked="checked">1st
				choice : <input type="text" name="choice_1"><br> <input
					type="radio" name="choice_id" value="choice_2">2nd choice :
				<input type="text" name="choice_2"><br> <input
					type="radio" name="choice_id" value="choice_3">3nd choice :
				<input type="text" name="choice_3"><br> <input
					type="submit" value="Add">
			</form>
		</c:otherwise>
	</c:choose>

</body>
</html>