<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>비밀번호 변경 불가</title>
  <head>

  <body style="background-color:gray;">

    <figure class="text-center mt-5 mb-4">
      <p class="fs-4">방문자 계정(visitor)은 비밀번호를 변경할 수 없습니다.</p>
      <a href="../" class="btn btn-dark" role="button">메인 페이지로 돌아가기</a>
    </figure>

  </body>
</html>