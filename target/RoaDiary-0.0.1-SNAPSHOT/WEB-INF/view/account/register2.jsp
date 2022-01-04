<%@ page contentType="text/html; charset=utf-8" %>
<!doctype html>
<html lang="en">
  <head>
    <title>계정 생성</title>
  </head>
  <body>
    <h1>To Register Account</h1>
    <p>
      <form action="registered" method="post">
        <label>
          이메일:
          <input type="email" name="email" id="email" />
          <br>
        </label>
        <label>
          이름:
          <input type="text" name="name" id="name" />
          <br>
        </label>
        <label>
          비밀번호:
          <input type="password" name="password" id="password" />
          <br>
        </label>
        <label>
          비밀번호 확인:
          <input type="password" name="confirmPassword" id="confirmPassword" />
          <br>
        </label>
        <input type="submit" onclick="checkIfEq()">
      </form>

    <script type="text/javascript">
      function checkIfEq() {
        var pw = document.getElementById('password');
        var cfPw = document.getElementById('confirmPassword');
        if(pw != cfPw) {
          alert("비밀번호가 일치하지 않습니다.");
        }
      }
    </script>
      
    </p>
  </body>
</html>