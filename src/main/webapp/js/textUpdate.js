$(document).ready(function(){
	
	var fonts = ['맑은 고딕', '돋움', '궁서', '맑은 고딕', '굴림', '굴림체', '궁서체', '나눔 고딕', '바탕'];
	$('#summernote').summernote({
		height: 500,
		minheight: 500,
		maxheight: 500,
		lang: 'ko-KR',
		fontNames: fonts.sort(),
		toolbar: [
		['Font Style', ['fontname']],
		['fontsize', ['fontsize']],
		['style', ['bold', 'italic', 'underline']],
		['font', ['strikethrough']],
		['color', ['color']],
		['para', ['paragraph']],
		['height', ['height']],
		['Insert', ['picture']],
		['code',['codeview']]
		],
		
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
				for (var i = files.length - 1; i >= 0; i--) {
					sendFile(files[i], this);
				}
			}
		}
	});
	
	textInputTyChFuntion();
});
// 툴바 버튼 클릭시 기능 작동
$('.dropdown-toggle').dropdown();

function sendFile(file, el) {
	var form_data = new FormData();
	form_data.append('file', file);
	
	$.ajax({
		data: form_data,
		type: "POST",
		url: "imgUpload",
		cache: false,
		contentType: false,
		enctype: "multipart/form-data",
		processData: false,
		success: function(img_name) {
			$(el).summernote('editor.insertImage', img_name);
		},
		error : function(error) {
				alert("이미지 저장에 실패하였습니다.");
		}
	});
}

$('#textInputBd').change(function(e){
	textInputTyChFuntion();
});

function textInputTyChFuntion() {
	var textInputBdVal = $('#textInputBd').val();
	$('#textInputType').empty();
	if(textInputBdVal != "0") {
		$('#textInputType').removeAttr('disabled');
		
		var jsonForm = {
			boardCode: textInputBdVal
		}
		
		$.ajax({
			type:"POST",           //요청방식
			url:"selectTypeName",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var typeInfoList = data.typeInfoList;
				
				for(var typeInfoCount = 0; typeInfoCount < typeInfoList.length; typeInfoCount++){
					var typeOption = document.createElement('option');
					var typeOptionText = document.createTextNode(typeInfoList[typeInfoCount].typeName);
					typeOption.setAttribute('value', typeInfoList[typeInfoCount].typeCode);
					if($('#hidTypeName').val().toString() == typeInfoList[typeInfoCount].typeName.toString()) {
						typeOption.setAttribute('selected', "selected");
					}
					typeOption.appendChild(typeOptionText);
					$('#textInputType').append(typeOption);
				}
			},
			error : function(error) {
				$('#textInputType').attr('disabled', 'disabled');
			}
		});
	} else {
		$('#textInputType').attr('disabled', 'disabled');
	}
}

function replaceAll(str, searchStr, replaceStr) {
	return str.split(searchStr).join(replaceStr);
}

function updateFunction() {
	var textNo = $('#hidTextNo').val().toString();
	var textInputBd = $('#textInputBd').val().toString();
	var textInputTy = $('#textInputType').val().toString();
	var textWriteTitle = $('#textWriteTitle').val().toString();
	var textDes = $('#summernote').val().toString();
	var imgFile = $('#textViewDesDiv img');
	var imgAdd = [];
	
	var pattern = /\/img\/userPic\/update\/[a-z0-9]{6,10}\//;
	var saveImgAdd = "\/img\/userPic\/upload\/";
	textDes = replaceAll(textDes, pattern, saveImgAdd);
	
	for(var imgCount = 0; imgCount < imgFile.length; imgCount++) {
		imgAdd.push(imgFile[imgCount].getAttribute('src'));
	}
	
	imgAdd.toString();
	
	if(!stringCheck(textInputBd)) {
		alert("작성할 게시판을 선택해 주세요.");
		return false;
	}
	
	if(!stringCheck(textInputTy)) {
		alert("게시판의 종류를 선택해 주세요.");
		return false;
	}
	
	if(!stringCheck(textWriteTitle)) {
		alert("제목을 입력해 주세요.");
		return false;
	}
	
	if(!stringCheck(textDes)) {
		alert("내용을 입력해 주세요.");
		return false;
	}
	
	var jsonForm = {
			textNo: textNo,
			textInputBd: textInputBd,
			textInputTy: textInputTy,
			textWriteTitle: textWriteTitle,
			textDes: textDes,
			imgAdd: imgAdd
		}
		
		$.ajax({
			type:"POST",           //요청방식
			url:"updateTextUpload",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var result = data.resultFlg.toString();
				var message = data.message.toString();
				
				if(result != "0") {
					alert(message);
					return false;
				} else {
					alert(message);
					document.updateForm.submit();
				}
			},
			error : function(error) {
				alert(message);
				return false;
			}
		});
	
}

function stringCheck(checkString) {
	if(checkString == "0" || checkString.length == 0 || checkString == null || checkString == "" || checkString == "undefined" || (checkString != null && typeof checkString == "object" && !Object.keys(checkString).length) || (checkString.trim() == "")) {
		return false;
	} else {
		return true;
	}
}

function cancelFunction() {
	var imgFile = $('#textViewDesDiv img');
	var imgAdd = [];
	
	for(var imgCount = 0; imgCount < imgFile.length; imgCount++) {
		imgAdd.push(imgFile[imgCount].getAttribute('src'));
	}
	
	imgAdd.toString();
	
	var cancelConfirmMsg = "게시글 수정을 취소하시겠습니까??"
	var cancelCheck = confirm(cancelConfirmMsg);
	
	if(cancelCheck) {
		var jsonForm = {
			imgAdd: imgAdd
		}
			
		$.ajax({
			type:"POST",           //요청방식
			url:"updateTextCancel",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var result = data.resultFlg.toString();
				var message = data.message.toString();
				
				if(result != "0") {
					alert(message);
					return false;
				} else {
					alert(message);
					document.updateForm.submit();
				}
			},
			error : function(error) {
				alert(message);
				return false;
			}
		});
	}	
}
// 페이지 벗어날 경우
$(window).on('unload', function() {
	return pageUnload();
});

function pageUnload() {
	var imgFile = $('#textViewDesDiv img');
	var imgAdd = [];
	
	for(var imgCount = 0; imgCount < imgFile.length; imgCount++) {
		imgAdd.push(imgFile[imgCount].getAttribute('src'));
	}
	
	imgAdd.toString();
	
	var jsonForm = {
			imgAdd: imgAdd
		}
		
		$.ajax({
			type:"POST",           //요청방식
			url:"updateTextCancel",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var result = data.resultFlg.toString();
				var message = data.message.toString();
				
				if(result != "0") {
					alert(message);
					return false;
				} else {
					alert(message);
					return true;
				}
			},
			error : function(error) {
				alert(message);
				return false;
			}
		});
}