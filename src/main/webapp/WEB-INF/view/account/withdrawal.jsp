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
      <form action="withdrawal" method="post">

        <div class="row justify-content-md-center">
          <div class="col-5">
            <p class="fs-6">회원 탈퇴를 하실 경우, 회원 정보가 삭제됩니다.</p>
            <p class="fs-6">이전에 기록하신 데이터들은 별도의 언급이 없으면 유지됩니다(이때 개인 정보는 저장되지 않습니다.).</p>
            <br>
            <p class="fs-6">기록된 데이터의 삭제를 원하실 경우, devsshyuny@gmail.com로 연락해주세요.</p>
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