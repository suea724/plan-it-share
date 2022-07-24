<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plan It Share</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=45bff8e1f05130c0cfcaa2b32275bf5e&libraries=services"></script>
<style>

	#header-menu li:nth-child(3) a {
		color : #6DA2DF;
	}

	#map {
		width: 100%;
		height: 100vh;
		margin: 0 auto;
	}
	
	.info-title {
	    display: block;
	    background: #50627F;
	    color: #fff;
	    text-align: center;
	    height: 24px;
	    line-height:22px;
	    border-radius:4px;
	    padding:0px 10px;
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
		<div id="map"></div>
	</main>

	<script type="text/javascript">
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(33.37665833461409, 126.54221668447137), // 지도의 중심좌표
        level: 9 // 지도의 확대 레벨
    };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    
	 // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    var mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.BOTTOMRIGHT);
    
    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.BOTTOM);

    
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
	
	// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
	var bounds = new kakao.maps.LatLngBounds();
	
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
		    		
		    		map.setBounds(bounds);
		    		
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
	    	    }
	    	    
		    } else {
		    	alert('failed');
		    } 
		    
		});
	});
	
	
		
	</script>

</body>
</html>