/**
 * 
 */
$('#fileUpload').change(function(e){
	$('#user_pic').attr("src", URL.createObjectURL(e.target.files[0]));
});

function changeImgFunction() {
	
	var formData = new FormData();
	formData.append('upload', $("#fileUpload")[0].files[0]);

	$.ajax({
		type:"POST",//요청방식
		url:"userPic",//요청경로
		data:formData,// 서버로 보낼 데이터
		contentType:false,// 서버로 전송하는 데이터 형식
		processData:false,
		dataType:"json",// 서버에서 받아오는 데이터 형식(xml,json,script,html)
		success : function(data) {
			var message = data.message.toString();
			var checkFlg = data.result;
			if(checkFlg != 0) {
				alert(message);
			} else {
				window.opener.location.href = window.opener.document.URL;
				window.close();
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