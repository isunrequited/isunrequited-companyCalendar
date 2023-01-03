// 캘린더 기본 셋팅
function calendar_ready() {
    console.log('CALENDAR READY()!!');
    // 오늘 날짜로 셋팅
    set_today();

    // 이벤트 추가
    calendar_add_events();  
    calendar_modal_add_events();
}

// 캘린더 이벤트
function calendar_add_events() {
    // 이전, 다음 달 화살표 클릭 이벤트
    // 휴일 표시
    $('#calendar_wrap .calendar_header .calendar_month_arrow i').click(function() {
        console.log('CALENDAR NEXT OR PREV MONTH BUTTON CLICK!!');

        // 년도, 월 설정 변경
        set_year_month($(this).data('year'), $(this).data('month'));

        // 날짜 html 생성
        calendar_create_days();
    });

    // today button 클릭시
    $('#go_today_button').click(function() {
        console.log('GO TODAY BUTTON CLICK!!');

        // 오늘 날짜로 설정
        set_today();
    });

    // create schedule 클릭시
    $('#create_schedule_button').click(function() {
        console.log('GO TODAY BUTTON CLICK!!');
        btn_display_none();
        // create schedule modal 실행
        display_create_schedule_modal('calendar');
    });

    // 년월 선택 모달 이벤트
    $('#calendar_wrap .calendar_header h1.calendar_year_month').click(function() {
        console.log('SELECT MONTH BUTTON CLICK!!');
        btn_display_none();
        display_month_select_modal();
    });

    // 일정 타입 체크박스 체크
    $('.calendar_schedule_type_checkbox_wrap input[type=checkbox][name=scd_auth_range_type_no]').change(function() {
        ajax_get_schedule();
    });
    
}

// 오늘 날짜 설정
function set_today() {
    console.log('set_today() CALLED!!');

    let today = new Date();
    set_year_month(today.getFullYear(), today.getMonth() + 1);

    calendar_create_days();
}

// 오늘 강조
// 캘린더 날짜 html 만들기
function calendar_create_days() {
    console.log("calendar_days_setting() CALLED!!");

    // 년, 월 변경
    $('#calendar_wrap .calendar_header h1.calendar_year_month span.calendar_year').text(select_year);
    $('#calendar_wrap .calendar_header h1.calendar_year_month span.calendar_month').text(('00' + select_month).slice(-2));

    // 이전, 다음 달 화살표 데이터 추가
    $('#calendar_wrap .calendar_header .calendar_month_arrow i.calendar_month_next').data('year', next_year);
    $('#calendar_wrap .calendar_header .calendar_month_arrow i.calendar_month_next').data('month', next_month);
    $('#calendar_wrap .calendar_header .calendar_month_arrow i.calendar_month_prev').data('year', prev_year);
    $('#calendar_wrap .calendar_header .calendar_month_arrow i.calendar_month_prev').data('month', prev_month);

    // 캘린더에 보여줄 날짜들의 정보 가져오기
    let days_list = calender_get_days_list();

    // html 만들기
    $('#calendar_wrap .calendar_body .calendar_day').remove();
    for (let i = 0; i < days_list.length; i++) {
        // 클래스 부여, 이전 달 또는 다음 달은 disabled 설정
        let day_class = 'calendar_day';
        if (days_list[i][1] != select_month) {
            day_class = 'calendar_day disabled_day';
        }

        // html 추가
        $('#calendar_wrap .calendar_body').append('<div id="cal_idx_' + i +'" class="' + day_class +'"><span class="day_word">' + days_list[i][2] +'</span></div>');
        
        // 토요일 파란색 추가
        if((i + 1) % 7 == 0) {
            $('#cal_idx_' + i).addClass('color_blue');
        }

        // 일요일 빨간색 추가
        if((i + 1) % 7 == 1) {
            $('#cal_idx_' + i).addClass('color_red');
        }

        // 오늘 날짜 강조
        let today = new Date();
        current_year = today.getFullYear();
        current_month = today.getMonth() + 1;
        current_day = today.getDate();

        if(days_list[i][0] == current_year && days_list[i][1] == current_month && days_list[i][2] == current_day) {
            $('#cal_idx_' + i).addClass('today');
        }

        // 각 일별 (년, 월, 일 데이터 추가)
        $('#cal_idx_' + i).data('year', days_list[i][0]);
        $('#cal_idx_' + i).data('month', days_list[i][1]);
        $('#cal_idx_' + i).data('day', days_list[i][2]);

        // 날짜 클릭 이벤트 추가
        $('#cal_idx_' + i + ' span.day_word').click(function(e) {
            let year = $(e.target).parent().data('year');
            let month = $(e.target).parent().data('month');
            let day = $(e.target).parent().data('day');
            display_schedule_list_modal(year, month, day)
        } );
    }

    // 캘린더에 휴일 정보 보여주기
    display_hoildays(days_list);

    // 캘린더에 일정 보여주기
    ajax_get_schedule();
}

