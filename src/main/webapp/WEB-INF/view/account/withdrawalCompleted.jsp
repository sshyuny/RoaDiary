<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>변경 완료</title>
  <head>

  <body style="background-color:gray;">

    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col-5 mt-4">
          <p class="fs-4">탈퇴가 정상적으로 완료되었습니다.</p>
        </div>
      </div>

      <div class="row justify-content-md-center">
        <div class="col-5 mb-2">
          <a href="../" class="btn btn-dark" role="button">메인 페이지로 돌아가기</a>
        </div>
      </div>
    </div>

  </body>

</html>