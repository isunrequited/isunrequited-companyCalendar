// 월 선택 modal
function display_month_select_modal() {
    console.log('display_month_select_modal() CALLED!!');
    $('#month_select_modal').css('display', 'block');

    display_month_list(select_year);
}

// 월 선택 modal 설정
function display_month_list(list_select_year) {
    console.log('display_month_list() CALLED!!');

    // modal에 선택된 년도 변경
    $('#month_select_modal .modal_window .month_list_header .year_word').text(list_select_year);

    // 화살표에 해당 년도 데이터 삽입
    let list_prev_year = list_select_year - 1;
    let list_next_year = list_select_year + 1;

    $('#month_select_modal .modal_window .month_list_header .year_arrows i.prev_year_arrow').data('year', list_prev_year);
    $('#month_select_modal .modal_window .month_list_header .year_arrows i.next_year_arrow').data('year', list_next_year);

    // month list html 생성
    $('#month_select_modal .modal_window .month_list_body .month_word').remove();
    for (let month = 1; month <= 12; month++) {
        $('#month_select_modal .modal_window .month_list_body').append('<div class="month_word" id="' + month +'">' + month + '</div>');
        $('#' + month).data('month', month);
        $('#' + month).data('year', list_select_year);
        if (select_year == list_select_year && select_month == month) {
            $('#' + month).addClass('selected');
        }
    }

    // 모달 달 클릭 이벤트
    $('#month_select_modal .modal_window .month_list_body .month_word').click(function() {
        console.log('MONTH IN MODAL CLICK!!');

        // 년도 월 설정 변경
        set_year_month($(this).data('year'), $(this).data('month'));

        $('#month_select_modal').css('display', 'none');
        calendar_create_days();
        btn_display();
    });
}

// 일정 등록 modal
function display_create_schedule_modal(prev_page) {
    console.log('display_create_schedule_modal() CALLED!!');

    $('#create_schedule_modal').data('prev_page', prev_page);
    if (prev_page == 'calendar') {
        $('#create_schedule_modal .datepicker > input').datepicker("setDate", new Date());
    } else {
        let date = prev_page.split('_')[1].split('-');
        $('#create_schedule_modal .datepicker > input').datepicker("setDate", new Date(date[0], date[1] - 1, date[2]));
    }
    $('#scd_target_warning').css('display', 'none');
    $('#scd_type_warning').css('display', 'none');
    $('#scd_date_warning').css('display', 'none');
    $('#scd_title_warning').css('display', 'none');
    $('#create_schedule_modal').css('display', 'block');
}

// 캘린더 모달 이벤트 추가
function calendar_modal_add_events() {
    // 월 선택 모달 화살표 클릭 이벤트
    $('#month_select_modal .modal_window .month_list_header .year_arrows i').click(function() {
        display_month_list($(this).data('year'));
    });

    // 일정 리스트 일 화살표 클릭 이벤트
    $('#schedule_list_modal .date_word .day_arrow').click(function() {
        $('#schedule_list_modal').data('year', $(this).data('year'));
        $('#schedule_list_modal').data('month', $(this).data('month'));
        $('#schedule_list_modal').data('day', $(this).data('day'));
        set_schedule_list_modal();
    });

    // 일정 리스트 체크박스
    $('#schedule_list_modal .modal_schedule_type_checkbox_wrap input[type=checkbox][name=scd_auth_range_type_no]').change(function() {
        set_schedule_list_modal();
    });

    // 일정 등록 modal 일정 타입 select 변경 옵션
    $('#create_schedule_modal select[name="scd_type_no"]').change(function() {
        let scd_type_no = $(this).val();
        $('#create_schedule_modal select[name="scd_target_no"] option').remove();
        if (scd_type_no == 0 || scd_type_no == 5) {
            msg = {};
            $.ajax({
                url: ctx + "/employee/get_logged_in_emp_info",
                type: "POST",
                data: JSON.stringify(msg), 
                contentType: 'application/json; charset=utf-8;', 
                dataType: "json",
                success: function(result) {
                    if (result.result == 'success'){
                        $('#create_schedule_modal select[name="scd_target_no"]').append('<option value="' + result.emp_no + '" disabled selected style="display: none;">개인</option>')
                    } else if (result.result == 'session-fail') {
                        location.href ="/comcal";
                    }
                }
            });
        } else if (scd_type_no == 1) {
            msg = {};
            $.ajax({
                url: ctx + "/project/get_projects_title_by_emp_no",
                type: "POST",
                data: JSON.stringify(msg), 
                contentType: 'application/json; charset=utf-8;', 
                dataType: "json",
                success: function(result) {
                    if (result.result == 'success'){
                        let projectVos = result.projectVos;
                        $('#create_schedule_modal select[name="scd_target_no"]').append('<option value="-1" disabled selected style="display: none;">프로젝트</option>')

                        for (let i = 0; i < projectVos.length; i++) {

                            $('#create_schedule_modal select[name="scd_target_no"]').append('<option value="' + projectVos[i].pjt_no + '">' + projectVos[i].pjt_title + '</option>')
                        }
                    } else if (result.result == 'session-fail') {
                        location.href ="/comcal";
                    }
                }
            });

        } else if (scd_type_no == 2) {
            msg = {};
            $.ajax({
                url: ctx + "/employee/get_logged_in_emp_info",
                type: "POST",
                data: JSON.stringify(msg), 
                contentType: 'application/json; charset=utf-8;', 
                dataType: "json",
                success: function(result) {
                    if (result.result == 'success'){
                        $('#create_schedule_modal select[name="scd_target_no"]').append('<option value="' + result.dep_no + '" disabled selected style="display: none;">' + result.dep_name + '</option>')
                    } else if (result.result == 'session-fail') {
                        location.href ="/comcal";
                    }
                }
            });
        } else if (scd_type_no == 3 || scd_type_no == 4) {
            $('#create_schedule_modal select[name="scd_target_no"]').append('<option value="0" disabled selected style="display: none;">회사</option>')
        }
    });
}


