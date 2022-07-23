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
	
	h4 {
		text-align: center;
		margin-top: 100px;
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
		width: 700px;
		margin: 30px auto;
	}
	
	.tour-img {
		float: left;
		width: 300px;
		height: 200px;
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
	
	.pagination {
		margin-top: 50px;
		justify-content: center;
	}
	
	.card {
		cursor: pointer;
	}
	
	#tour-info {
		margin-top: 30px;
	}
	
	h1 {
		text-align: center;
	}
	
	#header-menu li:nth-child(2) a {
		color: #6DA2DF
	}
   
</style>
</head>
<body>
   <%@ include file="/WEB-INF/views/inc/header.jsp" %>
   
   <main>
      <section>
   <%@ include file="/WEB-INF/views/inc/citysubmenu.jsp" %>
         
         <form method="GET" action="/planitshare/city/lodgingsearch.do">
         <div id="search-box">
				<select class="form-control" name="distinct">
					<option value="name">숙소명</option>
					<option value="category">카테고리</option>
				</select>
				<input type="text" name="keyword" id="" class="form-control" placeholder="검색어를 입력해주세요."/>
				<button type="button" class="btn btn-secondary" id="search-btn"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			</form>
         
         <c:forEach var="dto" items="${list}">
			<div class="card" onclick="location.href='/planitshare/city/lodging/view.do?cseq=${dto.cseq}&seq=${dto.seq}'">
			  <div class="card-body">
			  	<img src="/planitshare/asset/image/${dto.image}" class="tour-img">
			  	<div id="tour-info">
				    <h5 class="card-title">${dto.name}</h5>
				    <p class="card-text">${dto.category}</p>
				    <p class="card-text">
				    	<i class="fa-solid fa-heart"></i><span>${dto.likecnt}</span>
				    	<i class="fa-solid fa-star"></i><span>${dto.reviewavg eq null ? 0.0 : dto.reviewavg} (${dto.reviewcnt})</span>
			    	</p>
				    <p class="card-text">${dto.address}</p>
			    </div>
			  </div>
			</div>
			</c:forEach>
			
			
			<!-- 페이징 -->
			<c:if test="${not empty list}">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <c:if test="${pagination.prev}">
                        <li class="page-item">
                            <a class="page-link" href="/planitshare/city/lodging.do?cseq=${dto.seq}&page=${pagination.currentPage - 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach begin="${pagination.beginPage}" end="${pagination.endPage}" var="index">
                        <li class="page-item"><a class="page-link" href="/planitshare/city/lodging.do?cseq=${dto.seq}&page=${index}">${index}</a></li>
                    </c:forEach>
                    <c:if test="${pagination.next}">
                        <li class="page-item">
                            <a class="page-link" href="/planitshare/city/lodging.do?cseq=${dto.seq}&page=${pagination.currentPage + 1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
            </c:if>
         
      </section>
   </main>

</body>
</html>