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
		width: 100px;
	}
	
	#search-box input[type=text] {
		width: 300px;
	}
	
	#search-box input[type=button] { 
		width: 80px;
	}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main>
		<section>
			<h2>${dto.name}</h2>
			<div id="city-submenu">
				<span><a href="/planitshare/city.do?cseq=${dto.seq}">홈</a></span>
				<span><a href="/planitshare/city/lodge.do?cseq=${dto.seq}">숙소</a></span>
				<span><a href="/planitshare/city/tour.do?cseq=${dto.seq}">관광명소</a></span>
				<span><a href="/planitshare/city/food.do?cseq=${dto.seq}">음식점</a></span>
				<span><a href="/planitshare/city/plan.do?cseq=${dto.seq}">${dto.name}일정</a></span>
			</div>
			
			<div id="search-box">
				<select class="form-control">
					<option>음식점명</option>
					<option>카테고리</option>
				</select>
				<input type="text" name="" id="" class="form-control" placeholder="검색어를 입력해주세요."/>
				<button type="button" class="btn btn-secondary"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			
			
		</section>
	</main>

</body>
</html>