// 일정 등록 컨펌
function create_schedule_confirm() {
    let form = document.create_schedule_form;

    let input_scd_target_no = form.scd_target_no.value;
    let input_scd_type_no = form.scd_type_no.value;
    let input_scd_start_date = form.scd_start_date.value;
    let input_scd_end_date = form.scd_end_date.value;
    let input_scd_title = form.scd_title.value;
    let input_scd_detail = form.scd_detail.value;

    input_scd_start_date = datepicker_parsing(input_scd_start_date);
    input_scd_end_date = datepicker_parsing(input_scd_end_date);
    if (input_scd_target_no < 0) {
        $('#create_schedule_modal #scd_target_warning').css('display', 'block');
    } else if (input_scd_type_no < 0) {
        $('#create_schedule_modal #scd_type_warning').css('display', 'block');
    } else if (!date_confirm(input_scd_start_date, input_scd_end_date)) {
        $('#create_schedule_modal #scd_date_warning').css('display', 'block');
    } else if (input_scd_title == '') {
        $('#create_schedule_modal #scd_title_warning').css('display', 'block');
    } else {
        let input_scd_type = {
            scd_type_no : input_scd_type_no
        }
        
        let msg = {
            scd_title : input_scd_title,
            scd_detail : input_scd_detail,
            scd_type : input_scd_type,
            scd_auth_target_no :input_scd_target_no,
            scd_start_year : input_scd_start_date[0],
            scd_start_month : input_scd_start_date[1],
            scd_start_day : input_scd_start_date[2],
            scd_end_year : input_scd_end_date[0],
            scd_end_month : input_scd_end_date[1],
            scd_end_day : input_scd_end_date[2],
        }

        $.ajax({
            url: ctx + "/schedule/create_schedule",
            type: "POST",
            data: JSON.stringify(msg), 
            contentType: 'application/json; charset=utf-8;', 
            dataType: "json",
            success: function(result) {
                if (result.result == 'success') {
                    form.reset();
                    ajax_get_schedule();
                    create_schedule_modal_close();
                } else if (result.result == 'session-fail') {
                    location.href ="/comcal";
                } else {
                    alert('일정 등록에 실패하였습니다.');
                }
            }, 
            error: function(result) {
                alert('일정 등록에 실패하였습니다.');
            }
        });

    }

}

// 일정 추가 모달 닫기
function create_schedule_modal_close() {
    console.log('CREATE SCHEDULE BUTTON CLICK!!');
    $('#create_schedule_modal').css('display', 'none');
    let prev_page = $('#create_schedule_modal').data('prev_page');
    if (prev_page == 'calendar') {
        btn_display();
    } else {
        let date = prev_page.split('_')[1].split('-');
        display_schedule_list_modal(date[0], date[1], date[2]);
    } 
}

// datepicker String to Int로 파싱
function datepicker_parsing(input_date) {
    let date = [];
    date.push(parseInt(input_date.substring(0, 4)));
    date.push(parseInt(input_date.substring(6, 8)));
    date.push(parseInt(input_date.substring(10, 12)));
    return date;
}

