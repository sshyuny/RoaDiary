<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
  <head>
    <title>계정 정보 변경</title>
  <head>
  <body>
    <form:form>
    <p>
      <label>현재 비밀번호: 
      <form:input path="currentPassword"/>
      <form:errors path="currentPassword"/>
      </label>
    </p>
    <br>
    <p>
      <label>새 비밀번호: 
      <form:password path="newPassword"/>
      <form:errors path="newPassword"/>
      </label>
    </p>
    <br>
    <input type="submit" value="제출하기">
    </form:form>
  </body>
</html>