<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <title>RoaDiary</title>
  </head>
  <body>
    <c:if test="${empty loginInfo}">
    <h1>메인 페이지</h1>

    추가내용

    <p>
      <a href="./account/register1">회원 가입하기</a>
    </p>

    <p>
      <a href="./account/login">로그인하기</a>
    </p>
    </c:if>

    <c:if test="${! empty loginInfo}">
    <p>${loginInfo.name}님, 안녕하세요.</p>
    <p>
      <a href="./account/logout">로그아웃</a>
    </p>
    <p>
      <a href="./account/change">비밀번호 변경</a>
    </p>
    </c:if>

  </body>
</html>