// 시작 날짜와 종료 날짜 컴펌
function date_confirm(start_date, end_date) {
    let result = true;
    if (start_date[0] > end_date[0]) {
        result = false;
    } else if (start_date[0] == end_date[0]) {
        if (start_date[1] > end_date[1]) {
            result = false;
        } else if (start_date[1] == end_date[1]) {
            if (start_date[2] > end_date[2]) {
                result = false;
            }
        }
    }

    return result;
}

// 일정 상세 정보 modal
function display_schedule_detail_modal(scd_no, prev_page) {
    console.log('display_schedule_detail_modal() CALLED!!');
    btn_display_none();

    let msg = {
        scd_no : scd_no
    }

    $.ajax({
        url: ctx + "/schedule/get_schedule_by_no",
        type: "POST",
        data: JSON.stringify(msg), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            if (result.result == "success") {
                console.log("AJAX get_schedule_by_no SUCCESS!!");
                let scheduleVo = result.scheduleVo;
                let scd_target_name = result.scd_target_name;

                $('#display_schedule_detail_modal').data('scd_no', scheduleVo.scd_no);
                $('#display_schedule_detail_modal').data('scheduleVo', scheduleVo.scheduleVo);
                $('#display_schedule_detail_modal input[name="scd_target_name"]').val(scd_target_name);
                $('#display_schedule_detail_modal input[name="scd_type_name"]').val(scheduleVo.scd_type.scd_type_name);
                $('#display_schedule_detail_modal input[name="scd_writer_name"]').val(scheduleVo.scd_writer_emp_info.emp_name);

                $('#display_schedule_detail_modal input[name="scd_start_date"]').datepicker("setDate", new Date(scheduleVo.scd_start_year, scheduleVo.scd_start_month - 1, scheduleVo.scd_start_day));
                $('#display_schedule_detail_modal input[name="scd_start_date"]').datepicker("option", "disabled", true);
                $('#display_schedule_detail_modal input[name="scd_end_date"]').datepicker("setDate", new Date(scheduleVo.scd_end_year, scheduleVo.scd_end_month - 1, scheduleVo.scd_end_day));
                $('#display_schedule_detail_modal input[name="scd_end_date"]').datepicker("option", "disabled", true );
                $('#display_schedule_detail_modal input[name="scd_title"]').val(scheduleVo.scd_title);
                $('#display_schedule_detail_modal textarea[name="scd_detail"]').val(scheduleVo.scd_detail);
                $('#display_schedule_detail_modal').data('prev_page', prev_page);
                $('#display_schedule_detail_modal').css('display', 'block');

            } else if (result.result == 'session-fail') {
                location.href ="/comcal";
            }
        },
        error: function(result) {
            console.log("AJAX get_schedule_by_no FAIL!!");
        }
    });
}

// 일정 상세 정보 모달 닫기
function display_schedule_detail_close() {
    console.log('display_schedule_detail_close() CALLED!!');
    let prev_page = $('#display_schedule_detail_modal').data('prev_page');

    $('#display_schedule_detail_modal').css('display', 'none');
    if (prev_page == 'calendar') {
        btn_display();
    } else {
        let date = prev_page.split('_')[1].split('-');
        display_schedule_list_modal(date[0], date[1], date[2]);
    } 

}

// 일정 수정 modal 열기
function display_modify_schedule_modal() {
    console.log('display_modify_schedule_modal() CALLED!!');
    $('#display_schedule_detail_modal').css('display', 'none');

    let msg = {
        scd_no : $('#display_schedule_detail_modal').data('scd_no')
    }

    $.ajax({
        url: ctx + "/schedule/schedule_auth_confirm",
        type: "POST",
        data: JSON.stringify(msg), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",   
        success: function(result) {
            if (result.result == 'success') {
                $('#modify_schedule_modal').css('display', 'block');
                btn_display_none();

                $.ajax({
                    url: ctx + "/schedule/get_schedule_by_no",
                    type: "POST",
                    data: JSON.stringify(msg), 
                    contentType: 'application/json; charset=utf-8;', 
                    dataType: "json",
                    success: function(result) {
                        if (result.result == "success") {
                            console.log("AJAX get_schedule_by_no SUCCESS!!");
                            let scheduleVo = result.scheduleVo;
                            let scd_target_name = result.scd_target_name;

                            $('#modify_schedule_modal').data('scd_no', scheduleVo.scd_no);
                            $('#modify_schedule_modal input[name="scd_target_name"]').val(scd_target_name);
                            $('#modify_schedule_modal input[name="scd_type_name"]').val(scheduleVo.scd_type.scd_type_name);
                            $('#modify_schedule_modal input[name="scd_writer_name"]').val(scheduleVo.scd_writer_emp_info.emp_name);
            
                            $('#modify_schedule_modal input[name="scd_start_date"]').datepicker("setDate", new Date(scheduleVo.scd_start_year, scheduleVo.scd_start_month - 1, scheduleVo.scd_start_day));
                            $('#modify_schedule_modal input[name="scd_end_date"]').datepicker("setDate", new Date(scheduleVo.scd_end_year, scheduleVo.scd_end_month - 1, scheduleVo.scd_end_day));
                            $('#modify_schedule_modal input[name="scd_title"]').val(scheduleVo.scd_title);
                            $('#modify_schedule_modal textarea[name="scd_detail"]').val(scheduleVo.scd_detail);
            
                            $('#modify_schedule_modal').css('display', 'block');
            
                        } else if (result.result == 'session-fail') {
							location.href ="/comcal";
						}
                    },
                    error: function(result) {
                    }
                 });
            } else {
                alert("작성자만 수정할 수 있습니다.");
            }
        },
        error: function(result) {

        }

    });
}

