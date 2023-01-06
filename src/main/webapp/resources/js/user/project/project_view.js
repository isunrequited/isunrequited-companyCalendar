var muliselect1;

// 프로젝트 기본 세팅
function project_ready() {
	console.log('PROJECT READY!!');

	add_project_events();
	get_project_list();
}

function set_multiselect() {
	// multiselect 기능 설정
		mobiscroll.setOptions({
		locale: mobiscroll.localeKo,                                         
		theme: 'windows',                                                        
		themeVariant: 'light'                                                
	});

		muliselect1 = mobiscroll.select('#pjt_member_multiple_select', {
		inputElement: document.getElementById('demo-multiple-select-input') 
	});
}

function add_project_events() {
	console.log('add_project_events() CALLED!!');
	
	// 프로젝트 등록 버튼 클릭
	$('#create_project_button').click(function() {
		display_create_project_modal();
	});

}

// 프로젝트 생성 modal 닫기
function create_project_modal_close() {
	$('#create_project_modal').css('display', 'none');
	muliselect1.destroy();
	$('#create_project_button').css('display', 'block');
}

// 프로젝트 생성 modal 열기 - 부서 정보와 등록자 이름 가져오기
function display_create_project_modal() {
	$('#create_project_button').css('display', 'none');
	
	let msg1 = {};
    $.ajax({
        url: ctx + "/employee/get_logged_in_emp_info",
        type: "POST",
        data: JSON.stringify(msg1), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            if (result.result == 'success'){
              
              	$('#create_project_modal input[name="pjt_dep_no"]').val(result.dep_no);
              	$('#create_project_modal input[name="pjt_manage_emp_no"]').val(result.emp_no);
              	$('#create_project_modal input[name="pjt_dep_name"]').val(result.dep_name);
              	$('#create_project_modal input[name="pjt_manage_emp_name"]').val(result.emp_name);
              	
              	$('#pjt_date_warning').css('display', 'none');
			    $('#pjt_title_warning').css('display', 'none');
			    $('#pjt_member_warning').css('display', 'none');
				$('#create_project_modal .datepicker > input').datepicker("setDate", new Date());
				
				let msg2 = {
					dep_no : result.dep_no
				};
				
				console.log(msg2);
			    $.ajax({
			        url: ctx + "/employee/get_dep_member_by_dep_no",
			        type: "POST",
			        data: JSON.stringify(msg2), 
			        contentType: 'application/json; charset=utf-8;', 
			        dataType: "json",
			        success: function(result2) {
						if (result2.result == 'success'){
							
							$('#pjt_member_multiple_select option').remove();
							
							for (let i = 0; i < result2.employeeVos.length; i++) {
								$('#pjt_member_multiple_select').append('<option value="' + result2.employeeVos[i].emp_no +'">' + result2.employeeVos[i].emp_name +'</option>')
							}
							
							
							set_multiselect();
						}
					}
				});
				
				$('#create_project_modal').css('display', 'block');
                
            }
        }
    });

    
	
}

// 프로젝트 등록 컨펌
function create_project_modal_confirm() {
	console.log('create_project_modal_confirm() CALLED!!');
	let form = document.create_project_form;

    let pjt_dep_no = form.pjt_dep_no.value;
    let pjt_manager_emp_no = form.pjt_manage_emp_no.value;
    let pjt_start_date = form.pjt_start_date.value;
    let pjt_end_date = form.pjt_end_date.value;
    let pjt_title = form.pjt_title.value;
    let pjt_member = $('#pjt_member_multiple_select').val();
	
	// 배열로 나옴 [년, 월, 일]
    pjt_start_date = datepicker_parsing(pjt_start_date);
    pjt_end_date = datepicker_parsing(pjt_end_date);
    
	if (!date_confirm(pjt_start_date, pjt_end_date)) {
		// calender에 있음 - date_confirm
        $('#create_project_modal #pjt_date_warning').css('display', 'block');
    } else if (pjt_title == '') {
        $('#create_project_modal #pjt_title_warning').css('display', 'block');
    } else if (pjt_member <= 0) {
		$('#create_project_modal #pjt_member_warning').css('display', 'block');
	} else {
		
		// ajax 통신
		let pjt_dep = {
			dep_type_no : pjt_dep_no
		};
		
		let pjt_manager_emp = {
			emp_no : pjt_manager_emp_no
		};
		
		
		let msg = {
			pjt_dep : pjt_dep,
		    pjt_manager_emp : pjt_manager_emp,
		    pjt_start_year : pjt_start_date[0],
		    pjt_start_month : pjt_start_date[1],
		    pjt_start_day : pjt_start_date[2],
		    pjt_end_year : pjt_end_date[0],
		    pjt_end_month : pjt_end_date[1],
		    pjt_end_day : pjt_end_date[2],
		    pjt_title : pjt_title,
		    pjt_member : pjt_member
		};
		$.ajax({
			url: ctx + '/project/create_project_confirm',
			type:'post',
			data: JSON.stringify(msg),
			contentType: 'application/json; cahrset=utf-8;',
			datatype: 'json',
			success: function(data){
				if(data.result == 'success') {
					form.reset();
					create_project_modal_close();
					get_project_list();
				}
			}
		
		});
	}
}

