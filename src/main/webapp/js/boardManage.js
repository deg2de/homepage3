/**
 * 
 */

function addBoardFuction() {
	
	var inputBoardName = document.createElement('input');
	inputBoardName.setAttribute("type", "text");
	inputBoardName.setAttribute("id", "inputBoardName");
	inputBoardName.setAttribute("class", "form-control inputBoardName");
	var boardCreateBtn = document.createElement('button');
	boardCreateBtn.setAttribute("id", "boardCreateBtn");
	boardCreateBtn.setAttribute("class", "boardCreateBtn btn btn-light");
	boardCreateBtn.setAttribute("onclick", "return createBoardFuction()");
	var boardCreateText = document.createTextNode("생성");
	boardCreateBtn.appendChild(boardCreateText);
	
	$('#boardAddBtn').after(boardCreateBtn);
	$('#boardAddBtn').after(inputBoardName);
	$('#boardAddBtn').remove();
	$('#boardDeleteBtn').remove();
	
	
}

function createBoardFuction() {
	
	var inputValue = document.getElementById('inputBoardName').value;
	
	var inputValue_check = /^[a-zA-Z0-9가-힣]{1,30}$/;
/*	var inputValue_check = /^[a-zA-Z0-9가-힣]{1,30}$/;*/
	
	if(inputValue.length == 0 || inputValue == null) {
		alert("게시판 이름을 입력해 주세요. (영문,한글,숫자)");
		document.getElementById('inputBoardName').focus();
		return false;
	}
	
	if(!inputValue_check.test(inputValue)){
		alert("형식에 맞게 입력해 주세요. (영문,한글,숫자)");
		document.getElementById('inputBoardName').focus();
		return false;
	}
	
	var selectBox = document.getElementById('beforeBoard');
	var selectLen = selectBox.length;
	
	for(var size = 0; size < selectLen; size++) {
		if(selectBox.options[size].value == inputValue) {
			alert("이미 존재하는 게시판 이름입니다.");
			document.getElementById('inputBoardName').focus();
			return false;
		}
	}
	
	var createConfirmMsg = "정말로 해당 게시판을 추가 하시겠습니까?";
	var createCheck = confirm(createConfirmMsg);
	
	if(createCheck) {
		var jsonForm = {
			boardName: inputValue
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"createBoard",          //요청경로
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
					/*
					var option = document.createElement('option');
					var inputValueText = document.createTextNode(inputValue);
					option.appendChild(inputValueText);
					
					$('#beforeBoard').append(option);
					
					$('#inputBoardName').remove();
					$('#boardCreateBtn').remove();
					
					var boardAddBtn = document.createElement('button');
					boardAddBtn.setAttribute("id","boardAddBtn");
					boardAddBtn.setAttribute("class","btn btn-light boardBtn");
					boardAddBtn.setAttribute("onclick","addBoardFuction()");
					var boardAddText = document.createTextNode("추가");
					boardAddBtn.appendChild(boardAddText);
					
					var boardDeleteBtn = document.createElement('button');
					boardDeleteBtn.setAttribute("id","boardDeleteBtn");
					boardDeleteBtn.setAttribute("class","btn btn-light boardBtn");
					boardDeleteBtn.setAttribute("onclick","delBoardFuction()");
					var boardDeleteText = document.createTextNode("삭제");
					boardDeleteBtn.appendChild(boardDeleteText);
					
					$('#boardBtnDiv').append(boardAddBtn);
					$('#boardBtnDiv').append(boardDeleteBtn);
					**/
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

function delBoardFuction() {
	
	var clickBoard = $('#beforeBoard option:selected').val();
	var deleteMessage = "정말로 해당 게시판을 삭제 하시겠습니까? 해당 게시판을 삭제하면 게시판 종류까지 모두 삭제됩니다.(복구 불가)";
	
	var deleteCheck = confirm(deleteMessage);
	
	if(checkUndefined(clickBoard)){
		alert("삭제할 게시판을 선택해 주세요.");
		return false;
	}
	
	if(deleteCheck) {
		
		var jsonForm = {
			boardCode: clickBoard
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"deleteBoard",          //요청경로
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
		alert("삭제를 취소하였습니다.");
		return false;
	}
}

function checkUndefined(obj) {
		return obj === void 0;
	};

$('#beforeBoard').on('click', function(event){
	var clickBoard = $('#beforeBoard option:selected').val();
	var boardType = $('#boardType');
	
	var selectBox = document.getElementById('beforeBoard');
	var selectLen = selectBox.length;
	
	boardType.empty();
	
	if(selectLen > 0 && !checkUndefined(clickBoard)) {
		var jsonForm = {
			boardCode: clickBoard
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"selectBoardType",          //요청경로
			dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
			data : JSON.stringify(jsonForm), // 서버로 보낼 데이터
			contentType: "application/json; charset=utf-8;",
			success : function(data) {
				var checkFlg = data.result.toString();
				var message = data.message.toString();
				var board = data.board;
				var boardName = JSON.parse(board).boardName;
				var boardTypeList = data.boardTypeList;

				if(checkFlg != "0") {
					alert(message);
					window.location.reload();
					return false;
				} else {
					if(boardTypeList.length != 0) {
						for(var boardTypeCount = 0; boardTypeCount < boardTypeList.length; boardTypeCount++) {
							var boardTypeOption = document.createElement('option');
							boardTypeOption.setAttribute("value",boardTypeList[boardTypeCount].typeCode);
							var boardTypeName = document.createTextNode(boardTypeList[boardTypeCount].typeName);
							boardTypeOption.appendChild(boardTypeName);
							
							boardType.append(boardTypeOption);
						}
						$('#boardTypeAddBtn').removeAttr('disabled');
						$('#boardTypeDeleteBtn').removeAttr('disabled');
					}
				}
				$('#boardTypeLabel').empty();
				$('#boardTypeLabel').prepend("「" + boardName + "」" +"게시판 종류");
			},
			error : function(error) {
				alert(error);
				return false;
			}
		});
	} else {
		$('#boardTypeLabel').empty();
		$('#boardTypeLabel').prepend("게시판 종류");
	}
})

function addBoardTypeFuction() {
	
	var inputBoardTypeName = document.createElement('input');
	inputBoardTypeName.setAttribute("type", "text");
	inputBoardTypeName.setAttribute("id", "inputBoardTypeName");
	inputBoardTypeName.setAttribute("class", "form-control inputBoardTypeName");
	var boardTypeCreateBtn = document.createElement('button');
	boardTypeCreateBtn.setAttribute("id", "boardTypeCreateBtn");
	boardTypeCreateBtn.setAttribute("class", "boardTypeCreateBtn btn btn-light");
	boardTypeCreateBtn.setAttribute("onclick", "return createBoardTypeFuction()");
	var boardTypeCreateText = document.createTextNode("생성");
	boardTypeCreateBtn.appendChild(boardTypeCreateText);
	
	$('#boardTypeAddBtn').after(boardTypeCreateBtn);
	$('#boardTypeAddBtn').after(inputBoardTypeName);
	$('#boardTypeAddBtn').remove();
	$('#boardTypeDeleteBtn').remove();
	
	
}

function createBoardTypeFuction() {
	
	var clickBoard = $('#beforeBoard option:selected').val();
	
	var inputValue = document.getElementById('inputBoardTypeName').value;
	
	var inputValue_check = /^[a-zA-Z0-9가-힣]{1,30}$/;
	
	if(inputValue.length == 0 || inputValue == null) {
		alert("종류 이름을 입력해 주세요. (영문,제한된 특수문자 혹은 한글)");
		document.getElementById('inputBoardTypeName').focus();
		return false;
	}
	
	if(!inputValue_check.test(inputValue)){
		alert("형식에 맞게 입력해 주세요. (영문,제한된 특수문자 혹은 한글)");
		document.getElementById('inputBoardTypeName').focus();
		return false;
	}
	
	var selectBox = document.getElementById('boardType');
	var selectLen = selectBox.length;
	
	for(var size = 0; size < selectLen; size++) {
		if(selectBox.options[size].value == inputValue) {
			alert("이미 존재하는 게시판 종류 이름입니다.");
			document.getElementById('inputBoardTypeName').focus();
			return false;
		}
	}
	
	var createConfirmMsg = "정말로 해당 게시판 종류를 추가 하시겠습니까?";
	var createCheck = confirm(createConfirmMsg);
	
	if(createCheck && !checkUndefined(clickBoard)) {
		var jsonForm = {
			boardCode: clickBoard,
			boardTypeName: inputValue
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"createBoardType",          //요청경로
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

function delBoardTypeFuction() {
	
	var clickBoardType = $('#boardType option:selected').val();
	var deleteMessage = "정말로 해당 종류를 삭제 하시겠습니까? (복구 불가)";
	
	var deleteCheck = confirm(deleteMessage);
	
	if(checkUndefined(clickBoardType)){
		alert("삭제할 게시판 종류를 선택해 주세요.");
		return false;
	}
	
	if(deleteCheck) {
		
		var jsonForm = {
			boardTypeCode: clickBoardType
		}

		$.ajax({
			type:"POST",           //요청방식
			url:"deleteBoardType",          //요청경로
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
		alert("삭제를 취소하였습니다.");
		return false;
	}
}