// 일정 수정 modal 닫기
function modify_schedule_modal_close(){
    console.log('modify_schedule_modal_close() CALLED!!');
    $('#modify_schedule_modal').css('display', 'none');
    display_schedule_detail_modal($('#display_schedule_detail_modal').data('scd_no'));
}

// 일정 수정 컨펌
function modify_schedule_confirm() {
    console.log('modify_schedule_confirm() CALLED!!');

    let form = document.modify_schedule_form;

    let input_scd_start_date = form.scd_start_date.value;
    let input_scd_end_date = form.scd_end_date.value;
    let input_scd_title = form.scd_title.value;
    let input_scd_detail = form.scd_detail.value;

    input_scd_start_date = datepicker_parsing(input_scd_start_date);
    input_scd_end_date = datepicker_parsing(input_scd_end_date);
    if (!date_confirm(input_scd_start_date, input_scd_end_date)) {
        $('#modify_schedule_modal #scd_date_warning').css('display', 'block');
    } else if (input_scd_title == '') {
        $('#modify_schedule_modal #scd_title_warning').css('display', 'block');
    } else {
        
        let msg = {
            scd_no : $('#display_schedule_detail_modal').data('scd_no'),
            scd_title : input_scd_title,
            scd_detail : input_scd_detail,
            scd_start_year : input_scd_start_date[0],
            scd_start_month : input_scd_start_date[1],
            scd_start_day : input_scd_start_date[2],
            scd_end_year : input_scd_end_date[0],
            scd_end_month : input_scd_end_date[1],
            scd_end_day : input_scd_end_date[2],
        }

        $.ajax({
            url: ctx + "/schedule/modify_schedule",
            type: "POST",
            data: JSON.stringify(msg), 
            contentType: 'application/json; charset=utf-8;', 
            dataType: "json",
            success: function(result) {
                if (result.result == 'success') {
                    form.reset();
                    ajax_get_schedule();
                    modify_schedule_modal_close();
                } else {
                    alert('일정 수정에 실패하였습니다.');
                }
            }, 
            error: function(result) {
                alert('일정 수정에 실패하였습니다.');
            }
        });
    }

}

// 일정 삭제
function delete_schedule() {
    console.log('delete_schedule() CALLED!!');

    let answer = confirm('일정을 삭제하시겠습니까?');

    if (!answer) {
        return;
    }

    let msg = {
        scd_no : $('#display_schedule_detail_modal').data('scd_no'),
    }

    $.ajax({
        url: ctx + "/schedule/delete_schedule",
        type: "POST",
        data: JSON.stringify(msg), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            if (result.result == 'success') {
                $('#modify_schedule_modal').css('display', 'none');
                btn_display();
                ajax_get_schedule();
            } else if (result.result == 'session-fail') {
                location.href ="/comcal";
            } else {
                alert('일정 삭제에 실패하였습니다.');
            }
        },
        error: function(result) {
            alert('일정 삭제에 실패하였습니다.');
        }
    })
}

// 일정 목록 modal 보이기
function display_schedule_list_modal(year, month, day) {
    console.log('display_schedule_list_modal() CALLED!!');

    btn_display_none();
    $('#schedule_list_modal').data('year', year);
    $('#schedule_list_modal').data('month', month);
    $('#schedule_list_modal').data('day', day);
    set_schedule_list_modal();
    $('#schedule_list_modal').css('display', 'block');

}

