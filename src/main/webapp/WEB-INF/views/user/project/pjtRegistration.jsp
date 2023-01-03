<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section>
		<div id="section_wrap">
			
			<div class="word">
				PROJECT Registration
			</div>
	
			<div class="now_datetime">
				Today : <span class="today_txt">&nbsp;</span>
			</div>
			<div>
				<table>
					<tr>
						<td>프로젝트 번호</td>
						<td><input type="text" name="pjt_no"></td>
						<td>프로젝트 이름</td>
						<td><input type="text" name="pjt_title"></td>
					</tr>
					<tr>	
						<td>프로젝트 부서 번호</td>
						<td><input type="text" name="pjt_dep_no"></td>
						<td>프로젝트 관리자 사원 번호</td>
						<td><input type="text" name="pjt_manager_emp_no"></td>
					</tr>
				</table>
			</div>
			<div class="diary_write">
				
				<form action="<c:url value="/user/diary/diary_write_confirm" />" 
					  name="diary_write_form" 
					  method="post" 
					  enctype="multipart/form-data">
					
					<input type="hidden" name="d_write_full_year">
					<input type="hidden" name="d_write_month">
					<input type="hidden" name="d_write_date">
					<input type="hidden" name="d_write_day">
					<input type="hidden" name="d_write_hours">
					<input type="hidden" name="d_write_minutes">
					<input type="hidden" name="d_write_seconds">
					
					<input type="text" name="d_txt" placeholder="오늘 가장 인상 깊었던 사건은?">
					<br>
					<input type="file" name="d_img_file">
					<br>
					<input type="button" value="WRITE" onclick="diaryWriteForm();">
					<input type="reset" value="RESET">
					
				</form>
				
			</div>
			
		</div>
	</section>
</body>
</html>