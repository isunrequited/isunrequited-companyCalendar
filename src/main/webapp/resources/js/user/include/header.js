$(document).ready(function(){
	console.log('DOCUMENT READY!!');
	
});

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

function go_work(){
	console.log('go_work() CALLED!!');
	
	let now = setNowDateTime()
	
	let msg = {
		atd_year: now[0],
		atd_month: now[1],
		atd_day: now[2],
		atd_start_time: now[3]
	}
	console.log(msg);
	
	$.ajax({
        url: ctx + "/attendance/go_work",
        type: "POST",
        data: JSON.stringify(msg), 
		contentType: 'application/json; charset=utf-8;', 
		dataType: 'json',
        success: function(data){
			console.log('go_work ajax SUCCESS!!');
			if (data.result == 'success') {
				alert('success');
				// 출근 none 퇴근 display
				// 부분 refresh
			} else {
				alert('fail');
			}
        },
        error: function(){
			console.log('go_work ajax FAIL!!');
        }
  	});
}
