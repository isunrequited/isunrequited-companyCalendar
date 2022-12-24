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
    $('#month_select_modal .month_select_modal_body .month_list_header .modal_year_word').text(list_select_year);

    // 화살표에 해당 년도 데이터 삽입
    let list_prev_year = list_select_year - 1;
    let list_next_year = list_select_year + 1;

    $('#month_select_modal .month_select_modal_body .month_list_header .year_arrows i.prev_year_arrow').data('year', list_prev_year);
    $('#month_select_modal .month_select_modal_body .month_list_header .year_arrows i.next_year_arrow').data('year', list_next_year);

    // month list html 생성
    $('#month_select_modal .month_select_modal_body .month_list_body .month_word').remove();
    for (let month = 1; month <= 12; month++) {
        $('#month_select_modal .month_select_modal_body .month_list_body').append('<div class="month_word" id="' + month +'">' + month + '</div>');
        $('#' + month).data('month', month);
        $('#' + month).data('year', list_select_year);
        if (select_year == list_select_year && select_month == month) {
            $('#' + month).addClass('selected');
        }
    }

     // 모달 달 클릭 이벤트
    $('#month_select_modal .month_select_modal_body .month_list_body .month_word').click(function() {
        console.log('MONTH IN MODAL CLICK!!');

        // 년도 월 설정 변경
        set_year_month($(this).data('year'), $(this).data('month'));

        $('#month_select_modal').css('display', 'none');
        calendar_create_days();
    });
}

// 캘린더 모달 이벤트 추가
function calendar_modal_add_events() {
    // 모달 화살표 클릭 이벤트
    $('#month_select_modal .month_select_modal_body .month_list_header .year_arrows i').click(function() {
        console.log('ARROW IN MODAL CLICK!!');
        let list_select_year = $(this).data('year');
        display_month_list(list_select_year);
    });
}