/**
 * 
 */

// 페이지가 로드 될 경우 실행할 처리
$(document).ready(function(){
//	var imgs = document.getElementsByTagName('img');
//	var parentsDiv;
//	for(var count = 0; count < imgs.length; count++){
//		if(imgs[count].getAttribute('id') == "programmingread_text_block_img"){
//			imgs[count].remove();
//			parentsDiv[count] = imgs[count].parentElement;
//		}
//	}
//	$("").replaceAll('.textTable_body_textArea div');

	var texts = $("#textViewDesDiv img");
	var pattern = /\/\update\/[a-z0-9]{6,10}\//;
	var saveImgAdd = "\/upload\/";

	if(texts.length > 0) {
		for(var count = 0; count < texts.length; count++){
			var changeAdd = texts[count].getAttribute('src').toString().replace(pattern, saveImgAdd);
			texts[count].setAttribute('src', changeAdd);
		}
	}
	
	$('#commentViewBtn').remove();
	$('#commentViewDiv').remove();
	
	var textNo = document.getElementById('textViewNo').childNodes[0].data.toString();
	
	var jsonForm = {
		textNo: textNo
	}

	$.ajax({
		type:"POST",           //요청방식
		url:"firstCommentDes",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var afterCmtFlg = data.afterCmtFlg.toString();
			
			var commentInfoList = JSON.parse(data.commentInfoList);
			
			var commentCount = commentInfoList.length;
			if(commentCount > 0) {
				var commentViewDiv = document.createElement('div');
				commentViewDiv.setAttribute('id', 'commentViewDiv');
				$('#textView').append(commentViewDiv);
				
				for(var commentInfoCount = 0; commentInfoCount < commentInfoList.length; commentInfoCount++){
					commentInfoFunction(commentInfoList[commentInfoCount]);
				}
				
				if(afterCmtFlg == "0") {
					var commentViewBtn = document.createElement('button');
					commentViewBtn.setAttribute('id', 'commentViewBtn');
					commentViewBtn.setAttribute('class', 'btn btn-outline-secondary');
					commentViewBtn.setAttribute('onclick', 'moreFunction('+ textNo +', 2)');
					commentViewBtn.setAttribute('style', 'width:860px; height:30px; padding: 0; margin-left: 10px; margin-right: 10px; border: 1px solid lightgray; font-size: 13px;');
					var commentViewBtnText = document.createTextNode('댓글 더 보기');
					commentViewBtn.append(commentViewBtnText);
					$('#textView').append(commentViewBtn);
				}
			}
			return true;
		},
		error : function(error) {
			alert(error);
			return false;
		}
	});
});

$('.textInfoListTableBody').on('mouseover', function(e){
	e.target.parentNode.setAttribute('style', 'background-color: gainsboro; cursor: pointer');
})

$('.textInfoListTableBody').on('mouseout', function(e){
	e.target.parentNode.setAttribute('style', 'background-color: white');
})

$('.textInfoListTableBody').on('mouseup', function(e){
	e.target.parentNode.setAttribute('style', 'background-color: gainsboro; cursor: pointer');
})

