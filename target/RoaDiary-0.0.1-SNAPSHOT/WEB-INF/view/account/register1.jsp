<%@ page contentType="text/html; charset=utf-8" %>
<!doctype html>
<html>
  <head>
    <title>개인정보 동의</title>
  </head>
  <body>
    <h1>개인정보 동의</h1>
    <p>
      <form action="register2" method="post">
        제출하신 이메일과 RoaDiary에 적은 기록들을 보관 및 서비스 개선을 위한 사용에 동의하시나요?
        <input type="checkbox" id="agree" checked />동의함.
        <input type="submit" onclick="checkif()">
      </form>
    </p>

    <script type="text/javascript">
      function checkif() {
        var checkbox = document.getElementById('agree');
        if(checkbox.checked != true) {
          alert("동의하지 않으면 계정을 만들고 서비스를 이용하실 수 없습니다.");
        }
      }
    </script>
    
  </body>
</html>