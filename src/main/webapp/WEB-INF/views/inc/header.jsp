<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
   <div id="header-logo">
       <img src="/planitshare/asset/image/logo.png">
   </div>
   <ul id="header-menu">
       <li><a href="">홈</a></li>
       <li><a href="">여행지</a></li>
       <li><a href="">여행일정</a></li>
   </ul>
   
   <!-- 로그인 안 했을 때 -->
   <!-- <div id="before-login">
       <a href="">로그인</a>
       <a href="">회원가입</a>
   </div> -->

   <!-- 로그인 했을 때 -->
   <div id="header-profile">
       <div id="image-container">
           <img src="/planitshare/asset/image/profile.png" id="profile-img">
       </div>
       <div id="login-info">
           <div id="name">홍길동님</div>
           <div>
               <a href="">마이페이지</a>
               <a href="">로그아웃</a>
           </div>
       </div>
   </div>
</header>