$('.textInfoListTableBody').on('mousedown', function(e){
	e.target.parentNode.setAttribute('style', 'background-color: gray; cursor: pointer');
	
	$('#commentViewBtn').remove();
	$('#commentViewDiv').remove();
	var textNo = e.target.parentNode.childNodes[1].childNodes[0].data.toString();
	
	var jsonForm = {
			textNo: textNo
		}
		
	$.ajax({
		type:"POST",           //요청방식
		url:"readTextDes",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var afterCmtFlg = data.afterCmtFlg.toString();
			var textDesInfo = data.textDesInfo;
			textViewDesFunction(textDesInfo);
			
			var commentInfoList = JSON.parse(data.commentInfoList);
			
			var commentCount = JSON.parse(textDesInfo).commentCount;
			if(commentCount > 0) {
				var commentViewDiv = document.createElement('div');
				commentViewDiv.setAttribute('id', 'commentViewDiv');
				$('#textView').append(commentViewDiv);
				
				for(var commentInfoCount = 0; commentInfoCount < commentInfoList.length; commentInfoCount++){
					commentInfoFunction(commentInfoList[commentInfoCount]);
				}
				
				if(afterCmtFlg == "0") {
					var commentViewBtn = document.createElement('button');
					commentViewBtn.setAttribute('id', 'commentViewBtn');
					commentViewBtn.setAttribute('class', 'btn btn-outline-secondary');
					commentViewBtn.setAttribute('onclick', 'moreFunction('+ textNo +', 2)');
					commentViewBtn.setAttribute('style', 'width:860px; height:30px; padding: 0; margin-left: 10px; margin-right: 10px; border: 1px solid lightgray; font-size: 13px;');
					var commentViewBtnText = document.createTextNode('댓글 더 보기');
					commentViewBtn.append(commentViewBtnText);
					$('#textView').append(commentViewBtn);
				}
			}
			return true;
		},
		error : function(error) {
			return false;
		}
	});
})

function moreFunction(textNo, commentPage) {
	
	$('#commentViewBtn').remove();
	$('#commentViewDiv').remove();
	
	var jsonForm = {
		textNo: textNo,
		commentPage: commentPage
	}

	$.ajax({
		type:"POST",           //요청방식
		url:"readCommentDes",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var afterCmtFlg = data.afterCmtFlg.toString();
			
			var commentInfoList = JSON.parse(data.commentInfoList);
			
			var commentCount = commentInfoList.length;
			if(commentCount > 0) {
				var commentViewDiv = document.createElement('div');
				commentViewDiv.setAttribute('id', 'commentViewDiv');
				$('#textView').append(commentViewDiv);
				
				for(var commentInfoCount = 0; commentInfoCount < commentInfoList.length; commentInfoCount++){
					commentInfoFunction(commentInfoList[commentInfoCount]);
				}
				
				if(afterCmtFlg == "0") {
					commentPage = commentPage + 1;
					var commentViewBtn = document.createElement('button');
					commentViewBtn.setAttribute('id', 'commentViewBtn');
					commentViewBtn.setAttribute('class', 'btn btn-outline-secondary');
					commentViewBtn.setAttribute('onclick', 'moreFunction('+ textNo +', '+ commentPage +')');
					commentViewBtn.setAttribute('style', 'width:860px; height:30px; padding: 0; margin-left: 10px; margin-right: 10px; border: 1px solid lightgray; font-size: 13px;');
					var commentViewBtnText = document.createTextNode('댓글 더 보기');
					commentViewBtn.append(commentViewBtnText);
					$('#textView').append(commentViewBtn);
				}
			}
			return true;
		},
		error : function(error) {
			alert(error);
			return false;
		}
	});
}

