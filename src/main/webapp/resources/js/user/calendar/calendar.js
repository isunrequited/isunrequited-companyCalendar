// 문서 초기 상태(처음 한번 실행)
$(document).ready(function() {
    calendar_ready();
    
});

function calendar_ready() {
    console.log('CALENDAR READY()!!');
    // 오늘 날짜로 셋팅
    set_today();

    // 이벤트 추가
    calendar_add_events();  
    calendar_modal_add_events();
}

// 페이지 동적 이벤트
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

    // 년월 선택 모달 이벤트
    $('#calendar_wrap .calendar_header h1.calendar_year_month').click(function() {
        display_month_select_modal();
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
        $('#calendar_wrap .calendar_body').append('<div id="cal_idx_' + i +'" class="' + day_class +'">' + days_list[i][2] +'</div>');

        // 토요일 파란색 추가
        if((i + 1) % 7 == 6) {
            $('#cal_idx_' + i).addClass('color_blue');
        }

        // 일요일 빨간색 추가
        if((i + 1) % 7 == 0) {
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
    }

    // 캘린더에 휴일 정보 보여주기
    display_hoildays(days_list);

    // 캘린더에 일정 보여주기
}

// 캘린더 날짜 리스트 만들기
// 결과 [[년, 월, 일], ...]
// list[i][0] -> 년, list[i][1] -> 월, list[i][2] -> 일
function calender_get_days_list() { 
    console.log("calender_get_days_list() CALLED!!");
    
    let days_list = [];

    // 선택한 달의 첫째 날의 요일 가져오기
    let prev_month_first_day_of_week = (new Date(select_year, select_month - 1, 1).getDay()) - 1;
    if (prev_month_first_day_of_week == -1) {
        prev_month_first_day_of_week = 6
    }


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