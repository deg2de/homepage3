/**
 * 
 */

function userInfoDeleteFunction(deleteId) {
	var deleteConfirmMsg = "정말로 해당 유저를 탈퇴 시키겠습니까? (복구불가)";
	var deleteCheck = confirm(deleteConfirmMsg);
	
	if(deleteCheck) {
		var jsonForm = {
			id: deleteId
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"deleteuserManage",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var checkFlg = data.result.toString();
				var message = data.message.toString();

				if(checkFlg != "0") {
					alert(message);
					window.location.reload();
					return false;
				} else {
					alert(message);
					window.location.reload();
					return true;
				}
			},
			error : function(error) {
				alert(error);
				return false;
			}
		});
	} else {
		window.location.reload();
	}
}