// 캘린더 날짜 리스트 만들기
// 결과 [[년, 월, 일], ...]
// list[i][0] -> 년, list[i][1] -> 월, list[i][2] -> 일
function calender_get_days_list() { 
    console.log("calender_get_days_list() CALLED!!");
    
    let days_list = [];

    // 선택한 달의 첫째 날의 요일 가져오기
    let prev_month_first_day_of_week = (new Date(select_year, select_month - 1, 1).getDay());


    // 이전 달의 마지막 날짜 구하기
    let prev_month_last_day = new Date(select_year, select_month - 1, 0).getDate();

    // 이전 달의 정보 리스트에 담기
    for(let i = prev_month_first_day_of_week - 1; i >= 0; i--) {
        days_list.push([prev_year, prev_month, prev_month_last_day - i]);
    }

    // 선택한 달의 정보 리스트에 담기
    let select_month_last_day = new Date(select_year, select_month, 0).getDate();
    for (let i = 1; i <= select_month_last_day; i++) {
        days_list.push([select_year, select_month, i]);
    }

    // 다음 달의 정보 리스트에 담기
    let next_month_day = 1;
    for (let i = days_list.length; i < 42; i++) {
        days_list.push([next_year, next_month, next_month_day]);
        next_month_day++;
    }

    return days_list;
}

// google calendar api를 이용하여 한국 휴일 정보 가져와서 캘린더에 표시하기
function display_hoildays(days_list) {
    console.log('display_hoildays() CALLED!!');

    let request_select_month = select_month;

    // google calendar api 필요 정보
    let google_calendar_url = 'https://www.googleapis.com/calendar/v3/calendars/ko.south_korea.official%23holiday%40group.v.calendar.google.com/events';
    let api_key = 'AIzaSyDpIoyDXAz3ecEMQSOWJf_Y7Ad2v0EZs7Y';
    let time_min = make_data_format_for_google_calendar(days_list[0]);
    let time_max = make_data_format_for_google_calendar(days_list[days_list.length - 1]);

    let data = {
        orderBy: 'startTime',
        singleEvents: 'true',
        timeMax: time_max,
        timeMin: time_min,
        key: api_key
    };
    
    $.ajax({
        url: google_calendar_url,
        data: data, 
        method: "GET",
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            // 서버 통신 성공 후에 현재 선택된 달과 요청시 선택된 달이 다를 경우 리턴
            if (request_select_month != select_month) {
                return;
            }

            console.log('display_hoildays AJAX SUCCESS!!');

            let holidays_json = result;

            for (let i = 0; i < holidays_json.items.length; i++) {
                // 휴일 정보 데이터를 사용하기 위해 분해하기
                
                let holiday_start = holidays_json.items[i].start.date;
                let holiday_name = holidays_json.items[i].summary;
                holiday_year = parseInt(holiday_start.split('-')[0]);
                holiday_month = parseInt(holiday_start.split('-')[1]);
                holiday_day = parseInt(holiday_start.split('-')[2]);

                // 휴일 정보 html에 표시하기
                for (let j = 0; j < days_list.length; j++) {
                    if (holiday_year == days_list[j][0] && holiday_month == days_list[j][1] && holiday_day == days_list[j][2]) {
                        let target_dat = $('#cal_idx_' + j);
                        target_dat.prepend('<span class="holiday_name">' + holiday_name +'</span>');

                        // css 빨간색 추가
                        let classes = target_dat.attr("class").split(' ');
                        if (classes.includes('color_blue')) {
                            target_dat.removeClass('color_blue');
                            target_dat.addClass('color_red');
                        } else if (!classes.includes('color_red')) {
                            target_dat.addClass('color_red');
                        }
                    }
                }
            }

        }, 
        error: function(result) {
            console.log(result);
        }
    });
}