function cmtDeleteFunction(commentNo) {
	var jsonForm = {
		commentNo: commentNo
	}

	$.ajax({
		type:"POST",           //요청방식
		url:"deleteComment",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var checkFlg = data.resultFlg.toString();
			var message = data.message.toString();

			if(checkFlg != "0") {
				alert(message);
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
}

function textViewDesFunction(textDesInfo) {
	var boardName = JSON.parse(textDesInfo).boardName;
	var typeName = JSON.parse(textDesInfo).typeName;
	var textTitle = JSON.parse(textDesInfo).textTitle;
	var textNo = JSON.parse(textDesInfo).textNo;
	var textWriter = JSON.parse(textDesInfo).textWriter;
	var textCount = JSON.parse(textDesInfo).textCount;
	var textWriteDate = JSON.parse(textDesInfo).textWriteDate;
	var textUpdateDate = JSON.parse(textDesInfo).textUpdateDate;
	var commentCount = JSON.parse(textDesInfo).commentCount;
	var textDes = JSON.parse(textDesInfo).textDes;
	
	var textViewBoardNameType = document.createTextNode("[" + boardName + "]-[" + typeName + "]");
	var textViewTitle = document.createTextNode(textTitle);
	var textViewNo = document.createTextNode(textNo);
	var textViewWriter = document.createTextNode(textWriter);
	var textViewCount = document.createTextNode("조회수 " + textCount);
	var textViewWriteDate = document.createTextNode("작성일 " + textWriteDate);
	var textViewLastUpDateDate = document.createTextNode("수정일 " + textUpdateDate);
	var textViewCommentCount = document.createTextNode("댓글 " + commentCount);
	
	$('#textViewBoardNameType').empty();
	$('#textViewTitleDiv').empty();
	$('#textViewNo').empty();
	$('#textViewWriter').empty();
	$('#textViewCount').empty();
	$('#textViewWriteDate').empty();
	$('#textViewLastUpDateDate').empty();
	$('#textViewCommentCount').empty();
	$('#textViewDesDiv').empty();
	
	$('#textViewTitleDiv').removeAttr('title');
	$('#textViewTitleDiv').attr('title', textTitle);
	
	$('#textViewBoardNameType').append(textViewBoardNameType);
	$('#textViewTitleDiv').append(textViewTitle);
	$('#textViewNo').append(textViewNo);
	$('#textViewWriter').append(textViewWriter);
	$('#textViewCount').append(textViewCount);
	$('#textViewWriteDate').append(textViewWriteDate);
	$('#textViewLastUpDateDate').append(textViewLastUpDateDate);
	$('#textViewCommentCount').append(textViewCommentCount);
	$('#textViewDesDiv').append(textDes);
	
	var texts = $("#textViewDesDiv img");
	var pattern = /\/\update\/[a-z0-9]{6,10}\//;
	var saveImgAdd = "\/upload\/";

	if(texts.length > 0) {
		for(var count = 0; count < texts.length; count++){
			var changeAdd = texts[count].getAttribute('src').toString().replace(pattern, saveImgAdd);
			texts[count].setAttribute('src', changeAdd);
		}
	}
}

function commentInfoFunction(commentInfo) {
	var commentNo = commentInfo.commentNo;
	var commentTextNo = commentInfo.commentTextNo;
	var commentId = commentInfo.commentId;
	var commentDes = commentInfo.commentDes;
	var commentWriteDate = commentInfo.commentWriteDate;
	var commentWriteFlg = commentInfo.commentIdFlg;
	
	var commentViewTextDiv = document.createElement('div');
	commentViewTextDiv.setAttribute('class', 'card commentViewTextDiv');
	var commentNoHid = document.createElement('input');
	commentNoHid.setAttribute('type', 'hidden');
	commentNoHid.setAttribute('id', 'commentNo');
	commentNoHid.setAttribute('value', commentNo);
	var commentTextNoHid = document.createElement('input');
	commentTextNoHid.setAttribute('type', 'hidden');
	commentTextNoHid.setAttribute('id', 'commentTextNo');
	commentTextNoHid.setAttribute('value', commentTextNo);
	var commentCardBody = document.createElement('div');
	commentCardBody.setAttribute('class', 'card-body');
	commentCardBody.setAttribute('style', 'background-color: whitesmoke;');
	
	var commentViewTitleDiv = document.createElement('div');
	commentViewTitleDiv.setAttribute('class', 'commentViewTitleDiv');
	var commentViewTitleId = document.createElement('div');
	commentViewTitleId.setAttribute('class', 'card-subtitle h6 commentViewTitleId');
	var commentViewTitleIdDiv = document.createElement('div');
	commentViewTitleIdDiv.setAttribute('class', 'commentViewTitleIdDiv');
	var commentIdText = document.createTextNode(commentId);
	commentViewTitleIdDiv.append(commentIdText);
	var commentViewTitleBtnDiv = document.createElement('div');
	commentViewTitleBtnDiv.setAttribute('class', 'commentViewTitleBtnDiv');
	var commentViewTitleBtn = document.createElement('button');
	commentViewTitleBtn.setAttribute('class', 'commentViewTitleBtn btn-outline-secondary');
	commentViewTitleBtn.setAttribute('onclick', 'cmtDeleteFunction('+ commentNo +')');
	var commentBtnText = document.createTextNode('x');
	commentViewTitleBtn.append(commentBtnText);
	var commentViewTitleDt = document.createElement('div');
	commentViewTitleDt.setAttribute('class', 'text-muted h7 commentViewTitleDt');
	var commentWriteDateText = document.createTextNode(commentWriteDate);
	commentViewTitleDt.append(commentWriteDateText);
	if(commentWriteFlg == 0) {
		commentViewTitleBtnDiv.append(commentViewTitleBtn);
	}
	commentViewTitleId.append(commentViewTitleIdDiv);
	commentViewTitleId.append(commentViewTitleBtnDiv);
	commentViewTitleDiv.append(commentViewTitleId);
	commentViewTitleDiv.append(commentViewTitleDt);
	
	var commentViewDesDiv = document.createElement('div');
	commentViewDesDiv.setAttribute('class', 'commentViewDesDiv');
	var commentCardText = document.createElement('p');
	commentCardText.setAttribute('class', 'card-text');
	var commentDesText = document.createTextNode(commentDes);
	commentCardText.append(commentDesText);
	commentViewDesDiv.append(commentCardText);
	
	commentCardBody.append(commentViewTitleDiv);
	commentCardBody.append(commentViewDesDiv);
	commentViewTextDiv.append(commentNoHid);
	commentViewTextDiv.append(commentTextNoHid);
	commentViewTextDiv.append(commentCardBody);
	$('#commentViewDiv').append(commentViewTextDiv);
}

function textViewDeleteFunction() {
	var textNo = document.getElementById('textViewNo').childNodes[0].data.toString();
	var textWriter = document.getElementById('textViewWriter').childNodes[0].data.toString();
	
	var deleteConfirmMsg = "정말로 해당 게시글을 삭제하시겠습니까?"
	var deleteCheck = confirm(deleteConfirmMsg);
	
	if(deleteCheck) {
		var jsonForm = {
			textNo: textNo,
			textWriter: textWriter
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"textDelete",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var checkFlg = data.resultFlg.toString();
				var message = data.message.toString();

				if(checkFlg != "0") {
					alert(message);
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
	}
}

function textViewUpdateFunction() {
	var textNo = document.getElementById('textViewNo').childNodes[0].data.toString();
	var textWriter = document.getElementById('textViewWriter').childNodes[0].data.toString();
	
	var textNoHid = document.createElement('input');
	textNoHid.setAttribute('type', 'hidden');
	textNoHid.setAttribute('name', 'textNo');
	textNoHid.setAttribute('value', textNo);
	var textWriterHid = document.createElement('input');
	textWriterHid.setAttribute('type', 'hidden');
	textWriterHid.setAttribute('name', 'textWriter');
	textWriterHid.setAttribute('value', textWriter);
	
	$('#updateForm').append(textNoHid);
	$('#updateForm').append(textWriterHid);
	
	document.updateForm.submit();
}

function cmtWriteFunction() {
	var textNo = document.getElementById('textViewNo').childNodes[0].data.toString();
	var textArea = document.getElementById('commentWriteTA').value;
	
	// 줄바꿈 처리는 띄어쓰기로 변경(줄바꿈 불가처리)
	textArea = textArea.replace(/\n/gi, " ");
	
	// 미입력 확인 처리
	if(textArea.length == 0 || textArea == null) {
		alert("댓글을 입력해주세요.");
		return false;
	}
	// 입력 자릿수 제한 확인 처리
	if(textArea.length > 500) {
		alert("500자 이내로 입력해주세요.");
		return false;
	}
	
	var jsonForm = {
			textNo: textNo,
			textArea: textArea
		}

	$.ajax({
		type:"POST",           //요청방식
		url:"writeComment",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var checkFlg = data.resultFlg.toString();
			var message = data.message.toString();

			if(checkFlg != "0") {
				alert(message);
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
}