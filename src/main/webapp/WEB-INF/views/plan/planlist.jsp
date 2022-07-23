<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
	#header-menu li:nth-child(3) > a {
		color: #6DA2DF;
	}

	#city-submenu {
      box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
      width: 1400px;
      height: 50px;
      display: flex;
      justify-content: space-around;
      align-items : center;
      font-size: 1.2rem;
   }
   
   #search-box {
      margin: 0 0 50px 0;
      text-align: center;
   }
   
   #search-box > * {
      display: inline-block;
      margin-right: 10px;
   }
   
   #search-box select {
      width: 100px;
   }
   
   #search-box input[type=text] {
      width: 300px;
   }
   
   #search-box input[type=button] { 
      width: 80px;
   }
   
   .fa-solid {
   		margin-left: 7px;
   }

   
   .fa-solid.fa-heart {
   	  color: tomato;
   }
   
   #list {
   		width: 1000px;
   		margin: 0 auto 50px 0;
   		display: flex;
   		flex-wrap: wrap;
   }
   
   .plan {
   		margin: 10px 20px 10px 10px;
   		text-align: center;
   		cursor: pointer;
   }
   
   
   .plan > img {
   		width: 300px;
   		height: 200px;
   		background-size: contain;
   }
   
	.plan::after {
        content: '';
        display: block;
        clear: both;
    }
   
   #add-plan-btn {
   		float: right;
   		margin-right: 10px;
   }
   
	.plan .text i {
		margin-right: 5px;
	}
	
	#btn-box {
		height: 50px;
		width: 1000px;
	}
	
	.pagination {
   		justify-content: center;
   }
   
   #no-result {
   		text-align: center;
   		margin-bottom: 100px;
   }
   
   #search-box td {
   		padding-right: 10px;
   }
   
</style>
</head>
<body>
	<main>
		<%@ include file="/WEB-INF/views/inc/header.jsp"%>
		<section>
			
			<div id="search-box">
				<form method="GET" action="/planitshare/plan.do">
					<table class="search">
						<tr>
							<td>
								<select name="column" id="column" class="form-control" style="width: 130px;">
									<option value="name">지역</option>
									<option value="title">제목</option>
									<option value="content">내용</option>
									<option value="author">작성자</option>
								</select>
							</td>
							<td>
								<input type="text" name="word" class="form-control" required>
							</td>
							<td>
								<button class="btn btn-secondary">
								<i class="fa-solid fa-magnifying-glass"></i>
								</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
		<div id="btn-box">
			<c:if test="${not empty auth}">
         	<input type="button" class="btn btn-primary" id="add-plan-btn" value="일정 등록" onclick="location.href='/planitshare/plan/add.do';">
         	</c:if>
		</div>
		

		<div id="list">
			 <c:forEach items="${list}" var="dto">
	         <div class="plan" onclick="location.href='/planitshare/plan/view.do?seq=${dto.seq}';">
	            <img src="/planitshare/asset/image/${dto.image}">
	            <div class="text">
	                <div>${dto.title}</div>
	                <i class="fa-solid fa-user"></i><span>${dto.author}</span>
	                <i class="fa-solid fa-eye"></i><span>${dto.readCount}</span>
	                <i class="fa-solid fa-heart"></i><span>${dto.likecnt}</span>
	                <c:if test="${dto.commentcnt > 0}">
	                <span>(${dto.commentcnt})</span>
	                </c:if>
	            </div>
	         </div>
	         </c:forEach>
         </div>
         
          <c:if test="${list.size() == 0}">
			<h4 id="no-result">검색 결과가 없습니다.</h4>
		</c:if>
		
		<div id="page">	
        	${pagebar}
        </div>
	         
		</section>
	</main>
	
	<script>
		<c:if test="${map.isSearch == 'y'}">
		$('select[name=column]').val('${map.column}');
		$('input[name=word]').val('${map.word}');
		</c:if>
		
		$
	</script>

</body>
</html>