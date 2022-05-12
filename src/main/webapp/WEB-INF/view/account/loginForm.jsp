<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
    <title>로그인 하기</title>
  <head>

  <body style="background-color:gray;">
    <figure class="text-center mt-2 mb-4">
      <h3 style="color:white;">로그인</h3>
    </figure>

    <form:form action="account/login" modelAttribute="loginReqDto">

      <figure class="text-center mt-2 mb-4">
        <form:errors />
      </figure>

      <div class="row justify-content-md-center">
        <div class="col-3 mb-1 d-grid">
          <label>
            <p class="fs-5">이메일</p >
            <input name="email" type="email" class="form-control"/>
            <form:errors path="email"/>
          </label>
        </div>
      </div>
    
      <div class="row justify-content-md-center">
        <div class="col-3 mb-1 d-grid">
          <label>
            <p class="fs-5">비밀번호 </p>
            <input name="password" type="password" class="form-control"/>
            <form:errors path="password"/>
          </label>
        </div>
      </div>
      
      <div class="row justify-content-md-center mt-2">
        <div class="col-3 mb-2 d-grid">
          <button type="submit" class="btn btn-dark">제출하기</button>
        </div>
      </div>

      <input type="hidden" id="redirectURL" name="redirectURL" value="${redirectURL}"/>

    </form:form>
  </body>
</html>