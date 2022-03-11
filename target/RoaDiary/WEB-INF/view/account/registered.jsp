<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>등록 완료</title>
  </head>

  <body style="background-color:gray;">
    
    <div class="container mt-4">
      <div class="row justify-content-md-center">
        <div class="col-4">
          <figure class="text-center">
            <h1>등록되었습니다.</h1>
          </figure>
        </div>
      </div>

      <div class="row justify-content-md-center">
        <div class="col-3 mt-4">
          <a href="../" class="btn btn-dark" role="button">메인 페이지로 돌아가기</a>
        </div>
      </div>
    </div>

  </body>
</html>