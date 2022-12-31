<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!-- css -->
<link href="<c:url value="/resources/css/user/calendar/calendar.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/user/calendar/calendar_modal.css"/>" rel="stylesheet" type="text/css">

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
			<input type="checkbox" id="calendar_schedule_type_private" name="scd_auth_range_type_no" value="0" checked><label for="calendar_schedule_type_private"><span>개인</span></label>
			<input type="checkbox" id="calendar_schedule_type_project" name="scd_auth_range_type_no" value="1"><label for="calendar_schedule_type_project"><span>프로젝트</span></label>
			<input type="checkbox" id="calendar_schedule_type_department" name="scd_auth_range_type_no" value="2"><label for="calendar_schedule_type_department"><span>부서</span></label>
			<input type="checkbox" id="calendar_schedule_type_company" name="scd_auth_range_type_no" value="3"><label for="calendar_schedule_type_company"><span>회사</span></label>
		</div>
	</div>

	<!-- 캘린더 바디, 날짜 부분 -->
	<div class="calendar_body"><span class="day_name color_red">Sun</span><span class="day_name">Mon</span><span class="day_name">Tue</span><span class="day_name">Wed</span><span class="day_name">Thu</span><span class="day_name">Fri</span><span class="day_name color_blue">Sat</span>
		
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
<div id="month_select_modal" class="modal">
	<div class="modal_window">
		<div class="month_list_header">
			<div class="year_word">2018</div>
			
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

<!-- 일정 등록 modal -->
<div id="create_schedule_modal" class="modal schedule_modal">
	<div class="modal_window">
		<div class="schedule_modal_header">
			<div class="header_word">
				일정 등록
			</div>
		</div>

		<div class="schedule_modal_body">
			<form name="create_schedule_form">
				<div class="scd_type_section section">
					<div class="section_word">사항 
						<span id = "scd_target_warning" class="warning_word">담당을 정확히 선택하여 주십시오.</span>
						<span id = "scd_type_warning" class="warning_word">일정 분류를 정확히 선택하여 주십시오.</span>
					</div>
					<div class="select_wrap">
						<select name="scd_target_no">
							<option value="-1" disabled selected style="display: none;">담당</option>
						</select>
						<select name="scd_type_no">
							<option value="-1" disabled selected style="display: none;">일정 분류</option>
							<option value="0">개인</option>
							<option value="1">프로젝트</option>
							<option value="2">부서</option>
							<option value="3">회사</option>
							<option value="4">휴가</option>
							<option value="5">기타</option>
						</select>
					</div>
				</div>

				<div class="scd_start_end_date_section section">
					<div class="section_word">기간
						<span id = "scd_date_warning" class="warning_word">기간을 정확히 선택하여 주십시오.</span>
					</div>
					<div class="datepicker_wrap">
						<div class="datepicker">
							<input name="scd_start_date" readonly>
						</div>
						<span>~</span>
						<div class="datepicker">
							<input name="scd_end_date" readonly>
						</div>
					</div>
				</div>

				<div class="std_title_section section">
					<div class="section_word">내용
						<span id = "scd_title_warning" class="warning_word">일정 내용을 입력하여 주십시오.</span>
					</div>
					<div class="scd_title_wrap">
						<input type="text" name="scd_title">
					</div>
				</div>
				
				<div class="std_detail_section section">
					<div class="section_word">메모</div>
					<div class="scd_detail_wrap">
						<textarea name="scd_detail"></textarea>
					</div>
				</div>

				<div class="btn_section">
					<input class="schedule_modal_btn" type="button" value="등록" onclick="create_schedule_confirm();">
					<input class="schedule_modal_btn" type="reset" value="취소" onclick="create_schedule_modal_close();">
				</div>
			</form>
		</div>
	</div>
</div>

<!-- 일정 상세 정보 modal -->
<div id="display_schedule_detail_modal" class="modal schedule_modal">
	<div class="modal_window">
		<div class="schedule_modal_header">
			<div class="header_word">
				일정 상세 정보
			</div>
		</div>

		<div class="schedule_modal_body">
			<div class="scd_type_section section">
				<div class="section_word">사항</div>
				<div class="scd_type_wrap">
					<input type="text" name="scd_target_name" readonly>
					<input type="text" name="scd_type_name" readonly>
				</div>
			</div>

			<div class="scd_writer_section section">
				<div class="section_word">작성자</div>
				<div class="scd_writer_wrap">
					<input type="text" name="scd_writer_name" readonly>
				</div>
			</div>

			<div class="scd_start_end_date_section section">
				<div class="section_word">기간</div>
				<div class="datepicker_wrap">
					<div class="datepicker">
						<input name="scd_start_date" readonly>
					</div>
					<span>~</span>
					<div class="datepicker">
						<input name="scd_end_date" readonly>
					</div>
				</div>
			</div>

			<div class="std_title_section section">
				<div class="section_word">내용</div>
				<div class="scd_title_wrap">
					<input type="text" name="scd_title" readonly>
				</div>
			</div>
			
			<div class="std_detail_section section">
				<div class="section_word">메모</div>
				<div class="scd_detail_wrap">
					<textarea name="scd_detail" readonly></textarea>
				</div>
			</div>

			<div class="btn_section">
				<input class="schedule_modal_btn" type="button" value="수정" onclick="display_modify_schedule_modal();">
				<input class="schedule_modal_btn" type="reset" value="닫기" onclick="display_schedule_detail_close();">
			</div>
		</div>
	</div>
