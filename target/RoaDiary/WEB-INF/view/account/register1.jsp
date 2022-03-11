<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>개인정보 동의</title>
  </head>

  <body style="background-color:gray;">
    <figure class="text-center mt-5 mb-4">
      <h1>개인정보 동의</h1>
    </figure>

    <div class="container">
      <form action="register2" method="post">

        <div class="row justify-content-md-center">
          <div class="col-5">
            <p class="fs-5">제출하신 이메일과 RoaDiary에 적은 기록들을 해당 서버에 보관 및 서비스 개선을 위한 사용에 동의하시나요?</p>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-5">

            <p class="fs-5"><input type="checkbox" id="agree" name="agree" value="true" class="mx-2"/>
            동의
            <button type="submit" class="btn btn-dark mx-2" onclick="checkIfAgree()" >확인</button>
            </p>
            
          </div>
        </div>

      </form>
    </div>

    <script type="text/javascript">
      function checkIfAgree() {
        var checkbox = document.getElementById('agree');
        if(checkbox.checked != true) {
          alert("동의하지 않으면 계정을 만들고 서비스를 이용하실 수 없습니다.");
        }
      }
    </script>

  </body>
</html>