// 프로젝트 목록 가져오기
function get_project_list(){
	console.log('get_project_list() CALLED!!')
	$('#pjt_wrap .pjt_main_content .pjt_content').remove();
	var msg ={
		
	}	
	
	$.ajax({
		url: ctx + '/project/get_project_list',
		type:'post',
		data: JSON.stringify(msg),
		contentType: 'application/json; cahrset=utf-8;',
		datatype: 'json',
		success: function(data){
			if (data.result == 'success') {
				console.log('AJAX SUCCESS - ajax_informationItem()');
				let projectVos = data.projectVos;
				
				for(let i = 0; i < projectVos.length; i++ ){
					let projectVo = data.projectVos[i];
					
					let pjt_no = projectVo.pjt_no;
					let pjt_title = projectVo.pjt_title;
					let pjt_dep_no = projectVo.pjt_dep.dep_type_no;
					let pjt_dep_name = projectVo.pjt_dep.dep_type_name;
					let pjt_manager_emp_no = projectVo.pjt_manager_emp.emp_no;
					let pjt_manager_emp_name = projectVo.pjt_manager_emp.emp_name;
					let pjt_start_year = projectVo.pjt_start_year;
					let pjt_start_month = projectVo.pjt_start_month;
					let pjt_start_day = projectVo.pjt_start_day;
					let pjt_end_year = projectVo.pjt_end_year;
					let pjt_end_month = projectVo.pjt_end_month;
					let pjt_end_day = projectVo.pjt_end_day;
					
					let t = document.querySelector('#project_item');
					let clone = document.importNode(t.content, true);
					
					let clone_div = clone.querySelector(".pjt_content");
				
					$('div.pjt_main_content').append(clone);
					$(clone_div).attr('id', 'pjt_no_' + pjt_no);
					$('#pjt_no_' + pjt_no +' p.pjt_title').text(pjt_title);
					$('#pjt_no_' + pjt_no +' p.pjt_title').data('pjt_no', pjt_no);
					$('#pjt_no_' + pjt_no +' p.pjt_date').text(pjt_start_year + '년 ' + ('00' + pjt_start_month).slice(-2) + '월 ' + ('00' + pjt_start_day).slice(-2) + '일 ~ ' + pjt_end_year + '년 ' + ('00' + pjt_end_month).slice(-2) + '월 ' + ('00' + pjt_end_day).slice(-2)+ '일');
					$('#pjt_no_' + pjt_no +' span.pjt_manager').text(pjt_dep_name + ' ' + pjt_manager_emp_name);
					
					$('#pjt_no_' + pjt_no +' p.pjt_title').click(function(e) {
						let pjt_no = $(e.target).data('pjt_no');
						display_project_detail_modal(pjt_no);
					})
				}
			}
			
			
		},
		error: function(data){
			console.log('AJAX ERROR - ajax_registItem()');	
		}
	
	});
}


// 프로젝트 상세정보 가져오기
function display_project_detail_modal(pjt_no){
	console.log('get_project_detail() CALLED!!')
	
	$('#project_detail_modal').css('display', 'block');
	$('#create_project_button').css('display', 'none');
	
	var msg ={
		'pjt_no' : pjt_no
	}	
	
	$.ajax({
		url: ctx + '/project/get_project_detail',
		type:'post',
		data: JSON.stringify(msg),
		contentType: 'application/json; cahrset=utf-8;',
		datatype: 'json',
		success: function(data){
			if (data.result == 'success') {
				console.log('AJAX SUCCESS - get_project_detail_modal()');
				
				let projectVo = data.projectVo;
				let employeeVos = data.employeeVos;
				
				let pjt_no = projectVo.pjt_no;
				let pjt_title = projectVo.pjt_title;
				let pjt_dep_no = projectVo.pjt_dep.dep_type_no;
				let pjt_dep_name = projectVo.pjt_dep.dep_type_name;
				let pjt_manager_emp_no = projectVo.pjt_manager_emp.emp_no;
				let pjt_manager_emp_name = projectVo.pjt_manager_emp.emp_name;
				let pjt_start_year = projectVo.pjt_start_year;
				let pjt_start_month = projectVo.pjt_start_month;
				let pjt_start_day = projectVo.pjt_start_day;
				let pjt_end_year = projectVo.pjt_end_year;
				let pjt_end_month = projectVo.pjt_end_month;
				let pjt_end_day = projectVo.pjt_end_day;
				let pjt_member = '';
				
				for (let i = 0; i < employeeVos.length; i++) {
					pjt_member += employeeVos[i].emp_name;
					if (i != employeeVos.length - 1) {
						pjt_member += ', ';
					}
				}
				
				console.log('projectVo.pjt_member ' + pjt_member);
				
				
				$('#project_detail_modal input[name="pjt_dep_name"]').val(pjt_dep_name);
				$('#project_detail_modal input[name="pjt_manage_emp_name"]').val(pjt_manager_emp_name);
				$('#project_detail_modal input[name="pjt_start_date"]').datepicker("setDate", new Date(pjt_start_year, pjt_start_month - 1, pjt_start_day));
                $('#project_detail_modal input[name="pjt_start_date"]').datepicker("option", "disabled", true);
                $('#project_detail_modal input[name="pjt_end_date"]').datepicker("setDate", new Date(pjt_end_year, pjt_end_month - 1, pjt_end_day));
                $('#project_detail_modal input[name="pjt_end_date"]').datepicker("option", "disabled", true);					
				$('#project_detail_modal input[name="pjt_title"]').val(pjt_title);
				$('#project_detail_modal textarea[name="pjt_member"]').val(pjt_member);
			}
			
		},
		error: function(data){
			console.log('AJAX ERROR - get_project_detail_modal()');	
		}
	
	});
}

function project_detail_modal_close() {
	console.log('project_detail_modal_close() CALLED!!');
	
	$('#project_detail_modal').css('display', 'none');
	$('#create_project_button').css('display', 'block');
}
