<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link href="<c:url value='/resources/css/user/attendance/attendance_list_view.css' />" rel="stylesheet" type="text/css">

<script src="<c:url value='/resources/js/user/attendance/attendance_list_view_vo.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/js/user/attendance/attendance_list_view.js'/>" type="text/javascript"></script>


<section id="attendance_section">

<div id="atd_wrap" >	
	
	<div class="atd_header">
		<h1>
			<span></span>님의 근태 기록
		</h1>
	</div>
	
	<div class="attendance_list">
		<table>
			<thead>
				<tr>
					<td>년도</td>
					<td>월</td>
					<td>일</td>
					<td>출근 시간</td>
					<td>퇴근 시간</td>
					<td>상태</td>
					<td>비고</td>
				</tr>
			</thead>

			<tbody>

			</tbody>
		</table>
	</div>
	
	<div class="page_wrap">
		<div class="page_number">
			
		</div>
		
		<div class="page_goto">
			<input type="number" name="page_target_num" >&nbsp; &#47; <span class="page_total_num"></span>
			<div class="goto_page_btn">Go to page</div>
		</div>
	</div>
	
</div>
<template id="attendance_row">
	<tr>
		<td class="atd_year"></td>
		<td class="atd_month"></td>
		<td class="atd_day"></td>
		<td class="atd_start_time"></td>
		<td class="atd_end_time"></td>
		<td class="atd_type_name"></td>
		<td class="atd_rcd_detail"></td>
	</tr>
</template>

</section>

