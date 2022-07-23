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
	
	.food-img {
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
	
	#food-info {
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
	<main>
		<%@ include file="/WEB-INF/views/inc/header.jsp" %>
		<section>
		
			<%@ include file="/WEB-INF/views/inc/citysubmenu.jsp" %>
			
			<form id="search">
			<div id="search-box">
				<select name="keywordtype" class="form-control">
					<option value="placename">관광명소명</option>
					<option value="category">카테고리</option>
				</select>
				<input type="text" name="keyword" class="form-control" />
				<button type="button" name="btn-search" class="btn btn-secondary" onclick="search();"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			<input type="hidden" name="cseq" value="${cseq}">
			</form>
			
		<c:forEach var="dto" items="${list}">
			<div class="card" onclick="location.href='/planitshare/city/tour/view.do?cseq=${dto.cseq}&seq=${dto.seq}'">
			  <div class="card-body">
			  	<img src="/planitshare/asset/image/${dto.image}" class="food-img">
			  	<div id="food-info">
				    <h5 class="card-title">${dto.placeName}</h5>
				    <p class="card-text">${dto.category}</p>
				    <p class="card-text">
				    	<i class="fa-solid fa-heart"></i><span>${dto.likeCnt}</span>
				    	<i class="fa-solid fa-star"></i><span>${dto.reviewAvg eq null ? 0.0 : dto.reviewAvg} (${dto.reviewCnt})</span>
			    	</p>
				    <p class="card-text">${dto.address}</p>
			    </div>
			  </div>
			</div>
			</c:forEach>
		
		<div style="text-align: center">
			${pagebar}
		</div>
		</section>
	</main>
	
	<script type="text/javascript">
	
	// 관광명소 검색
	function search() {
		
		$.ajax({
			type: 'POST',
			url: '/planitshare/city/tourserach.do',
			data: $('#search').serialize(),
			dataType: 'json',
			success: function(result) {
				
				$('.container').html('');
				
				if (result.length > 0) {
					
					$(result).each(function(index, item) {
						
						let temp = '';
						
			            temp += '<div class="col d-flex justify-content-center align-items-center">';
			            temp += '    <div class="card my-2" style="width: 702px;">';
			            temp += '        <div class="row no-gutters">';
			            temp += '            <span><img src="/planitshare/asset/image/'+ item.image +'" class="img-fluid" alt="숙소이미지"></span>';
			            temp += '            <div class="col">';
			            temp += '                <div class="card-block px-4">';
			            temp += '                    <h4 class="card-title"><a href="/planitshare/city/tourview.do?seq='+ item.seq +'&cseq='+ item.cseq +'">'+ item.placeName +'</a></h4>'
			            temp += '                    <div class="card-text">'+ item.category +'</div>';
			            temp += '                    <div class="card-text"><i class="fa-solid fa-heart"></i> ' + item.likeCnt + '<i class="fa-solid fa-star"></i>'+item.reviewAvg+'('+item.reviewCnt+')</div>'
			            temp += '                    <div class="card-text">'+ item.address +'</div>'
			            temp += '                </div>';
			            temp += '            </div>';
			            temp += '        </div>';
			            temp += '    </div>';
			            temp += '</div>';
			        	
						$('.container').append(temp);
						
					});
				} else {
					$('.container').append('<h1>검색결과가 없습니다.</h1>');
				}
				
				
			},
			error: function(result) {
				console.log(result);
			}
			
		});
		
		}	
	$("#pagebar").change(function() {
        
        location.href = '/planitshare/city/tour.do?cseq=${cseq}&page=' + $(this).val();
        
     });
		
		$('#pagebar').val(${nowPage});

		
	</script>
	
</body>
</html>