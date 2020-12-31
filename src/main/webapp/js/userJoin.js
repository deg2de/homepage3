/**
 * 
 */
function idCheckFunction() {
	var id = document.getElementById('id').value;
	var id_check = /^[a-z0-9]{6,10}$/; // id는 영소문자, 숫자만 가능, 자릿수는 6~10자리이내

	if(id.length == 0 || id == null) {
		alert("ID를 입력해 주세요.");
		document.getElementById('id').focus();
		return false;
	}
	if(!id_check.test(id)) {
		alert("ID의 입력형식이 틀렸습니다.(영소문자, 숫자로 6~10자리이내)");
		document.getElementById('id').focus();
		return false;
	}
	
	var jsonFormId = {
		id:id
	}
	
	$.ajax({
		type:"POST",//요청방식
		url:"userJoinIdCheck",//요청경로
		data:JSON.stringify(jsonFormId),// 서버로 보낼 데이터
		contentType:"application/json; charset=UTF-8",// 서버로 전송하는 데이터 형식
		dataType:"json",// 서버에서 받아오는 데이터 형식(xml,json,script,html)
		success : function(data) {
			var message = data.message.toString();
			var checkFlg = data.result;

			if(checkFlg == 0) {
				var idUseCheck = confirm(message);
				if(idUseCheck) {
					document.getElementById("checkIdF").setAttribute("value", "1");
					document.getElementById('id').setAttribute("readonly", "readonly");
					$('#checkIdBtn').remove();
					$('#id').css("margin-right", "50px");
					document.getElementById('pw').removeAttribute("readonly");
					document.getElementById('pw2').removeAttribute("readonly");
				} else {
					document.getElementById("checkIdF").setAttribute("value", "0");
					return false;
				}
			} else {
				document.getElementById("checkIdF").setAttribute("value", "0");
				alert(message);
				return false;
			}

		},
		error : function(error) {
			alert(error);
			return false;
		}
	});

	return true;
}

function pwCheckFunction() {
	var pw = document.getElementById('pw').value;
	var pw2 = document.getElementById('pw2').value;
	var pw_check = /^[a-zA-Z0-9!@#$%^&*()?_~]{10,15}$/; // pw는 영대소문자, 숫자, 특수기호만 가능, 10~15자리이내

	// id결정 확인
	var idF = document.getElementById('checkIdF').value;
	if(idF == 0) {
		alert("ID를 먼저 결정해 주세요.");
		document.getElementById('id').focus();
		return false;
	}
	// pw의 입력여부 확인
	if(pw.length == 0 || pw == null) {
		alert("PW를 입력해 주세요.");
		document.getElementById('pw').focus();
		return false;
	}
	// pw2의 입력여부 확인
	if(pw2.length == 0 || pw2 == null) {
		alert("PW확인을 입력해 주세요.");
		document.getElementById('pw2').focus();
		return false;
	}
	// pw의 자릿수, 입력형태 확인
	if(!pw_check.test(pw)) {
		alert("PW의 입력형식이 틀렸습니다. (영대소문자, 숫자, 특수기호 10~15자리)");
		document.getElementById('pw').focus();
		return false;
	}
	// pw,pw2의 동일여부 확인
	if(pw != pw2) {
		alert("PW와 PW확인이 같지 않습니다.");
		document.getElementById('pw2').focus();
		return false;
	}
	
	var pwUseCheck = confirm("정말 해당 비밀번호를 사용하시겠습니까? (변경 불가)");
	if(pwUseCheck) {
		document.getElementById("checkPwF").setAttribute("value", "1");
		document.getElementById('pw').setAttribute("readonly", "readonly");
		document.getElementById('pw2').setAttribute("readonly", "readonly");
		$('#checkPwBtn').remove();
		$('#pw2').css("margin-right", "50px");
		document.getElementById('name').removeAttribute("readonly");
		document.getElementById('mailAdd').removeAttribute("readonly");
		document.getElementById('phoNum').removeAttribute("readonly");
	} else {
		return false;
	}
	return true;
}

function mailCheckFunction(){

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
		url:"userJoinMailCheck",          //요청경로
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
				var emailUseCheck = confirm(message);

				if(emailUseCheck) {
					document.getElementById('mailAdd').setAttribute("readonly", "readonly");
					$('#checkMailBtn').remove();
					$('#mailAdd').css("margin-right", "50px");
					
					var authCode_div = document.createElement('div');
					authCode_div.setAttribute("class", "input-group input-group-lg userJoin_input_check");
					
					var authCode_nameDiv = document.createElement('div');
					authCode_nameDiv.setAttribute("class", "input-group-prepend");
					
					var suthCode_name = document.createElement('span');
					suthCode_name.setAttribute("class","input-group-text inputGroup-sizing-lg");
					suthCode_name.appendChild(document.createTextNode("인증번호"));
					
					var authCode_input = document.createElement('input');
					authCode_input.setAttribute("type", "text");
					authCode_input.setAttribute("id", "authCode");
					authCode_input.setAttribute("class", "form-control");
					authCode_input.setAttribute("aria-label", "Sizing example input");
					authCode_input.setAttribute("aria-describedby", "inputGroup-sizing-lg");
					
					var authCode_btn = document.createElement('input');
					authCode_btn.setAttribute("type", "button");
					authCode_btn.setAttribute("id", "checkAuthCodeBtn");
					authCode_btn.setAttribute("class", "btn btn-secondary userJoin_input_check_btn");
					authCode_btn.setAttribute("onclick", "return numCheckFunction()");
					authCode_btn.setAttribute("value", "CK");
					
					var authCode_hidden = document.createElement('input');
					authCode_hidden.setAttribute("type", "hidden");
					authCode_hidden.setAttribute("id", "authCodeH");
					authCode_hidden.setAttribute("value", "0");
					
					authCode_nameDiv.appendChild(suthCode_name);
					authCode_div.appendChild(authCode_nameDiv);
					authCode_div.appendChild(authCode_input);
					authCode_div.appendChild(authCode_btn);
					authCode_div.appendChild(authCode_hidden);
					$('#mail_input').after(authCode_div);
					
					$.ajax({
						type:"POST",           //요청방식
						url:"userJoinMailNum",          //요청경로
						dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
						data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
						contentType: "application/json; charset=utf-8;",
						success : function(data) {
							var authCode = data.authCode.toString();
							var checkNum = data.result.toString();
							var message = data.message.toString();
							
							if(checkNum != "0") {
								alert(message);
								return false;
							} else {
								$('#authCodeH').attr("value", authCode);
								alert(message);
							}
						},
						error : function(error) {
							alert(error);
							return false;
						}
					});
				} else{
					return false;
				}
			}
		},
		error : function(error) {
			alert(error);
			return false;
		}
	});
	return true;
}

