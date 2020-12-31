/**
 * 
 */

function pwCheckFunction() {
	var pw = document.getElementById('pw').value;
	var pw2 = document.getElementById('pw2').value;
	var pw_check = /^[a-zA-Z0-9!@#$%^&*()?_~]{10,15}$/; // pw는 영대소문자, 숫자, 특수기호만 가능, 10~15자리이내

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
	} else {
		return false;
	}
	return true;
}

function infoChangeSucFunction(){
	
	var id = document.getElementById('id').value;
	var pw = document.getElementById('pw').value;
	var name = document.getElementById('name').value;
	var phone = document.getElementById('phoNum').value;
	
	var pwFlg = document.getElementById('checkPwF').value;
	
	var name_check = /^[가-힣]{2,10}/; // 한글 2~10자
	var phone_check = /^\d{2,3}-\d{3,4}-\d{4}$/; // 핸드폰 형식
	
	if(pwFlg != "1") {
		alert("비밀번호는 필수 입력조건입니다. 변경을 원하지 않는 경우 기존 비밀번호를 입력해 주세요.");
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
	
	var lastCheck = confirm("정말로 해당 정보로 변경하시겠습니까?");
	
	if(lastCheck) {
		var jsonForm = {
			id:id,
			pw:pw,
			name:name,
			phoNum:phone
		}
	
		$.ajax({
			type:"POST",//요청방식
			url:"infoChange",//요청경로
			data:JSON.stringify(jsonForm),// 서버로 보낼 데이터
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