// 일정 목록 modal 설정
function set_schedule_list_modal() {
    $('#schedule_list_modal .schedule_modal_body .schedule_list .modal_schedule').remove();

    // modal 선택 date설정 (이전 다음)
    let year = $('#schedule_list_modal').data('year');
    let month = $('#schedule_list_modal').data('month');
    let day = $('#schedule_list_modal').data('day');

    let prev_year = year;
    let prev_month = month;
    let prev_day = day - 1;
    if (day == 1) {
        prev_month = month - 1;
        prev_day = new Date(year, month - 1, 0).getDate();
        if (month == 1) {
            prev_year = year - 1;
            prev_month = 12;
        }
    }

    let next_year = year;
    let next_month = month;
    let next_day = day + 1;
    if (day == new Date(year, month, 0).getDate()) {
        next_month = month + 1;
        next_day = 1;
        if (month == 12) {
            next_year = year + 1;
            next_month = 1;
        }
    }

    $('#schedule_list_modal .date_word .word').text(year + '년 ' + ('00' + month).slice(-2) + '월 ' + ('00' + day).slice(-2) + '일');
    $('#schedule_list_modal .date_word .prev_day_arrow').data('year', prev_year);
    $('#schedule_list_modal .date_word .prev_day_arrow').data('month', prev_month);
    $('#schedule_list_modal .date_word .prev_day_arrow').data('day', prev_day);
    $('#schedule_list_modal .date_word .next_day_arrow').data('year', next_year);
    $('#schedule_list_modal .date_word .next_day_arrow').data('month', next_month);
    $('#schedule_list_modal .date_word .next_day_arrow').data('day', next_day);


    // date로 일정 목록 가져오기
    let private_schedule = false;
    let project_schedule = false;
    let department_schedule = false;
    let company_schedule = false;

    $('#schedule_list_modal .modal_schedule_type_checkbox_wrap input[name="scd_auth_range_type_no"]').each(function() {
        if (this.checked) {
            let checked_val = $(this).val();
            // scd_auth_range_type_no_list.push(checked_val);

            switch(checked_val) {
                case '0':
                    private_schedule = true;
                    return;
                case '1':
                    project_schedule = true;
                    return;
                case '2':
                    department_schedule = true;
                    return;
                case '3':
                    company_schedule = true;
                    return;
            }
        }
    });

    let msg = {
        date: year + '-' + month + '-' + day,
        private_schedule: private_schedule,
        project_schedule: project_schedule,
        department_schedule: department_schedule,
        company_schedule: company_schedule
    }

    $.ajax({
        url: ctx + "/schedule/get_schedule_list_by_date",
        type: "POST",
        data: JSON.stringify(msg), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            if (result.result == 'success') {
                let scheduleVos = result.scheduleVos;
                
                for (let i = 0; i < scheduleVos.length; i++) {
                    let class_auth_target_word;
                    switch(scheduleVos[i].scd_auth_range_type.scd_auth_range_type_no) {
                        case 0: 
                            class_auth_target_word = 'private';
                            break;
                        case 1: 
                            class_auth_target_word = 'project';
                            break;
                        case 2: 
                            class_auth_target_word = 'department';
                            break;
                        case 3: 
                            class_auth_target_word = 'company';
                            break;
                    }
                    $('#schedule_list_modal .schedule_modal_body .schedule_list').append('<div id="in_modal_scd_no_' + scheduleVos[i].scd_no + '" class="modal_schedule">' + scheduleVos[i].scd_title +'</div>')
                    $('#in_modal_scd_no_' + scheduleVos[i].scd_no).addClass(class_auth_target_word);
                    $('#in_modal_scd_no_' + scheduleVos[i].scd_no).data('scd_no', scheduleVos[i].scd_no);
                    $('#in_modal_scd_no_' + scheduleVos[i].scd_no).click(function(e) {
                        $('#schedule_list_modal').css('display', 'none');
                        let prev_page = 'modal_' + $('#schedule_list_modal').data('year') + '-' + $('#schedule_list_modal').data('month') + '-' +  $('#schedule_list_modal').data('day');
                        schedule_click_event(e.target, prev_page);
                    });
                } 

            } else if (result.result == 'session-fail') {
                location.href ="/comcal";
            }
        },
        error: function(result) {

        }
    });
}

// 일정 목록 modal 닫기
function schedule_list_modal_close() {
    console.log('schedule_list_modal_close() CALLED!!');
    $('#schedule_list_modal').css('display', 'none');
    btn_display();
}

// 일정 목록 modal에서 일정 추가 버튼 클릭시
function create_schedule_to_schedule_list_modal() {
    $('#schedule_list_modal').css('display', 'none');
    let prev_page = 'modal_' + $('#schedule_list_modal').data('year') + '-' + $('#schedule_list_modal').data('month') + '-' +  $('#schedule_list_modal').data('day');
    display_create_schedule_modal(prev_page);
}