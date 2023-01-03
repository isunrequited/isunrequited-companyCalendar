// 프로젝트 기본 세팅
function project_ready() {
	console.log('PROJECT READY!!');

	add_project_events();
	set_multiselect();
	display_project_list();

	// closeDetail()
	// ajax_informationItem()
	// ajax_registlItem()
}

function set_multiselect() {
	// multiselect 기능 설정
	mobiscroll.setOptions({
		locale: mobiscroll.localeKo,                                         
		theme: 'windows',                                                        
		themeVariant: 'light'                                                
	});

	mobiscroll.select('#pjt_member_multiple_select', {
		inputElement: document.getElementById('demo-multiple-select-input') 
	});
}

function add_project_events() {
	console.log('add_project_events() CALLED!!');
	
	$('.pjt_main_content').click(function(){
		console.log('txt CLICK EVENT HANDLER!!');
		console.log($(this));
		
		showDetail();
	});	

	// 프로젝트 등록 버튼 클릭
	$('#create_project_button').click(function() {
		display_create_project_modal();
	});

	$('#pjt_member_multiple_select').change(function() {
		console.log($('#pjt_member_multiple_select').val());
	});
}

// 프로젝트 생성 modal 닫기
function create_project_modal_close() {
	$('#create_project_modal').css('display', 'none');
	$('#create_project_button').css('display', 'block');
}

// 프로젝트 생성 modal 열기 - 부서 정보와 등록자 이름 가져오기
function display_create_project_modal() {
	$('#create_project_button').css('display', 'none');

    $('#pjt_date_warning').css('display', 'none');
    $('#pjt_title_warning').css('display', 'none');
    $('#pjt_member_warning').css('display', 'none');
	$('#create_project_modal .datepicker > input').datepicker("setDate", new Date());
	$('#create_project_modal').css('display', 'block');
}

// 프로젝트 등록 컨펌
function create_project_modal_confirm() {
	console.log('create_project_modal_confirm() CALLED!!');
	let form = document.create_project_form;

    let pjt_dep_no = form.pjt_dep_no.value;
    let pjt_manage_emp_no = form.pjt_manage_emp_no.value;
    let pjt_start_date = form.pjt_start_date.value;
    let pjt_end_date = form.pjt_end_date.value;
    let pjt_title = form.pjt_title.value;
    let pjt_member = form.pjt_member.value;

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
	}
}

// 프로젝트 목록 가져오기
function ajax_informationItem(){
	console.log('ajax_informationItem1() CALLED!!')
	var msg ={
		
	}	
	$.ajax({
		url: ctx + '/project/information_confirm',
		type:'post',
		data: JSON.stringify(msg),
		contentType: 'application/json; cahrset=utf-8;',
		datatype: 'json',
		success: function(data){
			console.log('AJAX SUCCESS - ajax_informationItem()');
			$('#pjt_wrap .pjt_main_content ul').remove();
			let projectVos = data.projectVos;
			console.log(projectVos);
			for(let i = 0; i < projectVos.length; i++ ){
			let projectVo = data.projectVos[i];
			console.log(projectVo);
			
			let pjt_no = projectVo.pjt_no;
			let pjt_title = projectVo.pjt_title;
			let pjt_dep_no = projectVo.pjt_dep_no;
			let pjt_manager_emp = projectVo.pjt_manager_emp;
			let pjt_start_year = projectVo.pjt_start_year;
			let pjt_start_month = projectVo.pjt_start_month;
			let pjt_end_year = projectVo.pjt_end_year;
			let pjt_end_month = projectVo.pjt_end_month;
			let t = document.querySelector('#pjt_detail_item');
			let clone = document.importNode(t.content, true);
			
			let clone_ul = clone.querySelector("ul");
		
			$('div.pjt_main_content').append(clone);
			$(clone_ul).attr('id', pjt_no);
			$('#' + pjt_no +' p.item_content').text(pjt_title);
			$('#' + pjt_no +' li.detail_item div p.item_content_date').text(String(pjt_start_year)+'.' + pjt_start_month + '~' + String(pjt_end_year)+ '.' + pjt_end_month );
			$('#' + pjt_no +' span.item_content_emp').text(pjt_manager_emp);
			}
		},
		error: function(data){
			console.log('AJAX ERROR - ajax_registItem()');	
		}
	
	});
}


// /* 프로젝트 등록 시작 */

// function ajax_registItem(msg){
// 	console.log('ajax_registItem() CALLED!!')
	
// 	$.ajax({
// 		url: ctx + '/project/project_confirm',
// 		type:'post',
// 		data: JSON.stringify(msg),
// 		contentType: 'application/json; cahrset=utf-8;',
// 		datatype: 'json',
// 		success: function(data){
// 			console.log('AJAX SUCCESS - ajax_registItem()');
			
// 			alert('등록완료!!');
			
// 			closeDetail();
		
// 		},
// 		error: function(data){
// 			console.log('AJAX ERROR - ajax_registItem()');	
// 			alert('다시 등록하세요!!');
// 		}
		
// 	});
// }

// /* 프로젝트 등록 끝 */
