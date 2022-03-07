<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
  <head>
    <title>기록 정리</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
  </head>
  
  <body style="background-color:black;">
    <figure class="text-center">
      <h1 style="color:white;">RoaDiary</h1>
      <h3 style="color:white;">태그 분석</h3>
      <p style="color:white;">세 달 동안 기록된 태그를 분석했어요.</p>
    </figure>

    <!-- container: 횟수 -->
    <div class="container mt-2 mb-2" style="color:white;">
      <div class="row align-items-center mb-4">  <!-- 가장 바깥 row[0] -->
        <!-- col[0]  -->
        <div class="col col-1">
          자주 사용한 순
        </div>
        <!-- col[1] -->
        <div class="col">
          <div class="row justify-content-md-center">
            <div class="col col-2 mb-2">
              한 일
            </div>
            <div class="col col-1 mb-2">
            </div>
            <div class="col col-2 mb-2">
              먹은 거
            </div>
            <div class="col col-1 mb-2">
            </div>
            <div class="col col-2 mb-2">
              건강
            </div>
            <div class="col col-1 mb-2">
            </div>
          </div>

          <c:forEach var="i" begin="0" end="4">
            <div class="row justify-content-md-center">
              <div class="col col-2 mb-2">
                <a href="./sortingFrequency?category=1&tag=${listCategory1[i].tagName}" class="btn btn-secondary" role="button">${listCategory1[i].tagName}</a>
              </div>
              <div class="col col-1 mb-2">
                ${listCategory1[i].quantity}회
              </div>
              <div class="col col-2 mb-2">
                <a href="./sortingFrequency?category=2&tag=${listCategory2[i].tagName}" class="btn btn-dark" role="button">${listCategory2[i].tagName}</a>
              </div>
              <div class="col col-1 mb-2">
                ${listCategory2[i].quantity}회
              </div>
              <div class="col col-2 mb-2">
                <a href="./sortingFrequency?category=3&tag=${listCategory3[i].tagName}" class="btn btn-secondary" role="button">${listCategory3[i].tagName}</a>
              </div>
              <div class="col col-1 mb-2">
                ${listCategory3[i].quantity}회
              </div>
            </div>
          </c:forEach>
        </div>
      </div>  <!-- 가장 바깥 row[0] -->

      <div class="row align-items-center">  <!-- 가장 바깥 row[1] -->
        <!-- col[0]  -->
        <div class="col col-1">
          오래 사용한 순
          <p class="fs-10">(한 일을 대상으로만 집계됩니다.)</p>
        </div>
        <!-- col[1] -->
        <div class="col">
          <c:forEach var="i" begin="0" end="4">
            <div class="row justify-content-md-center">
              <div class="col col-2 mb-2">
                <a href="./sortingTime?tag=${listTime[i].tagName}" class="btn btn-secondary" role="button">${listTime[i].tagName}</a>
              </div>
              <div class="col col-1 mb-2">
                ${listTime[i].quantity}분
              </div>
              <div class="col col-6">
              </div>
            </div>
          </c:forEach>
        </div>
      </div>  <!-- 가장 바깥 row[1] -->
      
    </div>  
    <!-- container: 횟수 -->

  </body>
</html>