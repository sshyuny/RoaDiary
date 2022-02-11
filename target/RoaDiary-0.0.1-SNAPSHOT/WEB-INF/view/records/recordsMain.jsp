<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
  <head>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
    <title>기록</title>
  </head>

  <body>
    <h1>오늘의 기록</h1>
    <div class="container">
      <form:form action="records" modelAttribute="thingsReqDto">

      <div class="row justify-content-md-center">
        <div class="col col-lg-2">
          시간
        </div>
        <div class="col col-lg-2">
          내용
        </div>
        <div class="col col-lg-2">
          카테고리
        </div>
        <div class="col col-lg-2">
          태그
        </div>
      </div>

      <div class="row justify-content-md-center">
        <div class="col col-lg-2">
          <!-- 시간 -->
          <form:input path="time"/>
        </div>
        <div class="col col-lg-2">
          <!-- 내용 -->
          <form:input path="content" />
        </div>
        <div class="col col-lg-2">
          <!-- 카테고리 -->
          <input type="radio" name="category" path="category" value="1" checked />한 일
          <input type="radio" name="category" path="category" value="2" />먹은 거
          <input type="radio" name="category" path="category" value="3" />건강
        </div>
        <div class="col col-lg-2">
          <!-- 태그 -->
          <form:input path="tag1"/>
          <form:input path="tag2"/>
          <form:input path="tag3"/>
          <form:input path="tag4"/>
        </div>
        <div class="col col-lg-8">
          <input type="submit" value="제출하기">
        </div>
      </div>
      </form:form>
    </div>

    <form method="post" id="frm"  action="recordsShow">
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col col-lg-1">
          <input type="button" id="minusDate" value="이전날" onclick="returnMinusDate()"/>
        </div>
        <div class="col col-lg-1">
          <input type="button" id="plusDate" value="다음날"  onclick="returnPlusDate()"/>
        </div>
        <div class="col col-lg-1">
          <input type="button" id="todayDate" value="오늘"  onclick="returnTodayDate()"/> 
        </div>
        <div class="col col-lg-2">
          <input type="date" id="selectDate" />
        </div>
        <div class="col col-lg-1">
          <input type="button" id="selectDateBt" value="제출"/>
        </div>
        <input type="hidden" id="someday" name="someday" value="${stringDate}"/>
      </div>
    </div>
    <script src="<c:url value='/resources/js/recordsMain.js'/>"></script>
    </form>

    <c:if test="${!empty joinTagTbs}">
    <table>
      <tr>
        <th>시간</th><th>내용</th><th>태그</th>
      </tr>
      
      <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)}">
      <tr>
        <td><input type="button" value="${joinTagTbs[i].time}" class="${joinTagTbs[i].thingsId}" onclick="changeTime(this.className)"/></td>
        <td><input type="button" value="${joinTagTbs[i].content}" class="${joinTagTbs[i].thingsId}" onclick="changeTime(this.className)"/></td>
        <td><input type="button" value="${joinTagTbs[i].name}" class="${joinTagTbs[i].thingsId}" onclick="changeTime(this.className)"/></td>
      </tr>
      </c:forEach>
    </table>
    </c:if>
    <script src="<c:url value='/resources/js/recordsMainChangeTime.js'/>"></script>


    <!-- 수정부분 -->
    <c:if test="${!empty joinTagTbs}">
    <div class="container">
      <div class="row">
        <div class="col">
          시간
        </div>
        <div class="col">
          내용
        </div>
        <div class="col">
          태그
        </div>
      </div>
      
      <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)}">
      <div class="row">
        <div class="col">
          ${joinTagTbs[i].time}
        </div>
        <div class="col">
          ${joinTagTbs[i].content}
        </div>
        <div class="col">
          ${joinTagTbs[i].name}
        </div>
      </div>
      </c:forEach>
    </div>
    </c:if>


  </body>
</html>