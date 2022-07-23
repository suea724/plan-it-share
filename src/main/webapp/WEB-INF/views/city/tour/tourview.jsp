<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<style>

	#header-menu li:nth-child(2) a{
		color : #6DA2DF;
	}

	.fa-heart {
		color: tomato;
	}
	
	.fa-star {
		color: gold;
	}
	
	textarea {
		resize: none;
	}

	#like {
	<c:if test="${state!=2}">
      color: black;
    </c:if>
      font-size: 2rem;
   }
   
    #info-box span {
		margin-right: 10px;
	}
	
	#info-box i {
		margin-right: 5px;
	}
	
	#info-box {
		width: 800px;
		height: 200px;
		margin: 0 auto 100px auto;
	}

	#info-box img {
		width: 400px;
		height: 250px;
		float: left;
		margin-right: 20px;
	}
	
	#likeform button {
		outline: none;
		float: right;
		background-color: white;
		border: none;
	}
   
</style>
<link rel="stylesheet" href="/planitshare/asset/css/rateit.css" />
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main>
		<section>
		
		<div id="info-box">
			<table>
				<tr>
					<td><img src="/planitshare/asset/image/${dto.image}"></td>
					<td>
						<h5>${dto.placeName}</h5>
						<p>${dto.category}</p>
						<p>${dto.address}</p>
						<p><span>영업시작시간: ${dto.open}</span> <span>영업종료시간: ${dto.close}</span></p>
						<p>
					    	<i class="fa-solid fa-heart likeCnt"></i><span id="likeCnt">${dto.likeCnt}</span>
					    	<i class="fa-solid fa-star reviewCnt"></i><span>${dto.reviewAvg eq null ? 0.0 : dto.reviewAvg} (${dto.reviewCnt})</span>
				    	</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<c:if test="${auth.loginmode == 'user'}">
						<form id="likeform">
				         	<button type="button" class="btn float-right" onclick="like(${dto.seq});"><i id="like" class="fa-solid fa-heart"></i></button> 
				         </form>
						</c:if>							
					</td>
				</tr>
			</table>
		</div>
		
		
		<%-- 	<div class="container">
			<div class="col d-flex justify-content-center align-items-center">
	            <div class="card my-2" style="width: 702px; border-color: white;">
	                <div class="row no-gutters">
	                    <img src="/planitshare/asset/image/${dto.image}" class="img-fluid" alt="숙소이미지">
	                    <div class="col">
	                        <div class="card-block px-4">
	                            <h4 class="card-title"><a href="/planitshare/city/tourview.do?seq=${dto.seq}&cseq=${dto.cseq}">${dto.placeName}</a></h4>
	                            <div class="card-text">${dto.category}</div>	                            
	                            <div class="card-text">${dto.address}</div>
	                            <div class="card-text">영업시작시간: ${dto.open} 영업종료시간: ${dto.close}</div>
	                            <p>
							    	<i class="fa-solid fa-heart likeCnt"></i><span id="likeCnt">${dto.likeCnt}</span>
							    	<i class="fa-solid fa-star reviewCnt"></i><span>${dto.reviewAvg eq null ? 0.0 : dto.reviewAvg} (${dto.reviewCnt})</span>
						    	</p>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        
	       </div> --%>
	       
	       <!-- 리뷰 등록 -->
	       <form method="POST" action="/planitshare/city/tourreviewadd.do" enctype="multipart/form-data">
			<table class="table table-borderless" style="margin-top: 50px;">
				<tr>
					<td>
						<div class="rateit" data-rateit-resetable="false" data-rateit-mode="font" data-rateit-icon="" style="font-family:fontawesome"></div>
						<input type="file" name="attach">
					</td>
				</tr>
				<tr>
					<td><textarea class="form-control" name="content" rows="5" required placeholder="리뷰를 입력해주세요"></textarea></td>
					<td style="vertical-align: middle;">
						<input type="submit" value="리뷰등록" class="btn btn-secondary">
					</td>
				</tr>
			</table>
			<input type="hidden" name="star">
			<input type="hidden" name="seq"  value="${sdto.seq}">
			<input type="hidden" name="cseq" value="${sdto.cseq}">
			</form>
			
			<table class="table table-bordered" style="margin-bottom: 100px;">
				<c:forEach items="${rlist}" var="rdto">
				<c:if test="${empty rdto.image}">
				<tr>
					<td>
						<div><span class="rateit" data-rateit-resetable="false" data-rateit-mode="font" data-rateit-icon="" style="font-family:fontawesome"></span></div>
						${rdto.id} ${rdto.regdate}
						<div>${rdto.content}</div>
					<c:if test = "${auth.id == rdto.id}">
					<div>
						<span class="btnspan"><a href="#!" onclick="delTourReview(${rdto.seq});">[삭제]</a></span>
						<span class="btnspan"><a href="#!" onclick="editTourReview(${rdto.seq});">[수정]</a></span>
					</div>
					</c:if>
					</td>
				</tr>
				</c:if>
				<c:if test="${not empty rdto.image}">
				<tr>
					<td>
						<div><span class="rateit" data-rateit-resetable="false" data-rateit-mode="font" data-rateit-icon="" style="font-family:fontawesome"></span></div>
						${rdto.id} ${rdto.regdate}
						<div><img src="/planitshare/userimage/tour/${rdto.image}" alt="리뷰사진"></div>
						<div>${rdto.content}</div>
						<c:if test = "${auth.id == rdto.id}">
						<div>
							<span class="btnspan"><a href="#!" onclick="delTourReview(${rdto.seq});">[삭제]</a></span>
							<span class="btnspan"><a href="#!" onclick="editTourReview(${rdto.seq});">[수정]</a></span>
						</div>
						</c:if>
					</td>
				</tr>
				</c:if>
				</c:forEach>
			</table>
			
		</section>
	</main>
	
	<script src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.16/webfont.js"></script>
	<script src="/planitshare/asset/js/jquery.rateit.js"></script>  
	<script>
		var configFontAwesome = {
		 custom: {
		     families: ['fontawesome'],
		     urls: ['https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css']
		     },
		     fontactive: function () {
		         $('.rateit-fa').rateit();
		     }
		 };
		WebFont.load(configFontAwesome);
		
		$(".rateit").bind('rated', function (event, value) { 
			
			$('input[name=star]').val(value);
			
		});
		
		$('.rateit').ready(function() {
			
			<c:forEach items="${rlist}" var="dto" varStatus="status">
			$('.rateit').eq(${status.index}).rateit('value', '${dto.star}');
			</c:forEach>
			
		});
		
		function delTourReview(seq) {
			
			if(confirm('리뷰를 삭제하시겠습니까?')){
			
				let tr = $(event.target).parent().parent().parent().parent();
				
				$.ajax({
					type: 'GET',
					url: '/planitshare/city/tourreviewdel.do',
					data: 'seq=' + seq,
					dataType: 'json',
					success: function(result) {
											
						if (result.result == '1') {
							
							tr.remove();
							
						} else {
							alert('리뷰 삭제에 실패했습니다.');
						}
						
					}, 
					error: function(result) {
						console.log(result);
					}
				});
			}
			
		}
		
		function like(seq) {
			  
			  $.ajax({
				 url:'/planitshare/city/tourlike.do',
				 type: 'POST',
				 dataType: 'json',
				 data: 'seq=' + seq,
				 success:function(result) {
					 
					 <c:set target="${dto}" property="likeCnt" value="result.likenum"/>
					 location.reload();	
					 
				 	},
				 	error:
					function(a,b,c){
					 	console.log(a,b,c);
					 
					 }
	
			 	 });
			  
		  
	 	 }
		
	
	</script>

</body>
</html>