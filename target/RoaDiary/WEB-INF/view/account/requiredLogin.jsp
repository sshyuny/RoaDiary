<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>로그인 정보 필요</title>
  <head>

  <body style="background-color:gray;">

    <figure class="text-center mt-5 mb-4">
      <p class="fs-4">로그인 정보가 필요한 페이지입니다. <br>해당 서비스를 이용하고 싶으면, 로그인을 먼저 해주세요.</p>
      <a href="./" class="btn btn-dark" role="button">메인 페이지로 돌아가기</a>
    </figure>

    

  </body>
</html>