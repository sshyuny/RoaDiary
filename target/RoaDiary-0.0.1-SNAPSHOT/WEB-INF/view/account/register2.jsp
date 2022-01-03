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
          이메일:<br>
          <input type="email" name="email" id="email">
        </label>
        <label>
          이름:<br>
          <input type="text" name="name" id="name">
        </label>
        <label>
          비밀번호:<br>
          <input type="password" name="password" id="password">
        </label>
        <label>
          비밀번호 확인:<br>
          <input type="password" name="confirmPassword" id="confirmPassword">
        </label>
        <input type="submit">
      </form>
      
    </p>
  </body>
</html>