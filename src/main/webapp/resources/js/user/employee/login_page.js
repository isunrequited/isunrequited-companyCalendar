function loginForm() {
	console.log('loginForm() CALLED!!');
	
	var form = document.login_form;
	
	if (form.emp_email.value == '') {
		alert('INPUT YOUR ID');
		form.emp_email.focus();
		
	} else if (form.emp_pw.value == '') {
		alert('INPUT YOUR PW');
		form.emp_pw.focus();
		
	} else {
		var msg = {
			emp_email : $('form[name=login_form] input[name=emp_email]').val(),
			emp_pw : $('form[name=login_form] input[name=emp_pw]').val(),
		};
		console.log(msg); 
		
		$.ajax({
        url: ctx + "/employee/login_confirm",
        type: "POST",
        data: JSON.stringify(msg), 
		contentType: 'application/json; charset=utf-8;', 
		dataType: 'json',
        success: function(data){
			console.log('login_confirm() ajax SUCCESS');
			if (data.result == 'success') {
				location.href = ctx + "/";
			} else {
				alert('이메일 또는 비밀번호가 다릅니다.');
			}
        },
        error: function(){
			console.log('login_confirm() ajax FAIL');
        }
  	});
		
	}
	
}

function create_temp_account() {
	console.log('create_temp_account() CALLED!!');

	var msg = {
		
	};
	
	console.log(msg); 
		
	$.ajax({
        url: ctx + "/employee/create_temp_account",
        type: "POST",
        data: JSON.stringify(msg), 
		contentType: 'application/json; charset=utf-8;', 
		dataType: 'json',
        success: function(data){
			console.log('create_temp_account() ajax SUCCESS');
			
			if (data.result == 'success') {
				alert('success');
			} else {
				alert('fail');
			}
        },
        error: function(){
			console.log('create_temp_account() ajax FAIL');
        }
  	});
}