<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<style>
	.table.layout {
		width: 1000px;
		margin-bottom: 10px;
	}
	
	.table.layout tr:nth-child(1) {
		text-align: center;
	}
	
	.table.layout tr:nth-child(2),
	.table.layout tr:nth-child(3) {
		text-align: right;
	}
	
	#plan-info div, #plan-info h2 {
		margin-bottom: 10px;
	}
	
	#count span {
		margin-right: 10px;
	}
	
	#count i {
		margin-right: 5px;
	}
	
	#plan-info .fa-solid.fa-heart {
		color: tomato;
	}
	
	#author-date {
		color: dimgray;
	}
	
	#map-box {
		width: 500px;
	}
	
	#map {
		width: 450px;
		height: 300px;
		margin-bottom: 10px;
	}
	
	#map-box .btn {
		margin: 0 auto;
		width: 450px;
	}
	
	.date, .comment-info {
		color: gray;
	}
	
	.day {
		display: inline-block;
		margin-right: 10px;
		margin-top: 50px;
	}
	
	.table.plan {
		margin-bottom: 100px;
	}
	
	.comment-info {
		display: inline-block;
		margin-bottom: 5px;
	}
	
	.table.comment td {
		height: 120px;
	}
	
	#like {
		outline: none;
		border: none;
		background-color: white;
		display: inline-block;
		margin-bottom: 50px;
		float: right;
	}
	
	#like i {
		color: black;
		font-size: 2rem;
	}
	
	#like i.liked {
		color: tomato;
		font-size: 2rem;
	}
	
	#invite img {
		width: 50px;
		height: 50px;
		border-radius: 50% 50%;
	}
	
	#invite span {
		display: inline-block;
		margin-right: 5px;
	}
	
	.table.comment td {
		padding: 15px;
	}
	
	#plan {
		height: 400px;
	}
	
	#plan th {
		width: 120px;
	}
	
	#plan td, #plan th {
		text-align: center;
		vertical-align: middle;
		border: 1px solid lightgray;
		padding: 5px;
	}
	
	#plan tr:nth-child(1) td { height : 40px; } 
	#plan tr:nth-child(2) td { height : 40px; } 
	#plan tr:nth-child(3) td { height : auto; } 
	
	#write-comment textarea {
		resize: none;
		width: 900px;
		display: inline-block;
	}
	
	#write-comment input[type=button] {
		display: inline-block;
	}
	
	.table.plan, #write-comment {
		width: 1000px;
		margin: 0 auto;
	}
	
	.table.plan {
		margin-bottom: 50px;
	}
	
	.place-img {
		width: 150px;
		height: 120px;
	}
	
	.day-title {
		border-bottom: 1px solid lightgray;
	}
	
	#place td {
		vertical-align: middle;
	}
	
	.comment-box {
		position: relative;
		left: 0;
		top: 0;
	}
	
	#delcomment-btn {
		position: absolute;
		bottom: 8px;
		display: block;
		cursor: pointer;
		right: 8px;
	}
	
	.modal-body {
		margin: 0 auto;
	}
	
	.ui-widget {
		z-index:2147483647;
	}
	
	#content {
		width: 100%;
		height: 400px;
		border-radius: 3%;
		border: 1px solid lightgray;
		text-align: left;
		padding: 8%;
	}
	
	.customoverlay {position:relative;bottom:60px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
	.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
	.customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
	.customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}
	.customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main>
		<section>
		
		<!-- Modal -->
		<div class="modal fade" id="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">일정에 초대하기</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      	<div class="ui-widget">
		      		<label for="invite-user" class="form-label">초대할 회원의 아이디<input type="text" id="invite-user" class="form-control"></label>
				</div>
			  	
			 </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
		        <button type="button" class="btn btn-primary" id="invite-btn">초대하기</button>
		      </div>
		    </div>
		  </div>
		</div>
			
			<table class="table table-borderless layout">
				<tr>
					<td colspan="2" id="plan-info">
						<div>
							<span>${pdto.city} · ${dayCnt-1}박 ${dayCnt}일</span>
						</div>
						<h2 id="title">${pdto.title}</h2>
						<div id="author-date">${pdto.author} ${pdto.regdate}</div>
						<div id="count">
							<i class="fa-solid fa-heart"></i><span id="likeCnt">${pdto.likeCnt}</span>
							<i class="fa-solid fa-eye"></i><span>${pdto.readcount}</span>
						</div>
					</td>
				</tr>
				<tr id="invite">
					<td colspan="2">
						<c:forEach var="planUser" items="${puList}">
						<span><img src="/planitshare/userimage/${planUser.profile}" title="${planUser.id}"></span>
						</c:forEach>
						<span><img src="/planitshare/userimage/${pdto.authorProfile}" title="${pdto.author}"></span>
						<c:if test="${auth.id == pdto.author}">
						<button class="btn btn-secondary btn-sm" data-toggle="modal" data-target="#modal">+</button>
						</c:if>
					</td>
				</tr>
				<c:if test="${auth.id == pdto.author}">
				<tr>
					<td colspan="2">
						<button type="button" class="btn btn-primary">수정하기</button>
						<button type="button" class="btn btn-secondary" onclick="delplan('${pdto.seq}')">삭제하기</button>
					</td>
				</tr>
				</c:if>
				
				<c:forEach var="planUser" items="${puList}">
				<c:if test="${auth.id == planUser.id}">
				<tr>
					<td colspan="2">
						<button type="button" class="btn btn-primary">수정하기</button>
						<button type="button" class="btn btn-secondary" onclick="delplan('${pdto.seq}')">삭제하기</button>
					</td>
				</tr>
				</c:if>
				</c:forEach>
				<tr>
					<td id="map-box">
						<div id="map" style="width:100%;height:350px;"></div>
						<button class="btn btn-secondary btn-block" type="button" onclick="location.href='/planitshare/plan/planmap.do?seq=${pdto.seq}'">지도 크게 보기</button>
					</td>
					<td>
						<div id="content">
							${pdto.content}
						</div>
					</td>
				</tr>
			</table>
			
			<table class="table table-borderless plan">
				<c:forEach var="day" items="${days}">
				<tr class="day-title">
					<th><h4 class="day">Day ${day.day}</h4> <span class="date">${day.date}</span></th>
				</tr>
				<c:forEach var="plan" items="${planList}">
				<tr>
					<c:if test="${plan.day == day.day}"> 
					<td>
						<table id="place">
							<tr>
								<td>
									<img src="/planitshare/asset/image/${plan.image}" class="place-img">
								</td>
								<td>
								<h5>${plan.name}</h5>
								<span>${plan.address}</span>
								</td>
							</tr>
						</table>
					</td>
					</c:if>
				</tr>
				</c:forEach>
				</c:forEach>
			</table>
			
			<c:if test="${not empty auth}">
			<button id="like"><i class="fa-solid fa-heart"></i></button>
			</c:if>
			
			<table class="table table-bordered comment">
				<c:forEach var="dto" items="${clist}">
				<tr>
					<td class="comment-box">
						<span class="comment-info">${dto.id} ${dto.regdate}</span>
						<div>${dto.content}</div>
						<c:if test="${dto.id == auth.id}">
						<span class="badge badge-secondary" id="delcomment-btn" onclick="deleteComment('${dto.seq}')">삭제</span>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<table id="write-comment">
				<tr>
					<td><textarea class="form-control" placeholder="댓글을 작성해주세요." id="comment"></textarea></td>
					<td><input type="button" class="btn btn-primary" value="댓글 작성" id="add-comment"/></td>
				</tr>
			</table>
		</section>
		
		<!-- <a href="https://www.flaticon.com/free-icons/map-marker" title="map marker icons">Map marker icons created by juicy_fish - Flaticon</a> -->
		
	</main>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=45bff8e1f05130c0cfcaa2b32275bf5e&libraries=services"></script>
	<script>
	
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	    mapOption = { 
	        center: new kakao.maps.LatLng(33.37665833461409, 126.54221668447137), // 지도의 중심좌표
	        level: 10 // 지도의 확대 레벨
	    };

	    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	    
	 	// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
		var bounds = new kakao.maps.LatLngBounds();
	    
	 	// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();
		
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('${pdto.mainAddress}', function(result, status) {

		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {

		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords);
		    }
		});
		
		let placeList = [];
		
		<c:forEach items="${planList}" var="place">
		var place = {
				address: '${place.address}',
				name: '${place.name}',
				distinct: '${place.distinct}',
				day: '${place.day}'
		};
		
		placeList.push(place);
		</c:forEach>
		
		linePath = [];
		
		var itemsProcessed = 0;
		
		placeList.forEach(function(place, index) {
			
			geocoder.addressSearch(place.address, function(result, status) {

			    // 정상적으로 검색이 완료됐으면 
			     if (status === kakao.maps.services.Status.OK) {
			    	 
			    	var foodMarkerSrc = "https://ifh.cc/g/xKgRT8.png";
			 		var tourMarkerSrc = "https://ifh.cc/g/8d8KZy.png";
			 		var lodgingMarkerSrc = "https://ifh.cc/g/V4mXnk.png";
			 		
			 		// 마커 이미지의 이미지 크기 입니다
			 	    var imageSize = new kakao.maps.Size(35, 35); 
			 	    
			 	    // 마커 이미지를 생성합니다    
			 	    var foodMarker = new kakao.maps.MarkerImage(foodMarkerSrc, imageSize);
			 	    var tourMarker = new kakao.maps.MarkerImage(tourMarkerSrc, imageSize);
			 	    var lodgingMarker = new kakao.maps.MarkerImage(lodgingMarkerSrc, imageSize);
			    	
			    	var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			    	linePath.push(coords);
			    	bounds.extend(coords);
			    	
			    	var marker = '';
			    	
			    	var title = 'DAY ' + place.day + ' ' + place.name;
			    	
			    	if (place.distinct == 'food') {
			    		// 마커를 생성합니다
					   marker = new kakao.maps.Marker({
					        map: map, // 마커를 표시할 지도
					        position: coords, // 마커를 표시할 위치
					        title : title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
					        image : foodMarker
					    });
			    	} else if (place.distinct == 'tour') {
			    		// 마커를 생성합니다
					    marker = new kakao.maps.Marker({
					        map: map, // 마커를 표시할 지도
					        position: coords, // 마커를 표시할 위치
					        title : title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
					        image : tourMarker
					    });
			    	} else {
			    		// 마커를 생성합니다
					    marker = new kakao.maps.Marker({
					        map: map, // 마커를 표시할 지도
					        position: coords, // 마커를 표시할 위치
					        title : title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
					        image : lodgingMarker
					    });
			    	}
			    	
			    	var content = '<div class="customoverlay" id="popup_map">' +
			        '  <a href="https://map.kakao.com/link/search/' + place.name + '" target="_blank">' +
			        '    <span class="title">' + (Number(index) + 1) + '. ' + place.name + '</span>' +
			        '  </a>' +
			        '</div>';
			    	
			    	var customOverlay = new kakao.maps.CustomOverlay({
			    	    position: coords,
			    	    content: content,
			    	    removable: true
			    	});
			    	
			    	kakao.maps.event.addListener(marker, 'click', function() {
			    		
			    		var length = $("#popup_map").length;
			    		
			    		if(length == 0){
			    			customOverlay.setMap(map); // 마커 처음 클릭시 커스텀오버레이 맵에 띄우는애
			    		} else{
			    			customOverlay.setMap(null); // 마커를 한번더 클릭시 커스텀오버레이 지우는애
			    		}
			    	});
			    	
			    	// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
			    	function closeOverlay() {
			    	    overlay.setMap(null);     
			    	}
			    	
					itemsProcessed++;
			    	
			    	if(itemsProcessed === placeList.length) {
			    		
			    	 	// 지도에 표시할 선을 생성합니다
			    		var polyline = new kakao.maps.Polyline({
			    		    path: linePath, // 선을 구성하는 좌표배열 입니다
			    		    strokeWeight: 3, // 선의 두께 입니다
			    		    strokeColor: '#ED4545', // 선의 색깔입니다
			    		    strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
			    		    strokeStyle: 'solid' // 선의 스타일입니다
			    		});

			    		// 지도에 선을 표시합니다
			    		polyline.setMap(map);
			    		map.setBounds(bounds);
		    	    }
		    	    
			    } else {
			    	alert('failed');
			    } 
			    
			});
		});


		
		
	// 관심등록한 음식점일 경우
	<c:if test="${like == 'y'}">
	$('#like i').addClass('liked');
	</c:if>

	// 댓글 작성
	$('#add-comment').click(function() {
		
		// 로그인 체크
		let id = '${auth.id == null ? '' : auth.id}';
		
		if (id == '' || id == null) {
			alert('로그인 후 댓글을 작성해주세요.');
			location.href='/planitshare/login.do';
			return;
		}
		
		$.ajax({
			type: 'POST',
			url: '/planitshare/plan/view/addcomment.do',
			data: 'comment=' + $('#comment').val() + '&seq=' + ${pdto.seq},
			dataType: 'json',
			success: function(result) {
				
				if (result.result == '1') {
					location.reload();
				} else {
					alert('댓글 작성 실패');
				}
			},
			error: function(a, b, c) {
				console.log(a, b, c);
			}
		});
	});
	
	// 댓글 삭제
	function deleteComment(seq) {
		
		if (confirm("댓글를 삭제하시겠습니까?")) {
			$.ajax({
				type: 'GET',
				url: '/planitshare/plan/view/delcomment.do',
				dataType: 'json',
				data: 'seq=' + seq,
				success: function(result) {
					location.reload();
				},
				error: function(a, b, c) {
					console.log(a, b, c);
				}
			});	
		}
		
	}
	
	// 관심등록, 해제 구현
	$(document).on("click", '#like', function() {
		$.ajax({
			type: 'GET',
			url: '/planitshare/plan/view/like.do',
			dataType: 'json',
			data: 'seq=' + ${pdto.seq},
			success: function(result) {
				if (result.status == 'insertLike') {
					$('#like i').addClass('liked');
					let likeCnt = $('#likeCnt').html();
					$('#likeCnt').html(parseInt(likeCnt) + 1);
				} else {
					$('#like i').removeClass('liked');
					let likeCnt = $('#likeCnt').html();
					$('#likeCnt').html(parseInt(likeCnt) - 1);
				}
			},
			error: function(a, b, c) {
				console.log(a, b, c);
			}
		});
	});
	
	function delplan(seq) {
		if (confirm('해당 일정을 삭제하시겠습니까?')) {
			location.href='/planitshare/plan/view/delete.do?seq=' + seq;
		}
	}
	
	let idList = [];
	
	<c:forEach var="id" items="${idList}">
		idList.push("${id}");
	</c:forEach>
	
	$( function() {
	    $( "#invite-user" ).autocomplete({
	      source: idList
	    });
	  } );
	
	  // modal이 열릴 때 다시 영역 한정 (appendTo 옵션)
	  $("#modal").on("shown.bs.modal", function() {
	    $("#tags").autocomplete("option", "appendTo", "#modal")
	  })
	  
	  $('#invite-btn').click(function() {
		 
		 $.ajax({
			type: 'GET',
			url: '/planitshare/plan/view/invite.do',
			data: 'pseq=${pdto.seq}' + '&id=' + $('#invite-user').val() + '&host=${pdto.author}',
			dataType: 'json',
			success: function(result) {
				
				if (result.result == "1") {
					alert('초대 성공');
					$('#invite-user').val('');
				} else {
					alert('올바르지 않은 아이디입니다.');
				}
			},
			error: function(a, b, c) {
				console.log(a, b, c);
			}
				
		 });
	  });
	
	</script>

</body>
</html>
