<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!-- css -->
<link href="<c:url value="/resources/css/user/calendar/calendar.css"/>" rel="stylesheet" type="text/css">

<!-- js -->
<script src="<c:url value="/resources/js/user/calendar/calendar_vo.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/user/calendar/calendar.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/user/calendar/calendar_modal.js"/>" type="text/javascript"></script>


<!-- 캘린더 -->
<div id="calendar_wrap">
	<!-- 캘린더 헤드 -->
	<div class="calendar_header">
		<!-- 캘린더 년, 월 -->
		<h1 class="calendar_year_month">
			<span class="calendar_year">2018</span>.
			<span class="calendar_month">11</span>.
		</h1>`
		
		<!-- 캘린더 월 이동 화살표 -->
		<div class="calendar_month_arrow">
			<i class="calendar_month_next fa-solid fa-angle-up"></i>
			<i class="calendar_month_prev fa-solid fa-angle-down"></i>
		</div>
		
		<!-- 캘린더 일정 타입 체크박스 -->
		<div class="calendar_schedule_type_checkbox_wrap">
			<input type="checkbox" id="calendar_schedule_type_checkbox01" name="scd_auth_range_type_no" value="0"><label for="calendar_schedule_type_checkbox01"><span>개인</span></label>
			<input type="checkbox" id="calendar_schedule_type_checkbox02" name="scd_auth_range_type_no" value="1"><label for="calendar_schedule_type_checkbox02"><span>프로젝트</span></label>
			<input type="checkbox" id="calendar_schedule_type_checkbox03" name="scd_auth_range_type_no" value="2"><label for="calendar_schedule_type_checkbox03"><span>부서</span></label>
			<input type="checkbox" id="calendar_schedule_type_checkbox04" name="scd_auth_range_type_no" value="3"><label for="calendar_schedule_type_checkbox04"><span>회사</span></label>
		</div>
	</div>

	<!-- 캘린더 바디, 날짜 부분 -->
	<div class="calendar_body"><span class="day_name">Mon</span><span class="day_name">Tue</span><span class="day_name">Wed</span><span class="day_name">Thu</span><span class="day_name">Fri</span><span class="day_name color_blue">Sat</span><span class="day_name color_red">Sun</span>
		
	</div>
</div>

<!-- 오늘 바로가기 버튼 -->
<div id="go_today_button" class="calendar_button">
	<i class="fa-solid fa-house"></i>	
</div>

<!-- 일정 추가 버튼 -->
<div id="create_schedule_button" class="calendar_button">
	<i class="fa-solid fa-circle-plus"></i>
</div>


<!-- modal -->
<!-- 년, 월 선택 modal -->
<div id="month_select_modal" class="modal_wrap">
	<div class="month_select_modal_body">
		<div class="month_list_header">
			<div class="modal_year_word">
				2018
			</div>
			
			<div class="year_arrows">
				<i class="prev_year_arrow fa-solid fa-chevron-down"></i>
				<i class="next_year_arrow fa-solid fa-chevron-up"></i>
			</div>
		</div>

		<div class="month_list_body">
			<div class="month_word selected" id="1">1</div>
		</div>
	</div>
</div>

<div id="create_schedule_modal" class="modal_wrap">
	<div class="create_schedule_modal_body">
		<div class="create_schedule_header">
			<div class="create_schedule_word">
				일정 등록
			</div>
		</div>

		<div class="create_schedule_form">
			<div>
				내용
				<input type="text" name="scd_title">
			</div>
			<div>
				상세사항
				<input type="text" name="scd_detail">
			</div>
			<div>
				기간
			</div>
			<div>분류</div>
		</div>
	</div>
</div>

<!--
<div class="calendar_day disabled_day today color_red color_blue"><span class="holiday_name">설날</span>30</div>
<section class="task task--warning">Projects</section>
<section class="task task--danger">Design Sprint</section>
<section class="task task--primary">Product Checkup 1<div class="task__detail">
		<h2>Product Checkup 1</h2>
		<p>15-17th November</p>
	</div>
</section>
<section class="task task--info">Product Checkup 2</section>
-->