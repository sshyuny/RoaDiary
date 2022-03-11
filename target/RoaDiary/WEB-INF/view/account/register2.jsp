<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

    <title>계정 생성</title>
  </head>

  <body style="background-color:gray;">
    <figure class="text-center mt-4 mb-4">
      <h3>새로운 계정 생성</h3>
    </figure>

    <div class="container">
      <form:form action="registered" modelAttribute="registerReqDto">
        <div class="row justify-content-md-center">
          <div class="col-3 d-grid">
            <label>
              <p class="fs-5">이메일
              <input name="email" type="email" class="form-control"/></p >
              <%-- <form:input path="email" /> --%>
              <form:errors path="email" />
            </label>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-3 d-grid">
            <label>
              <p class="fs-5">이름
              <input name="name" type="text" class="form-control"/></p >
              <%-- <form:input path="name" /> --%>
            </label>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-3 d-grid">
            <label>
              <p class="fs-5">비밀번호
              <input name="password" type="password" class="form-control"/></p >
              <%-- <form:errors path="password" /> --%>
            </label>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-3  d-grid">
            <label>
              <p class="fs-5">비밀번호 확인
              <input name="confirmPassword" type="password" class="form-control"/></p >
              <%-- <form:errors path="confirmPassword" /> --%>
            </label>
          </div>
        </div>
        
        <div class="row justify-content-md-center mt-2">
          <div class="col-3 d-grid">
            <button type="submit" class="btn btn-dark">제출하기</button>
          </div>
        </div>
                
      </form:form>
      
    </div>

  </body>
</html>