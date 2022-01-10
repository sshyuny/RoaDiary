<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <title>기록</title>
  </head>
  <body>
    <h1>오늘의 기록</h1>
    <p>
      <form:form action="records" modelAttribute="thingsReqDto">
        <label>
          시간:
          <form:input path="time"/>
          <br>
        </label>
        <label>
          내용:
          <form:input path="content" />
          <br>
        </label>
        <label>
          카테고리:
          <input type="radio" name="category" path="category" value="1" checked />한 일
          <input type="radio" name="category" path="category" value="2" />먹은 거
          <input type="radio" name="category" path="category" value="3" />건강
          <br>
        </label>
        <input type="submit" value="제출하기">
      </form:form>
    </p>

    <c:if test="${!empty thingsTbs}">
    <table>
      <tr>
        <th>시간</th><th>내용</th>
      </tr>
      <c:forEach var="thing" items="${thingsTbs}">
      <tr>
        <td>${thing.time}</td>
        <td>${thing.content}</td>
      </tr>
      </c:forEach>
    </table>
    </c:if>

  </body>
</html>