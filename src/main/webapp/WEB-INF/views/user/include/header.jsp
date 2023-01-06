<%@page import="com.twotrillion.comcal.user.employee.vo.EmployeeVo"%>
<%@page import="com.twotrillion.comcal.user.attendance.vo.AttendanceVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/resources/css/user/include/header.css" rel="stylesheet" type="text/css">
<script src="<c:url value='/resources/js/user/include/header.js' />" type="text/javascript"></script>


<div class="left-side-bar">
    <div class="status-ico">
    </div>
    
    <div class="left-side-bar-body">
        <div>
            <%
                EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("logged_in_employee_vo");
            %>
            
            <div class="word">
                <div class="logo">TwoTrillion</div>
                <div class="emp_info">
                    <span class="dep_name"><%=employeeVo.getEmp_dep().getDep_type_name()%></span>
                    <span class="emp_name"><%=employeeVo.getEmp_name()%></span>
                </div>
                
            </div>       
    
        </div>
                
        
        <!-- 버튼 -->
        <!-- attendance Button -->
        <div class="header_btn_wrap">
            
        </div>
        
    
    
        <ul class="gnb">
            <li>
                <div class="gnb_menu">
                    <i class="fa-solid fa-bars"></i>
                    <span>메뉴</span>
                </div>
            </li>
            <li>
                <div class="display_calendar gnb_selected">
                    <i class="fa-regular fa-calendar"></i>
                    <span>일정 관리</span>
                </div>                
            </li>
            <li>
                <div class="display_project">
                    <i class="fa-solid fa-copy"></i>
                    <span>프로젝트 관리</span>
                    </div>
            </li>
            <li>
                <div class="display_attendance">
                    <i class="fa-regular fa-address-book"></i>
                    <span>근태 기록</span>
                </div>
            </li>
        </ul>
    
        <ul class="gnb_2">
            <li>
                <div class ="att_btn">
                    <i class="fa-solid fa-building"></i>
                    <span>출근</span>
                </div>
            </li>
            <li>
                <div class ="home_btn">
                    <i class="fa-solid fa-house-user"></i>
                    <span>퇴근</span>
                </div>
            </li>
            <li>
                <div class="logout_btn">
                    <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    <span>로그아웃</span>
                </div>
            </li>
        </ul>
        
    </div>
    
            
</div>
