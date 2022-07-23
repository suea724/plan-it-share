<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



	<h2>${dto.name}</h2>
		<div id="city-submenu">
          <span><a href="/planitshare/citydetail.do?cseq=${dto.seq}">홈</a></span>
          <span><a href="/planitshare/city/lodging.do?cseq=${dto.seq}">숙소</a></span>
          <span><a href="/planitshare/city/tour.do?cseq=${dto.seq}">관광명소</a></span>
          <span><a href="/planitshare/city/food.do?cseq=${dto.seq}">음식점</a></span>
       	</div>


