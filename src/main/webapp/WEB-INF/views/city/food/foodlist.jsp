<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<style>
	#city-submenu {
		box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
		width: 1400px;
		height: 50px;
		display: flex;
		justify-content: space-around;
		align-items : center;
		font-size: 1.2rem;
	}
	
	h2 {
		text-align: center;
		margin-bottom: 50px;
	}
	
	#search-box {
		margin: 50px 0;
		text-align: center;
	}
	
	#search-box > * {
		display: inline-block;
		margin-right: 10px;
	}
	
	#search-box select {
		width: 120px;
	}
	
	#search-box input[type=text] {
		width: 300px;
	}
	
	#search-box input[type=button] { 
		width: 80px;
	}
	
	.card {
		width: 1000px;
		height: 180px;
		margin: 30px auto;
	}
	
	.food-img {
		float: left;
		width: 200px;
		margin-right: 20px;
	}

	.card-title, .card-text {
		margin : 5px 0;
	}
	
	.card-text span {
		margin-right: 10px;
	}
	
	.card-text i {
		margin-right: 5px;
	}
	
	i.fa-solid.fa-heart {
		color: tomato;
	}
	
	i.fa-solid.fa-star {
		color: gold;
	}
</style>
</head>
<body>
	<main>
		<%@ include file="/WEB-INF/views/inc/header.jsp" %>
		<section>
		
			<h2>제주</h2>
			<div id="city-submenu">
				<span><a href="">홈</a></span>
				<span><a href="">숙소</a></span>
				<span><a href="">관광명소</a></span>
				<span><a href="">음식점</a></span>
				<span><a href="">제주일정</a></span>
			</div>
			
			<div id="search-box">
				<select class="form-control">
					<option>음식점명</option>
					<option>카테고리</option>
				</select>
				<input type="text" name="" id="" class="form-control" />
				<button type="button" class="btn btn-secondary"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			
			<c:forEach var="dto" items="${list}">
			<div class="card">
			  <div class="card-body">
			  	<img src="/planitshare/asset/image/제주.jpg" class="food-img">
			    <h5 class="card-title"><a href="/planitshare/city/food/detail.do?cseq=${dto.cseq}&seq=${dto.seq}">${dto.name}</a></h5>
			    <p class="card-text">${dto.category}</p>
			    <p class="card-text"><i class="fa-solid fa-heart"></i><span>1234</span><i class="fa-solid fa-star"></i><span>4.4 (2392)</span></p>
			    <p class="card-text">${dto.address}</p>
			  </div>
			</div>
			</c:forEach>
			
		</section>
	</main>
	
</body>
</html>