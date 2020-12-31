/**
 * 
 */

function findUserInfoFunction(){

	var email = document.getElementById('mailAdd').value;
	var email_check = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/; // 이메일 형식에 맞도록

	if(email.length == 0 || email == null) {
		alert("메일주소를 입력해 주세요.");
		document.getElementById('mailAdd').focus();
		return false;
	}
	if(!email_check.test(email)) {
		alert("메일 형식에 맞게 입력해 주세요. ex) ****@****.com");
		document.getElementById('mailAdd').focus();
		return false;
	}
	if(email.length > 40) {
		alert("메일주소는 최대 영문, 숫자, 기호 40자이내입니다.");
		document.getElementById('mailAdd').focus();
		return false;
	}

	var jsonForm = {
			mailAdd: email
	}

	$.ajax({
		type:"POST",           //요청방식
		url:"findUserInfo",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var checkFlg = data.result.toString();
			var message = data.message.toString();

			if(checkFlg != "0") {
				alert(message);
				return false;
			} else {
				document.getElementById('mailAdd').setAttribute("readonly", "readonly");
				alert(message);
			}
		},
		error : function(error) {
			alert(error);
			return false;
		}
	});
	return true;
}