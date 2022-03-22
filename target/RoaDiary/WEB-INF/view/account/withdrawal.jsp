<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>회원 탈퇴</title>
  </head>

  <body style="background-color:gray;">
    <figure class="text-center mt-5 mb-4">
      <h1>회원 탈퇴</h1>
    </figure>

    <div class="container">
      <form action="withdrawalPost" method="post">

        <div class="row justify-content-md-center">
          <div class="col-5">
            <p class="fs-6">회원 탈퇴를 하실 경우, 회원 정보와 회원님이 입력하실 기록들도 다같이 일괄 삭제되어 복구가 불가능합니다.</p>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-5">
            <p class="fs-5">회원 탈퇴에 관한 위 내용에 동의하시나요?</p>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-5">
            <button type="submit" class="btn btn-dark btn-outline-danger btn-sm mx-2">동의 및 탈퇴</button>            
          </div>
        </div>

      </form>
    </div>

  </body>
</html>