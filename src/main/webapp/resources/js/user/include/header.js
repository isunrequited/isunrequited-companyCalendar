function header_ready() {
	console.log("HEADER READY!!");

	add_header_events();
	check_att_status();
}
//now time set
function setNowDateTime() {
	console.log('setNowDateTime() CALLED!');	
	let options = { hour: "numeric", minute: "numeric", second: "numeric", hour12: false };
	let now = new Date();
	let year = now.getFullYear();
	let month = now.getMonth();
	let day = now.getDate();
	let time = now.toLocaleTimeString("en-US", options);

	let time_datas = [year, month, day, time];
	
	return time_datas;
}

// attendance
function go_work(){
	console.log('go_work() CALLED!');
	let now = setNowDateTime()
	
	let msg = {
		atd_year: now[0],
		atd_month: now[1],
		atd_day: now[2],
		atd_start_time: now[3]
	}
	console.log(msg);
	
	if (!confirm("출근하시겠습니까?")) {
            
	} else {
		$.ajax({
			url: ctx + "/attendance/go_work",
			type: "POST",
			data: JSON.stringify(msg), 
			contentType: 'application/json; charset=utf-8;', 
			dataType: 'json',
			success: function(data){
				if (data.result == 'success') {
					check_att_status();
				}
			},
			error: function(){
				check_att_status();
			}
		});
	}
}

// off work
function go_home(){
	console.log('go_home() CALLED!');
		
	let now = setNowDateTime()
	
	let msg = {
		atd_year: now[0],
		atd_month: now[1],
		atd_day: now[2],
		atd_end_time: now[3]
	}
	console.log(msg);
	if (!confirm("퇴근 하시겠습니까?")) {
            
	} else {
		$.ajax({
			url: ctx + "/attendance/go_home",
			type: "POST",
			data: JSON.stringify(msg), 
			contentType: 'application/json; charset=utf-8;', 
			dataType: 'json',
			success: function(data){
				if (data.result == 'success') {
					check_att_status();
				}
        	},
			error: function(){
				check_att_status();
			}
		});
	}
}
function logout(){
        if (!confirm("로그아웃 하시겠습니까?")) {
            
        } else {
              $.ajax({
            type:"POST",
            url: ctx + "/employee/logout_confirm",
            success:function(data){
			if (data.result == 'success') {
                location.href ="/comcal";
            } else {
				alert('fail');
			}
        },
        error: function(){
            alert("err");
            location.href ="/comcal";
        }
     }); 
    }
}

function check_att_status() {
	msg = {};
	$.ajax({
		url: ctx + "/attendance/attendance_check",
		type: "POST",
		data: JSON.stringify(msg), 
		contentType: 'application/json; charset=utf-8;', 
		dataType: 'json',
		success: function(result){
			if(result.result == 'success') {
				if (result.att_status == 0) {
					$(".att_btn").css("display","block");
					$(".home_btn").css("display","none");
				} else {
					$(".att_btn").css("display","none");
					$(".home_btn").css("display","block");
				}
			}
		}
	});
}


function add_header_events() {
    $('.left-side-bar .gnb .display_calendar').click(function() {
        $('#calenar_section').css('display', 'block');
        $('#project_section').css('display', 'none');
        $('#attendance_section').css('display', 'none');
		$('.left-side-bar .gnb .display_calendar').addClass('gnb_selected');
        $('.left-side-bar .gnb .display_project').removeClass('gnb_selected');
        $('.left-side-bar .gnb .display_attendance').removeClass('gnb_selected');
    });

    $('.left-side-bar .gnb .display_project').click(function() {
        $('#calenar_section').css('display', 'none');
        $('#project_section').css('display', 'block');
        $('#attendance_section').css('display', 'none');
		$('.left-side-bar .gnb .display_calendar').removeClass('gnb_selected');
        $('.left-side-bar .gnb .display_project').addClass('gnb_selected');
        $('.left-side-bar .gnb .display_attendance').removeClass('gnb_selected');
    });

    $('.left-side-bar .gnb .display_attendance').click(function() {
        $('#calenar_section').css('display', 'none');
        $('#project_section').css('display', 'none');
        $('#attendance_section').css('display', 'block');
		$('.left-side-bar .gnb .display_calendar').removeClass('gnb_selected');
        $('.left-side-bar .gnb .display_project').removeClass('gnb_selected');
        $('.left-side-bar .gnb .display_attendance').addClass('gnb_selected');
    });

	$('.left-side-bar .gnb_2 .att_btn').click(function() {
		go_work();
	});

	$('.left-side-bar .gnb_2 .home_btn').click(function() {
		go_home();
	});

	$('.left-side-bar .gnb_2 .logout_btn').click(function() {
		logout();
	});
}