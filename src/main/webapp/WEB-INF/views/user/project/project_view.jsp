<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- css -->
<link href="<c:url value='/resources/css/user/project/project_view.css' />" rel="stylesheet" type="text/css">

<!-- js -->
<script src="<c:url value="/resources/js/user/project/project_view.js"/>" type="text/javascript"></script>

<section id="project_section">

<!-- 프로젝트 뷰 -->
<div id="pjt_wrap">

	<div class="pjt_header">
		<h1>
			프로젝트 목록
		</h1>
	</div>
	
	<!-- 프로젝트 목록 시작 -->
	<div class="pjt_main_content">
		
		
	</div>
	<!-- 프로젝트 목록 끝 -->

</div>

<!-- 프로젝트 추가 버튼 -->
<div id="create_project_button">
	<i class="fa-solid fa-circle-plus"></i>
</div>

<!-- 프로젝트 추가 modal -->
<div id="create_project_modal" class="modal">
	
	<div class="modal_window">
		<div class="project_modal_header">
			<div class="header_word">
				프로젝트 등록
			</div>
		</div>

		<div class="project_modal_body">
			<form name="create_project_form">
				<input type="hidden" name="pjt_dep_no" value="">
				<input type="hidden" name="pjt_manage_emp_no" value="">

				<div class="pjt_manage_section section">
					<div class="section_word">담당</div>
					<div class="manage_wrap">
						<input type="text" name="pjt_dep_name" readonly>
						<input type="text" name="pjt_manage_emp_name" readonly>
					</div>
				</div>

				<div class="pjt_start_end_date_section section">
					<div class="section_word">기간
						<span id = "pjt_date_warning" class="warning_word">기간을 정확히 선택하여 주십시오.</span>
					</div>
					<div class="datepicker_wrap">
						<div class="datepicker">
							<input name="pjt_start_date" readonly>
						</div>
						<span>~</span>
						<div class="datepicker">
							<input name="pjt_end_date" readonly>
						</div>
					</div>
				</div>

				<div class="pjt_title_section section">
					<div class="section_word">프로젝트 이름
						<span id = "pjt_title_warning" class="warning_word">프로젝트 이름을 입력하여 주십시오.</span>
					</div>
					<div class="pjt_title_wrap">
						<input type="text" name="pjt_title">
					</div>
				</div>

				<div class="pjt_member_section section">
					<div class="section_word">구성원
						<span id = "pjt_member_warning" class="warning_word">구성원이 1명 이상 구성되어야 합니다.</span>
					</div>
					<div class="pjt_member_wrap">
						<select id="pjt_member_multiple_select" name="pjt_member" multiple>
							<option value="1">김사장</option>
							
						</select>
					</div>
					
				</div>
				
				<div class="btn_section">
					<input class="project_modal_btn" type="button" value="등록" onclick="create_project_modal_confirm();">
					<input class="project_modal_btn" type="reset" value="취소" onclick="create_project_modal_close();">
				</div>
			</form>
		</div>
	</div>
</div>

<!-- 프로젝트 상세 정보 -->
<div id="project_detail_modal" class="modal">
	
	<div class="modal_window">
		<div class="project_modal_header">
			<div class="header_word">
				프로젝트 상세 정보
			</div>
		</div>

		<div class="project_modal_body">

			<div class="pjt_manage_section section">
				<div class="section_word">담당</div>
				<div class="manage_wrap">
					<input type="text" name="pjt_dep_name" readonly>
					<input type="text" name="pjt_manage_emp_name" readonly>
				</div>
			</div>

			<div class="pjt_start_end_date_section section">
				<div class="section_word">기간</div>
				<div class="datepicker_wrap">
					<div class="datepicker">
						<input name="pjt_start_date" readonly>
					</div>
					<span>~</span>
					<div class="datepicker">
						<input name="pjt_end_date" readonly>
					</div>
				</div>
			</div>

			<div class="pjt_title_section section">
				<div class="section_word">프로젝트 이름</div>
				<div class="pjt_title_wrap">
					<input type="text" name="pjt_title" readonly>
				</div>
			</div>

			<div class="pjt_member_section section">
				<div class="section_word">구성원</div>
				<div class="pjt_member_wrap">
					<textarea name="pjt_member" readonly></textarea>
				</div>
			</div>
			
			<div class="btn_section">
				<input class="project_modal_btn" type="button" value="등록" onclick="">
				<input class="project_modal_btn" type="reset" value="닫기" onclick="">
			</div>
		</div>
	</div>
</div>
	
		
<template id = "project_item">
	<div class="pjt_content">
		<ul>
			<li class="detail_item">
			
				<div class="item_content_top">
					PROJECT
				</div>
			
				<div>
					<a href="#none"><p class="pjt_title"></p></a>
				</div>
			
				<div>
					<p class="pjt_date">
					
					</p>
				</div>
			
				<div>
					담당: <span class="pjt_manager"></span>
				</div>
			
				<div class="item_content_bot">
					
				</div>
			</li>				
		</ul>
		
	</div>
</template>

</section>

