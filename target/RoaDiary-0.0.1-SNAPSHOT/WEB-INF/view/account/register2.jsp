<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
  <head>
    <title>계정 생성</title>
  </head>
  <body>
    <h1>To Register Account</h1>
    <p>
      <form:form action="registered" modelAttribute="accountRegisterRequest">
        <label>
          이메일:
          <form:input path="email" />
          <form:errors path="email" />
          <br>
        </label>
        <label>
          이름:
          <form:input path="name" />
          <form:errors path="name" />
          <br>
        </label>
        <label>
          비밀번호:
          <form:password path="password" />
          <form:errors path="password" />
          <br>
        </label>
        <label>
          비밀번호 확인:
          <form:password path="confirmPassword" />
          <form:errors path="confirmPassword" />
          <br>
        </label>
        <input type="submit" value="제출하기">
      </form:form>

    <script type="text/javascript">
      /*function checkIfEq() {
        var pw = document.getElementById('password');
        var cfPw = document.getElementById('confirmPassword');
        if(pw != cfPw) {
          alert("비밀번호가 일치하지 않습니다.");
        }
      }*/
    </script>
      
    </p>
  </body>
</html>