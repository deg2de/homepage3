/**
 * 
 */

$(document).ready(function(){

	$.ajax({
		type:"POST",           //요청방식
		url:"headerBoard",          //요청경로
		dataType:"JSON",     //서버가 어떤형식인지(xml,json,script,html)
		contentType: "application/json; charset=utf-8;",
		success : function(data) {
			var boardList = data.boardList;
			var boardName = data.boardName;
			
			var mainUl = document.getElementById('board_main_menu_ul');
			
			
			for(var boardNameCount = 0; boardNameCount < boardName.length; boardNameCount++) {
				var boardMainMenu = document.createElement('li');
				boardMainMenu.setAttribute('class', 'board_main_menu');
				
				var boardNameDiv = document.createElement('div');
				boardNameDiv.setAttribute('id', boardName[boardNameCount].toString());
				boardNameDiv.setAttribute('class', 'text-dark board_main_menu_div');
				boardNameDiv.setAttribute('onmousedown', 'boardSearchFunction('+ boardName[boardNameCount].toString() +')');
				var boardNameDivText = document.createTextNode(boardName[boardNameCount].toString());
				boardNameDiv.appendChild(boardNameDivText);
				
				var boardNameLastUl = document.createElement('ul');
				boardNameLastUl.setAttribute('class', 'boardNameLastUl');
				
				for(var boardListCount = 0; boardListCount < boardList[boardName[boardNameCount]].length; boardListCount++) {
					var subLi = document.createElement('li');
					subLi.setAttribute('id', boardList[boardName[boardNameCount]][boardListCount].toString());
					subLi.setAttribute('class', 'board_sub_li');
					subLi.setAttribute('onmousedown', 'bdTpSearchFunction('+ boardList[boardName[boardNameCount]][boardListCount].toString() +')');
					var subLiText = document.createTextNode(boardList[boardName[boardNameCount]][boardListCount].toString());
					subLi.appendChild(subLiText);
					
					boardNameLastUl.appendChild(subLi);
				}
				
				boardMainMenu.appendChild(boardNameDiv);
				boardMainMenu.appendChild(boardNameLastUl);
				
				
				mainUl.appendChild(boardMainMenu);
			}
		},
		error : function(error) {
			alert(error);
			return false;
		}
	});
})

function boardSearchFunction(e) {
	$('#hidSearchObject').attr('value', 'searchBoard');
	$('#hidSearchText').attr('value', e.getAttribute('id').toString());
	document.searchForm.submit();
}

function bdTpSearchFunction(e) {
	var boardName = e.parentNode.previousSibling.getAttribute('id').toString();
	var typeName = e.getAttribute('id').toString();
	var searchTextBdTp = boardName + '_' + typeName;
	$('#hidSearchObject').attr('value', 'searchBdTp');
	$('#hidSearchText').attr('value', searchTextBdTp);
	document.searchForm.submit();
}

$('.board_main_menu_ul').on('mouseover', function(e){
	if(e.target.id != "board_main_menu_ul") {
		e.target.setAttribute('style', 'background-color: gainsboro; cursor: pointer');
	}
})

$('.board_main_menu_ul').on('mouseout', function(e){
	if(e.target.id != "board_main_menu_ul") {
		e.target.setAttribute('style', 'background-color: white');
	}
})

$('.board_main_menu_ul').on('mousedown', function(e){
	if(e.target.id != "board_main_menu_ul") {
		e.target.setAttribute('style', 'background-color: white');
	}
})


$('#header_logo').on('mouseover', function(e){
	$('#header_logo').css('color', 'red');
	$('#header_logo').css('cursor', 'pointer');
})

$('#header_logo').on('mouseout', function(e){
	$('#header_logo').css('color', 'white');
	$('#header_logo').css('cursor', 'pointer');
})

$('#header_logo').on('mousedown', function(e){
	$('#header_logo').css('color', 'red');
	$('#header_logo').css('cursor', 'pointer');
	
	location.href = 'main';
})

$('#login_pic').on('mouseover', function(event){
	$('.login_pic').css('border', '3px solid red');
})
$('#login_pic').on('mouseout', function(event){
	$('.login_pic').css('border', '3px solid white');
})
// 이미지 클릭시 메인페이지로 이동한다.
$('#login_pic').on('mousedown', function(event){
	window.open("userPic", "사용자 이미지 설정", "width=500px, height=500px, location=0");
})