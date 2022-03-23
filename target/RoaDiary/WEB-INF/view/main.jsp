<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
    <title>RoaDiary</title>
  </head>

  <body style="background-color:gray;">

    <%-- 로그인 안되어있을 경우 --%>
    <c:if test="${empty loginInfo}">
      <figure class="text-center mt-2 mb-4">
        <h1 style="color:white;">RoaDiary</h1>
        <h3 style="color:white;">언제 어디서든 기록하고 관리하세요!</h3>
      </figure>

      <div class="container">
        <div class="row justify-content-md-center">
          <div class="col-4 mb-2 d-grid">
            <a href="./account/register1" class="btn btn-dark btn-outline-info btn-lg" role="button">회원 가입</a>
          </div>
        </div>
        <div class="row justify-content-md-center">
          <div class="col-4 mb-2 d-grid">
            <a href="./account/login" class="btn btn-dark btn-outline-info btn-lg" role="button">로그인</a>
          </div>
        </div>
        <div class="row justify-content-md-center">
          <div class="col-4 mb-2 d-grid">
            <a href="./loginForVisitor" class="btn btn-secondary btn-outline-info btn-sm" role="button">둘러보기(방문자용)</a>
          </div>
        </div>
      </div>

      <%-- 연락처 --%>
      <div class="position-absolute bottom-0 start-0 ">
        <button class="btn" type="button" data-bs-toggle="offcanvas" data-bs-target="#placeToConnect" aria-controls="placeToConnect">
          <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="white" class="bi bi-chat-right-fill" viewBox="0 0 16 16">
            <path d="M14 0a2 2 0 0 1 2 2v12.793a.5.5 0 0 1-.854.353l-2.853-2.853a1 1 0 0 0-.707-.293H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h12z"/>
          </svg>
        </button>
      </div>
      <div class="offcanvas offcanvas-bottom" tabindex="-1" id="placeToConnect" aria-labelledby="placeMenuLabel">
        <div class="offcanvas-header">
          <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
          Contact: devsshyuny@gmail.com
        </div>
      </div>
    </c:if>

    <%-- 로그인 되어있을 경우 --%>
    <c:if test="${! empty loginInfo}">
      <figure class="text-center mt-2 mb-4">
        <h1 style="color:white;">RoaDiary</h1>
      </figure>

      <div class="container mt-2 mb-2">
        <div class="row justify-content-md-center">
          <div class="col-4 d-grid">
            <p style="color:black;" class="fs-4">${loginInfo.name}님, 안녕하세요.</p>
          </div>
        </div>
      </div>

      <div class="container mt-2 mb-2">
        <div class="row justify-content-md-center">
          <div class="col-4 mb-2 d-grid">
            <a href="./records" class="btn btn-dark btn-outline-info btn-lg" role="button">기록 서비스 이용하기</a>
          </div>
        </div>
        <div class="row justify-content-md-center">
          <div class="col-4 mb-2 d-grid">
            <a href="./sortingMain" class="btn btn-dark btn-outline-info btn-lg" role="button">기록 분석 보기</a>
          </div>
        </div>
      </div>

      <div class="container">
        <div class="row justify-content-md-center">
          <div class="col-2 mb-2 d-grid">
            <a href="./account/logout" class="btn btn-secondary btn-outline-info btn-sm" role="button">로그아웃</a>
          </div>
          <div class="col-2 mb-2 d-grid">
            <a href="./account/change" class="btn btn-secondary btn-outline-info btn-sm" role="button">계정 정보 변경</a>
          </div>
        </div>
      </div>

      <%-- 연락처 --%>
      <div class="position-absolute bottom-0 start-0 ">
        <button class="btn" type="button" data-bs-toggle="offcanvas" data-bs-target="#placeToConnect" aria-controls="placeToConnect">
          <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="white" class="bi bi-chat-right-fill" viewBox="0 0 16 16">
            <path d="M14 0a2 2 0 0 1 2 2v12.793a.5.5 0 0 1-.854.353l-2.853-2.853a1 1 0 0 0-.707-.293H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h12z"/>
          </svg>
        </button>
      </div>
      <div class="offcanvas offcanvas-bottom" tabindex="-1" id="placeToConnect" aria-labelledby="placeMenuLabel">
        <div class="offcanvas-header">
          <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
          Contact: devsshyuny@gmail.com
        </div>
      </div>
    </c:if>

  </body>
</html>