// google calendar api를 이용하기 위한 시간 formatter
function make_data_format_for_google_calendar(day_info) {
    return day_info[0] + '-' + ('00' + day_info[1]).slice(-2) + '-' + ('00' + day_info[2]).slice(-2) + 'T00:00:00Z'
}

// 오늘, 일정 생성 버튼 숨기기
function btn_display_none() {
    $('#go_today_button').css('display', 'none');
    $('#create_schedule_button').css('display', 'none');
}

// 오늘, 일정 생성 버튼 보이기
function btn_display() {
    $('#go_today_button').css('display', 'block');
    $('#create_schedule_button').css('display', 'block');
}

function ajax_get_schedule() {
    console.log('ajax_get_schedule() CALLED!!');

    let days_list = calender_get_days_list();
    $('#calendar_wrap .calendar_body .schedule').remove();
    $('#calendar_wrap .calendar_body .etc_schedule').remove();

    // 일정 종류 체크박스 확인
    let scd_auth_range_type_no_list = [];
    let private_schedule = false;
    let project_schedule = false;
    let department_schedule = false;
    let company_schedule = false;

    $('.calendar_schedule_type_checkbox_wrap input[name="scd_auth_range_type_no"]').each(function() {
        if (this.checked) {
            let checked_val = $(this).val();
            scd_auth_range_type_no_list.push(checked_val);

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

    if (scd_auth_range_type_no_list.length == 0) {
        return;
    }

    // ajax 통신
    let msg = {
        start_date : days_list[0].join('-'),
        end_date : days_list[days_list.length - 1].join('-'),
        scd_auth_range_type_no_list: scd_auth_range_type_no_list,
        private_schedule: private_schedule,
        project_schedule: project_schedule,
        department_schedule: department_schedule,
        company_schedule: company_schedule
    };

    $.ajax({
        url: ctx + "/schedule/get_schedule_list",
        type: "POST",
        data: JSON.stringify(msg), 
        contentType: 'application/json; charset=utf-8;', 
        dataType: "json",
        success: function(result) {
            console.log('ajax_get_schedule() SUCCESS!!');
            
            if (result.result == 'success') {
                let scheduleVos = result.scheduleVos;

                display_schedule(days_list, scheduleVos);
                $('#calendar_wrap .calendar_body .schedule').click(function(e){
                    schedule_click_event(e.target, 'calendar')}
                );
            } else {
                console.log('일정 불러오기 실패');
            }
        },
        error : function(result) {
            console.log('ajax_get_schedule() FAIL!!');
        }
    });

   
}

// 일정 가져와서 보여주기
function display_schedule(days_list, scheduleVos) {
    console.log('display_schedule() CALLED!!');
    
    let cnt_schedule = [];
    for (let i = 0; i < 42; i++) {
        cnt_schedule.push([0, 0, 0]);
    }

    // 일정 시작 날짜, 끝 날짜 변경 
    for (let i = 0; i < scheduleVos.length; i++) {
        scheduleVos[i].scd_cal_disp_start_date = [scheduleVos[i].scd_start_year, scheduleVos[i].scd_start_month, scheduleVos[i].scd_start_day]
        scheduleVos[i].scd_cal_disp_end_date = [scheduleVos[i].scd_end_year, scheduleVos[i].scd_end_month, scheduleVos[i].scd_end_day]

        if (new Date(scheduleVos[i].scd_cal_disp_start_date[0], scheduleVos[i].scd_cal_disp_start_date[1] - 1, scheduleVos[i].scd_cal_disp_start_date[2]) < new Date(days_list[0][0], days_list[0][1] - 1, days_list[0][2])) {
            scheduleVos[i].scd_cal_disp_start_date[0] = days_list[0][0];
            scheduleVos[i].scd_cal_disp_start_date[1] = days_list[0][1];
            scheduleVos[i].scd_cal_disp_start_date[2] = days_list[0][2];
        }
        if (new Date(scheduleVos[i].scd_cal_disp_end_date[0], scheduleVos[i].scd_cal_disp_end_date[1] - 1, scheduleVos[i].scd_cal_disp_end_date[2]) > new Date(days_list[days_list.length - 1][0], days_list[days_list.length - 1][1] - 1, days_list[days_list.length - 1][2])) {
            scheduleVos[i].scd_cal_disp_end_date[0] = days_list[days_list.length - 1][0];
            scheduleVos[i].scd_cal_disp_end_date[1] = days_list[days_list.length - 1][1];
            scheduleVos[i].scd_cal_disp_end_date[2] = days_list[days_list.length - 1][2];
        }
    }

    // 일정 깊이 구하기
    for (let i = 0; i < scheduleVos.length; i++) {
        
        let period = getDatePeriod([scheduleVos[i].scd_cal_disp_start_date[0], scheduleVos[i].scd_cal_disp_start_date[1], scheduleVos[i].scd_cal_disp_start_date[2]], [scheduleVos[i].scd_cal_disp_end_date[0], scheduleVos[i].scd_cal_disp_end_date[1], scheduleVos[i].scd_cal_disp_end_date[2]])
        for (let j = 0; j < days_list.length; j++) {
            if (days_list[j][0] == scheduleVos[i].scd_cal_disp_start_date[0]){
                if (days_list[j][1] == scheduleVos[i].scd_cal_disp_start_date[1]){
                    if (days_list[j][2] == scheduleVos[i].scd_cal_disp_start_date[2]){

                        // 일정 디스플레이 깊이 설정
                        let deep = 0;
                        for(let k = 0; k < period; k++) {
                            if (cnt_schedule[j + k][2] == 1) {
                                deep = 2;
                                break;
                            } 
                            if (cnt_schedule[j + k][0] == 1){
                                deep = 1;
                            }
                        }
                        if (deep == 1) {
                            for(let k = 0; k < period; k++) {
                                if (cnt_schedule[j + k][2] == 1) {
                                    deep = 2;
                                    break;
                                } 
                                if (cnt_schedule[j + k][1] == 1){
                                    deep = 2;
                                }
                            }
                        }
                        scheduleVos[i].deep = deep;
                        // 깊이 체크
                        for(let k = 0; k < period; k++) {
                            cnt_schedule[j + k][deep] = 1;
                        }
                    }
                }
            }
        }
    }

    // console.log(getDatePeriod([scd_cal_disp_start_year, scd_cal_disp_start_month, scd_cal_disp_start_day], [scd_cal_disp_end_year, scd_cal_disp_end_month, scd_cal_disp_end_day]));
    // 한주마다 뿌리기
    for (let week = 0; week < 6; week++) {
        for (let day = 0; day < 7; day++) {
            for (let i = 0; i < scheduleVos.length; i++) {
                if (days_list[(week * 7) + day][0] == scheduleVos[i].scd_cal_disp_start_date[0]){
                    if (days_list[(week * 7) + day][1] == scheduleVos[i].scd_cal_disp_start_date[1]){
                        if (days_list[(week * 7) + day][2] == scheduleVos[i].scd_cal_disp_start_date[2]){
                            let period = getDatePeriod([scheduleVos[i].scd_cal_disp_start_date[0], scheduleVos[i].scd_cal_disp_start_date[1], scheduleVos[i].scd_cal_disp_start_date[2]], [scheduleVos[i].scd_cal_disp_end_date[0], scheduleVos[i].scd_cal_disp_end_date[1], scheduleVos[i].scd_cal_disp_end_date[2]]);
                            if (new Date(scheduleVos[i].scd_cal_disp_end_date[0], scheduleVos[i].scd_cal_disp_end_date[1] - 1, scheduleVos[i].scd_cal_disp_end_date[2]) > new Date(days_list[(week * 7) + 6][0], days_list[(week * 7) + 6][1] - 1, days_list[(week * 7) + 6][2])) {
                                period = 7 - day;
                                scheduleVos[i].scd_cal_disp_start_date[0] = days_list[((week + 1) * 7)][0];
                                scheduleVos[i].scd_cal_disp_start_date[1] = days_list[((week + 1) * 7)][1];
                                scheduleVos[i].scd_cal_disp_start_date[2] = days_list[((week + 1) * 7)][2];
                            }

                            create_schedule_html(scheduleVos[i], week, day, period);
                        }    
                    }
                }
            }
        }
    }

    // 각 날짜별로 일정 정보 담기
    for (let i = 0; i < cnt_schedule.length; i++) {
        // 일정 3개 이상 ... 표시 및 클릭 이벤트 추가
        if(cnt_schedule[i][2] == 1) {
            $('#cal_idx_' + i).append('<span class="etc_schedule">...</span>');
            $('#cal_idx_' + i + ' span.etc_schedule').click(function(e) {
                let year = $(e.target).parent().data('year');
                let month = $(e.target).parent().data('month');
                let day = $(e.target).parent().data('day');
                display_schedule_list_modal(year, month, day)
            } );
        }
        
        
    }
}

// 일정 html만들기
function create_schedule_html(scheduleVo, week, day, period) {
    $('#calendar_wrap .calendar_body').prepend('<section id="scd_idx_' + scheduleVo.scd_no + '" class="schedule">' + scheduleVo.scd_title + '</section>');
    $('#scd_idx_' + scheduleVo.scd_no).data('scd_no', scheduleVo.scd_no);

    switch(scheduleVo.scd_auth_range_type.scd_auth_range_type_no) {
        case 0:
            $('#scd_idx_' + scheduleVo.scd_no).addClass('private');
            break;
        case 1:
            $('#scd_idx_' + scheduleVo.scd_no).addClass('project');
            break;
        case 2:
            $('#scd_idx_' + scheduleVo.scd_no).addClass('department');
            break;
        case 3:
            $('#scd_idx_' + scheduleVo.scd_no).addClass('company');
            break;
    }

    $('#scd_idx_' + scheduleVo.scd_no).css('grid-column', day + 1 + '/span ' + period);
    $('#scd_idx_' + scheduleVo.scd_no).css('grid-row', week + 2);
    if (scheduleVo.deep == 0) {
        $('#scd_idx_' + scheduleVo.scd_no).css('margin-top', '30px');
    } else if (scheduleVo.deep == 1) {
        $('#scd_idx_' + scheduleVo.scd_no).css('margin-top', '66px');
    } else if (scheduleVo.deep == 2) {
        $('#scd_idx_' + scheduleVo.scd_no).css('display', 'none');
    }
    
}

// 기간 구하기
function getDatePeriod(start_date, end_date) {
    let date1 = new Date(start_date[0], start_date[1] - 1, start_date[2]);
    let date2 = new Date(end_date[0], end_date[1] - 1, end_date[2]);
    
    let diffDate = date1.getTime() - date2.getTime();
    
    return Math.abs(diffDate / (1000 * 60 * 60 * 24)) + 1; // 밀리세컨 * 초 * 분 * 시 = 일
}

// 스케줄 클릭 이벤트
function schedule_click_event(target, prev_page) {
    let scd_no = $(target).data('scd_no');

    display_schedule_detail_modal(scd_no, prev_page);
}

