<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
  <head>
    <title>로그인 하기</title>
  <head>
  <body>
    <form:form modelAttribute="loginReqDto">
    <form:errors />
    <p>
      <label>이메일:
      <form:input path="email"/>
      <form:errors path="email"/>
      </label>
    </p>
    <br>
    <p>
      <label>비밀번호: 
      <form:password path="password"/>
      <form:errors path="password"/>
      </label>
    </p>
    <br>
    <input type="submit" value="제출하기">
    </form:form>
  </body>
</html>