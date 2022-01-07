<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
  <head>
    <title>기록</title>
  </head>
  <body>
    <h1>오늘의 기록</h1>
    <p>
      <form:form action="">
        <label>
          이메일:
          <form:input path="email" />
          <br>
        </label>
        <label>
          이름:
          <form:input path="name" />
          <br>
        </label>
        <input type="submit" value="제출하기">
      </form:form>
      
    </p>
  </body>
</html>