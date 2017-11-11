<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,examinator.entity.Exam"%>
<!DOCTYPE html>
<html>
<head>
<title>Examinator</title>
</head>
<body>
	<h1>List of exams</h1>
	<ul>
		<c:forEach items="${listExams}" var="exam">
			<li><a href="first/${exam.id}">${exam.title}</a></li>
		</c:forEach>
	</ul>
	<hr>
	<h1>List of evaluations</h1>
	<ul>
		<c:forEach items="${listEvaluations}" var="evaluation">
			<li>${evaluation.date} - <a href="evaluation/${evaluation.id}">See Evaluation</a></li>
		</c:forEach>
	</ul>
</body>