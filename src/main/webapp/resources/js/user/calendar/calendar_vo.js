var select_year;
var select_month;
var prev_year;
var prev_month;
var next_year;
var next_month;

// 년도, 월 설정
function set_year_month(year, month) {
    console.log('set_year_month() CALLED!!');

    select_year = year;
    select_month = month;
    next_year = select_year;
    next_month = select_month + 1;
    if (select_month == 12) {
        next_year = select_year + 1;
        next_month = 1;
    }

    prev_year = select_year;
        prev_month = select_month - 1;
    if (select_month == 1) {
        prev_year = select_year - 1;
        prev_month = 12;
    }
}

