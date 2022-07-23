<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<style>
	section {
      display: flex;
   }

   #admin-submenu {
      display:inline-block;
      width: 200px;
      margin-right: 50px;
      margin-top: 100px;

   }
   
   #admin-submenu li {
      list-style: none;
      margin-bottom: 15px;
      font-size: 23px;
      font-weight: bold;
   }
   
   h3 {
   		text-align: center;
   		margin-bottom: 10px;
   }
   
   #each {
   	  width:1000px;
   	  float: left;
   }
   
   #sub {
   	  float:left;
   }
   
   .board{

   	  margin-top: 100px;
   	  margin-bottom: 50px;
   	  width: 1000px;
   }
   
   #page {
   	  width: 1000px;
   	  display:inline-block;
   	  padding-left: 450px;
   	  text-align: center;
   	  
   }
   
   #banadd {
   		width: 300px;
   		display: inline-block;
   		margin-bottom: 10px;
   }
   
   #addbox {
   		float: right;
   }
   
   #admin-submenu li:nth-child(5) a { color : #6DA2DF; }
   
   .table th {
   		text-align: center;
   }
   
   .table th:first-child {
   		width: 80px;
   }
   
</style>
</head>
<body>
	
	
	<main>
		<%@ include file="/WEB-INF/views/inc/header.jsp" %>
		
		<section>
			
			<div id="sub">
			<%@ include file="/WEB-INF/views/inc/adminsubmenu.jsp" %>
         	</div>
         		<div id="each">
         		<div class="board">
	         		<div>
	         		<form method="GET" action="/planitshare/admin/bannedwordadd.do">
					<h3 class="ins">금칙어 등록</h3>
					<span id="addbox">
					<input type="text" name="word" id="banadd" class="form-control"/>
					<button class="btn btn-secondary">등록</button>
					</span>
					
					</form>
					</div>
					
				<form method="GET" action="/planitshare/admin/bannedwordlist.do" id="multidelete">
				<table class="table table-bordered" id="add-word">
				<tr>
					<th><input type="checkbox" name="all" id="cbox"/></th>
					<th style="text-align: center;">금지어명</th>
				</tr>
				<c:forEach items="${list}" var="dto" varStatus="status">
				<tr>
					<td id="cbox"  style="text-align: center;"><input type="checkbox" class="chk1" name="chk" value="${list[status.index].seq}"/></td>
					<td id="word">${dto.word}</td>
				</tr>
				</c:forEach>
				<c:if test="${list.size() == 0}">
				<tr>
					<td colspan="2" style="text-align: center;">등록된 금지어가 없습니다.</td>
				</tr>
				</c:if>
				</table>
				<button class="btn btn-danger" style="float:right;">삭제</button>
				</form>
				</div>
				
				<div id="page">
					${pagebar}
				</div>
			
				<div class="board">
				<div id="title">
					<h3 class="ins">금칙어 게시글</h3>
				</div>
				
				<form method="GET" action="/planitshare/admin/bannedwordlist.do" id="multideleteplan">
				<table class="table table-bordered">
					<tr>
						<th><input type="checkbox" name="all" id="cbox1"/></th>
						<th>작성글 제목</th>
						<th>금칙어</th>
						<th>작성자</th>
						<th>등록일</th>
					</tr>
					<c:forEach items="${plist}" var="dto" varStatus="status">
					<tr>
						<td id="cbox1"><input type="checkbox" name="chk1" value="${dto.seq}"/></td>
						<td id="word1">${dto.title}</td>
						<td id="word1">${dto.banned}</td>
						<td id="word1">${dto.author}</td>
						<td id="word1">${dto.regdate}</td>
					</tr>
					</c:forEach>
					<c:if test="${plist.size() == 0}">
					<tr>
						<td colspan="5" style="text-align: center;">금지어로 등록된 게시글이 없습니다.</td>
					</tr>
					</c:if>
				</table>
				
					<button class="btn btn-danger" style="float:right;">삭제</button>
				</form>
				</div>
				</div>
		
			
		</section>
	
	</main>


	<script>
	$("#pagebar").change(function() {
		
		location.href = '/toy/board/list.do?page=' + $(this).val() + "&column=${map.column}&word=${map.word}";
		
	});
	
	$('#pagebar').val(${nowPage});
	
	$("#cbox").click(function() { //전체 선택 + 선택 해제
		
		var checked = $(this).prop("checked");
		
		if (checked) {
			$(":checkbox[name=chk]").prop("checked", true);
		} else {
			$(":checkbox[name=chk]").prop("checked", false)				
		}
		
	});
	
	$("#cbox1").click(function() { //전체 선택 + 선택 해제
		
		var checked = $(this).prop("checked");
		
		if (checked) {
			$(":checkbox[name=chk1]").prop("checked", true);
		} else {
			$(":checkbox[name=chk1]").prop("checked", false)				
		}
		
	});
	
	  
	
	
	
	
	</script>


</body>
</html>