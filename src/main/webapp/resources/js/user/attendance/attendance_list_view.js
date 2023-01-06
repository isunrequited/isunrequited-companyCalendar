function attendance_ready() {
	console.log('ATTENDANCE READY!!');

	set_attendance_header();
	ajax_getAttendanceList();
	add_events_for_paging();
}

function set_attendance_header() {
	console.log('set_attendance_emp_info() CALLED!');

	var msg = {};
	$.ajax({
		url : ctx + '/employee/get_logged_in_emp_info',
		type : 'POST',
		data : JSON.stringify(msg),
		contentType : 'application/json; charset=utf-8;',
		dataType : 'json',
		success : function(result) {
			if(result.result == 'success') {
				$('#atd_wrap .atd_header h1 span').text(result.emp_name);
			}
		}
	});
}

function add_events_for_paging() {
	console.log('add_events_for_paging() CALLED!');

	/* 페이지 번호 클릭 시 */
	$(document).on('click', '#atd_wrap .page_wrap .page_number div',function() {
		console.log('page_number a CLICKED!!');

		let pageNum = $(this).data('pagenum');
		setPageNum(pageNum);

		ajax_getAttendanceList();
	});

	/* 페이지 번호 입력 시 */
	$(document).on('click','#atd_wrap .page_wrap .page_goto div.goto_page_btn',function() {
		console.log('goto_page_btn CLICKED!!');

		let page_target = $('#atd_wrap .page_wrap .page_goto input[name="page_target_num"]').val();
		let total_page = $('#atd_wrap .page_wrap .page_goto span.page_total_num').html();

		if (page_target == '' || page_target == ' '
				|| page_target == null) {
			$('#atd_wrap .page_wrap .page_goto input[name="page_target_num"]').focus();
			return;

		} else if (parseInt(page_target) < 1) {
			$('#atd_wrap .page_wrap .page_goto input[name="page_target_num"]').focus();
			return;

		} else if (parseInt(page_target) > parseInt(total_page.replaceAll(',', ''))) {
			$('#atd_wrap .page_wrap .page_goto input[name="page_target_num"]').focus();
			return;

		}

		setPageNum(page_target);
		ajax_getAttendanceList();

		$('#atd_wrap .page_wrap .page_goto input[name="page_target_num"]').val('');
	});

}

function ajax_getAttendanceList() {
	console.log('ajax_getAttendanceList() CALLED!!');

	var msg = {
		pageNum: getPageNum(),
		amount: getAmount()
	};
	$.ajax({
		url : ctx + '/attendance/get_attendance_list',
		type : 'POST',
		data : JSON.stringify(msg),
		contentType : 'application/json; charset=utf-8;',
		dataType : 'json',
		success : function(result) {

			if (result.result == 'success') {
				$('#atd_wrap .attendance_list table tbody tr').remove();

				let attendanceVos = result.attendanceVos;
	
				for (let i = 0; i < attendanceVos.length; i++) {
					let attendanceInfos = attendanceVos[i];
	
					let atd_rcd_no = attendanceInfos['atd_rcd_no'];
					let atd_year = attendanceInfos['atd_year'];
					let atd_month = attendanceInfos['atd_month'];
					let atd_day = attendanceInfos['atd_day'];
					let atd_start_time = attendanceInfos['atd_start_time'];
					let atd_end_time = attendanceInfos['atd_end_time'];
					let atd_type_name = attendanceInfos['atd_type']['atd_type_name'];
					let atd_rcd_detail = attendanceInfos['atd_rcd_detail'];
	
					let template = document.querySelector('#attendance_row');
					let clone = document.importNode(template.content,true);
					let clone_tr = clone.querySelector("tr");
	
					$(clone_tr).attr('id', 'atd_rcd_no_' + atd_rcd_no);
	
					$('#atd_wrap .attendance_list table tbody').append(clone);
	
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_year').text(atd_year);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_month').text(atd_month);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_day').text(atd_day);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_start_time').text(atd_start_time);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_end_time').text(atd_end_time);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_type_name').text(atd_type_name);
					$('#atd_rcd_no_' + atd_rcd_no + ' .atd_rcd_detail').text(atd_rcd_detail);
	
				}
				
				/* PAGE UI */
				$('#atd_wrap .page_wrap .page_number').children().remove();
				
				let pageMakerVo = result.pageMakerVo;
				
				if (pageMakerVo.prev) {
					$('#atd_wrap .page_wrap .page_number').append('<div href="#none" data-pagenum="' + (pageMakerVo.startPage - 1) + '">PREV</div>');
				}
				
				if (getPageNum() > 10) {
					$('#atd_wrap .page_wrap .page_number').append('<div href="#none" data-pagenum="1">1</div><span> ..... </span>');
				}
				
				for (var p = parseInt(pageMakerVo.startPage); p <= parseInt(pageMakerVo.endPage); p++) {
					$('#atd_wrap .page_wrap .page_number').append('<div href="#none" data-pagenum="' + p + '">' + p + '</div>');
				}
				
				$('#atd_wrap .page_wrap .page_number div[data-pagenum=' + getPageNum() + ']').addClass('selectedPageNum');
				
				if (pageMakerVo.next) {
					$('#section_wrap .page_wrap .page_number').append('<div href="#none" data-pagenum="' + (pageMakerVo.endPage + 1) + '">NEXT</div>');
				}
				
				$('#atd_wrap > div.page_wrap > div.page_goto > span.page_total_num').html(addComma(pageMakerVo.totalPage.toString()));	
			}

		},
		error : function(result) {
		}
	});

}

function addComma(value) {
	if (value == null) {
		return value;
	}

	return value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}