</div>

<!-- 일정 수정 modal -->
<div id="modify_schedule_modal" class="modal schedule_modal">
	<div class="modal_window">
		<div class="schedule_modal_header">
			<div class="header_word">
				일정 수정
			</div>
		</div>

		<div class="schedule_modal_body">
			<form  name="modify_schedule_form">
				<div class="scd_type_section section">
					<div class="section_word">사항</div>
					<div class="scd_type_wrap">
						<input type="text" name="scd_target_name" readonly>
						<input type="text" name="scd_type_name" readonly>
					</div>
				</div>

				<div class="scd_writer_section section">
					<div class="section_word">작성자</div>
					<div class="scd_writer_wrap">
						<input type="text" name="scd_writer_name" readonly>
					</div>
				</div>

				<div class="scd_start_end_date_section section">
					<div class="section_word">기간
						<span id = "scd_date_warning" class="warning_word">기간을 정확히 선택하여 주십시오.</span>
					</div>
					<div class="datepicker_wrap">
						<div class="datepicker">
							<input name="scd_start_date" readonly>
						</div>
						<span>~</span>
						<div class="datepicker">
							<input name="scd_end_date" readonly>
						</div>
					</div>
				</div>

				<div class="std_title_section section">
					<div class="section_word">내용
						<span id = "scd_title_warning" class="warning_word">일정 내용을 입력하여 주십시오.</span>
					</div>
					<div class="scd_title_wrap">
						<input type="text" name="scd_title">
					</div>
				</div>
				
				<div class="std_detail_section section">
					<div class="section_word">메모</div>
					<div class="scd_detail_wrap">
						<textarea name="scd_detail"></textarea>
					</div>
				</div>

				<div class="btn_section">
					<input class="schedule_modal_btn" type="button" value="수정" onclick="modify_schedule_confirm();">
					<input class="schedule_modal_btn" type="button" value="삭제" onclick="delete_schedule();">
					<input class="schedule_modal_btn" type="reset" value="취소" onclick="modify_schedule_modal_close();">
				</div>
			</form>
		</div>
	</div>
</div>

<!-- 일정 목록 modal -->
<div id="schedule_list_modal" class="modal schedule_modal">
	<div class="modal_window">
		<div class="schedule_modal_header">
			<div class="header_word">일정 목록</div>
			<div class="date_word">
					<span class="day_arrow prev_day_arrow">
						<i class="fa-solid fa-caret-left"></i>
					</span>
					<span class="word">2022년 12월 22일</span>
					<span class="day_arrow next_day_arrow">
						<i class="fa-solid fa-caret-right"></i>
					</span>
			</div>
		</div>

		<div class="schedule_modal_body">
			<!-- 캘린더 일정 타입 체크박스 -->
			<div class="modal_schedule_type_checkbox_wrap">
				<input type="checkbox" id="modal_schedule_type_private" name="scd_auth_range_type_no" value="0" checked><label for="modal_schedule_type_private"><span>개인</span></label>
				<input type="checkbox" id="modal_schedule_type_project" name="scd_auth_range_type_no" value="1"><label for="modal_schedule_type_project"><span>프로젝트</span></label>
				<input type="checkbox" id="modal_schedule_type_department" name="scd_auth_range_type_no" value="2"><label for="modal_schedule_type_department"><span>부서</span></label>
				<input type="checkbox" id="modal_schedule_type_company" name="scd_auth_range_type_no" value="3"><label for="modal_schedule_type_company"><span>회사</span></label>
			</div>
			<div class="schedule_list">
				

			</div>
			<div class="btn_section">
				<input class="schedule_modal_btn" type="button" value="추가" onclick="create_schedule_to_schedule_list_modal();">
				<input class="schedule_modal_btn" type="reset" value="닫기" onclick="schedule_list_modal_close();">
			</div>
		</div>
	</div>
</div>

<!-- 
<section class="schedule private">Projects</section>
<section class="schedule project">Design Sprint</section>
<section class="schedule department">Product Checkup 2</section>
<section class="schedule company">Product Checkup 2</section> 
-->
