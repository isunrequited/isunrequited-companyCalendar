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


    <ul>
        <li>
            <a href="#">켈린더 보기</a>                
        </li>
        <li>
            <a href="#">스케쥴 보기</a>
        </li>
        <li>
            <a href="#">스케쥴 관리</a>
        </li>
            <li>
                <a href="#">근태 관리</a>
        </li>
    </ul>
            
</div>
