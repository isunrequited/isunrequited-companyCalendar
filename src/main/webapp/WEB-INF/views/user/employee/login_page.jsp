<%@page import="com.twotrillion.comcal.user.employee.vo.EmployeeVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="../../include/title.jsp"/>

	<!-- css -->
	<link href="<c:url value='/resources/css/user/employee/login_page.css' />" rel="stylesheet" type="text/css">

	<!-- js -->
	<jsp:include page="../../include/common_js.jsp"/>
	<script src="<c:url value='/resources/js/user/employee/login_page.js' />" type="text/javascript"></script>
</head>
	
<body>
	<!-- 로고 -->
	<div class="company_logo">
		<br>
		<br>
		<br>
		<h1>TwoTrillion</h1>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
	</div>
	
	<!-- 로그인 -->
	<div class="login-box">
		<h2>Login</h2>

		<!-- 로그인 폼 -->
		<form action="<c:url value='/member/login_confirm' />" name="login_form" method="post">

			<div class="user-box">
				<input type="text" name="emp_email" required="">
				<label>Email</label>
			</div>

			<div class="user-box">
				<input type="password" name="emp_pw" required="">      
				<label>Password</label>
			</div>

			<div class="submit">
				<a href="#" onclick="loginForm();"> 
					<span></span>
					<span></span>
					<span></span>
					<span></span>
					Submit
				</a>
			</div>

			<!-- 임직원 더미 데이터 생성 버튼 -->
			<a href="#" onclick="create_temp_account();">dummy</a>
		</form>
	</div>
	
</body>

</html>