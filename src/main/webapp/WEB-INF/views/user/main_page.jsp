<%@page import="com.twotrillion.comcal.user.employee.vo.EmployeeVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="./include/title.jsp"/>

	<!-- Mobiscroll JS and CSS Includes -->
	<link href="<c:url value='/resources/css/user/include/mobiscroll.javascript.min.css'/>" rel="stylesheet" />
	<script src="<c:url value='/resources/js/user/include/mobiscroll.javascript.min.js'/>"></script>

	<!-- css -->
	<link href="<c:url value='/resources/css/user/include/common.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/resources/css/user/include/datepicker.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/resources/css/user/main_page.css'/>" rel="stylesheet" type="text/css">

	<!-- js -->
	<jsp:include page="./include/common_js.jsp"/>
	<script src="<c:url value='/resources/js/user/main_page.js'/>" type="text/javascript"></script>
    
</head>

<body>
	<div id="body_wrap">
		<jsp:include page="./include/header.jsp"/>
		
		<div class="content_wrap">
			<jsp:include page="./calendar/calendar_view.jsp"/>
			
			<jsp:include page="./project/project_view.jsp"/>
			
			<section id="attendance_section">
			</section>
		</div>
		
		<jsp:include page="./include/footer.jsp"/>
	</div>

</body>
</html>