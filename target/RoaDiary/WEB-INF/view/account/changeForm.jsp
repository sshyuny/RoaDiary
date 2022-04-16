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

    <title>계정 정보 변경</title>
  <head>

  <body style="background-color:gray;">
    <figure class="text-center mt-2 mb-4">
      <h3 style="color:white;">계정 정보 변경</h1>
    </figure>

    <div class="container mt-4">
      <form:form action="changeName">
        <div class="row justify-content-md-center">
          <div class="col-3 mb-1 d-grid">
            <label>
              <p class="fs-5">이름</p>
              <input name="newName" id="newName" type="text" class="form-control" placeholder="name"/>
              현재 이름: ${currentName}
            </label>
          </div>
        </div>
        <div class="row justify-content-md-center mt-4">
          <div class="col-3 mb-2 d-grid">
            <button type="submit" class="btn btn-dark">이름 변경</button>
          </div>
        </div>
      </form:form>

      <form:form action="change">
        <div class="row justify-content-md-center">
          <div class="col-3 mb-1 d-grid">
            <label>
              <p class="fs-5">현재 비밀번호</p>
              <input name="currentPassword" type="password" class="form-control"/>
              <form:errors path="currentPassword"/>
            </label>
          </div>
        </div>
      
        <div class="row justify-content-md-center">
          <div class="col-3 mb-1 d-grid">
            <label>
              <p class="fs-5">새 비밀번호 </p>
              <input name="newPassword" type="password" class="form-control"/>
              <form:errors path="newPassword"/>
            </label>
          </div>
        </div>

        <div class="row justify-content-md-center">
          <div class="col-3 mb-1 d-grid">
            <label>
              <p class="fs-5">새 비밀번호 확인</p>
              <input name="newPasswordConfirm" type="password" class="form-control"/>
              <form:errors path="newPasswordConfirm"/>
            </label>
          </div>
        </div>
        
        <div class="row justify-content-md-center mt-4">
          <div class="col-3 mb-2 d-grid">
            <button type="submit" class="btn btn-dark">비밀번호 변경</button>
          </div>
        </div>
      </form:form>

      <div class="row justify-content-md-center mt-4">
        <div class="col-3 mb-2">
          <a href="./withdrawal" class="btn btn-dark btn-outline-danger btn-sm" role="button">탈퇴</a>
        </div>
        <%-- <script>
          function deleteAccount() {
            if (!confirm("해당 기록을 지우시겠습니까?")) {
              document.getElementById('deleteAccount').action = "recordsShow";
            } else {
              document.getElementById('deleteAccount').submit();
            }
          }
        </script> --%>
      </div>

    </div>

  </body>
</html>