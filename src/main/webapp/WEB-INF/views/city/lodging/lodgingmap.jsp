<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${dto.name}</title>
<style>
	html, body {
		width: 100%;
		height: 100%;	
	}
	
	#map {
		width: 100%:
		height: 100%;	
	}
</style>
</head>
<body>
	<div id="map" style="width:100%;height:100%;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=45bff8e1f05130c0cfcaa2b32275bf5e&libraries=services"></script>
	<script>
	
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	    mapOption = { 
	        center: new kakao.maps.LatLng(33.37665833461409, 126.54221668447137), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };
		
	    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	    
	 	// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();
		
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('${dto.address}', function(result, status) {
	
		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {
	
		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords);
		        
		        marker = new kakao.maps.Marker({
			        map: map, // 마커를 표시할 지도
			        position: coords, // 마커를 표시할 위치
			    });
		    }
		});
		
	</script>
</body>
</html>