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

    <title>RoaDiary 주소 이전 안내</title>
  </head>

  <body style="background-color:gray;">

    <figure class="text-center mt-5 mb-4">
      <p class="fs-4">사이트 주소와 포트가 변경되었습니다. 아래 메인 페이지로 연결해주세요.</p>
      <a href="http://roadiary.sshyuny.com" class="btn btn-dark" role="button">메인 페이지로 돌아가기</a>
    </figure>

  </body>
</html>