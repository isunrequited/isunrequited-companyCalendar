<%@page import="com.twotrillion.comcal.user.employee.vo.EmployeeVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="../include/title.jsp"/>

	<!-- css -->
	<link href="<c:url value="/resources/css/include/common.css"/>" rel="stylesheet" type="text/css">

	<!-- js -->
	<jsp:include page="../include/common_js.jsp"/>
</head>

<body>
	<jsp:include page="./include/header.jsp"/>
	
	<section>
		<jsp:include page="./calendar/calendar_view.jsp"/>
	</section>

	<jsp:include page="../include/footer.jsp"/>
</body>
</html>