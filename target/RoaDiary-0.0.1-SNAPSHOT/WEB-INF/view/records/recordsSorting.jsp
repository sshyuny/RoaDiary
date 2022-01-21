<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
  <head>
    <title>기록 정리</title>
  </head>
  
  <body>
    <label>
      카테고리:
      <input type="radio" name="category" path="category" value="1" checked />한 일
      <input type="radio" name="category" path="category" value="2" />먹은 거
      <input type="radio" name="category" path="category" value="3" />건강
      <br>
    </label>

  </body>
</html>