/*
function timmerFunction(saveNum){
	setTimeout(()=>{
		var emailCFlg = document.getElementById("checkFlg").getAttribute("value");

		if(emailCFlg != 1){
			saveNum.setAttribute("value", "");
			$('#emailCheck_button').attr('onclick', 'return emailCheck()');
			$('#emailCheck_button').attr('value', '인증번호전송');
			alert("메일 인증시간이 초과되었습니다.");
		}
		},240000);
}
**/

function numCheckFunction(){
	var saveNum = document.getElementById('authCodeH').value;
	var inputNum = document.getElementById('authCode').value;

	if(saveNum.length == 0 || saveNum == null) {
		saveNum.setAttribute("value", "0");
		alert("인증번호가 메일에 전송되지 않았습니다. 새로고침 후 다시 작성해 주세요.");
		return false;
	}

	if(saveNum != inputNum) {
		alert("입력하신 인증번호는 맞지 않습니다.");
		return false;
	} else if(saveNum == inputNum) {
		$('#checkAuthCodeBtn').remove();
		$('#authCode').attr("readonly", "readonly");
		$('#authCode').css("margin-right", "50px");
		$('#checkMailF').attr('value', '1');
		alert("인증되었습니다.");
		return true;
	} else {
		alert("처리 실패, 관리자에게 문의 바랍니다.");
		return false;
	}
}

function userJoinSucFunction(){
	
	var id = document.getElementById('id').value;
	var pw = document.getElementById('pw').value;
	var name = document.getElementById('name').value;
	var email = document.getElementById('mailAdd').value;
	var phone = document.getElementById('phoNum').value;
	
	var idFlg = document.getElementById('checkIdF').value;
	var pwFlg = document.getElementById('checkPwF').value;
	var emailFlg = document.getElementById('checkMailF').value;
	
	var name_check = /^[가-힣]{2,10}/; // 한글 2~10자
	var phone_check = /^\d{2,3}-\d{3,4}-\d{4}$/; // 핸드폰 형식
	
	if(idFlg != "1" || pwFlg != "1" || emailFlg != "1") {
		alert("인증절차가 마무리 되지 않았습니다. 인증되지 않은 입력이 있는지 확인해 주세요.");
		return false;
	}
	
	if(name.length == 0 || name == null) {
		alert("이름을 입력해 주세요.");
		document.getElementById('name').focus();
		return false;
	}
	if(!name_check.test(name)) {
		alert("이름은 한글로 2~10자이내로 입력해 주세요.");
		document.getElementById('name').focus();
		return false;
	}
	
	if(phone.length == 0 || phone == null) {
		alert("번호를 입력해 주세요.");
		document.getElementById('phoNum').focus();
		return false;
	}
	if(!phone_check.test(phone)) {
		alert("번호 형식에 맞게 입력해 주세요. ex)010-0000-0000");
		document.getElementById('phoNum').focus();
		return false;
	}
	
	var lastCheck = confirm("정말로 해당 정보로 가입하시겠습니까?");
	
	if(lastCheck) {
		var jsonFormId = {
			id:id,
			pw:pw,
			name:name,
			mailAdd:email,
			phoNum:phone
		}
	
		$.ajax({
			type:"POST",//요청방식
			url:"userJoin",//요청경로
			data:JSON.stringify(jsonFormId),// 서버로 보낼 데이터
			contentType:"application/json; charset=UTF-8",// 서버로 전송하는 데이터 형식
			dataType:"json",// 서버에서 받아오는 데이터 형식(xml,json,script,html)
			success : function(data) {
				var message = data.message.toString();
				var checkFlg = data.result;
				
				if(checkFlg == 0) {
					alert(message);
				} else {
					alert(message);
					return false;
				}
			},
			error : function(error) {
				alert(error);
				return false;
			}
		});
		return true;
	} else {
		return false;
	}
}