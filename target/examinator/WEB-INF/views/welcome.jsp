<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,examinator.entity.Exam"%>
<!DOCTYPE html>
<html>
<head>
	<title>Spring 4 MVC Hello World Example with Maven Eclipse</title>
</head>
<body>
	<h2>Hello World, Spring MVC</h2>

	<p>Welcome, ${examId}</p>
	<a href="exam/${examId}">exam</a>
</body>