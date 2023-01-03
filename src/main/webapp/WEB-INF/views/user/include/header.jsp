<%@page import="com.twotrillion.comcal.user.employee.vo.EmployeeVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/resources/css/user/include/header.css" rel="stylesheet" type="text/css">
<script src="<c:url value='/resources/js/user/include/header.js' />" type="text/javascript"></script>


<div class="left-side-bar">
    <div class="status-ico">
        <span>▶</span>
        <span>▼</span>
    </div>
    
    <div class="info"></div>
            
    
    <div class="att_btn_wrap">
        <button class="att_btn_go_work att_btn" onclick="go_work();"><span>출근</span></button>
        <button class="att_btn_go_home att_btn" onclick="go_home();"><span>퇴근</span></button>
    </div>


    <ul class="gnb">
        <li>
            <div class="display_calendar">켈린더 보기</div>                
        </li>
        <li>
            <div class="display_project">프로젝트 관리</div>
        </li>
        <li>
            <div class="display_attendance">근태 관리</div>
        </li>
    </ul>
            
</div>
