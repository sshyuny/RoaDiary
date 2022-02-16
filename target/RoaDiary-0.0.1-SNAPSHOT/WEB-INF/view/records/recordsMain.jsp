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

      <form:form action="recordsChange" modelAttribute="thingsReqDto">
        <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)}">
          <div class="row">
            <div class="col">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTime${i}" aria-expanded="false" aria-controls="collapseTime${i}">
                ${joinTagTbs[i].time}
              </button>
            </div>
            <div class="col">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i}" aria-expanded="false" aria-controls="collapseContent${i}">
                ${joinTagTbs[i].content}
              </button>
            </div>
            <div class="col">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseName${i}" aria-expanded="false" aria-controls="collapseName${i}">
                ${joinTagTbs[i].name}
              </button>
            </div>
          </div>

          <div class="row">
             <!--   <input type="hidden" id="thingsId${i}" name="thingsId${i}" value="${joinTagTbs[i].thingsId}"/> -->
            <form:hidden path="thingsId" value="${joinTagTbs[i].thingsId}"/>  <!-- hidden value로 되는지 확인 후 주석 지우기-->
            <div class="col">
              <div class="collapse" id="collapseTime${i}" value="${joinTagTbs[i].thingsId}">
                <form:input path="time"/>
              </div>
            </div>
            <div class="col">
              <div class="collapse" id="collapseContent${i}">
                <form:input path="content" />
              </div>
            </div>
            <div class="col">
              <div class="collapse" id="collapseName${i}">
                <form:input path="tag1"/>
                <form:input path="tag2"/>
                <form:input path="tag3"/>
                <form:input path="tag4"/>
              </div>
            </div>
          </div>
          
        </c:forEach>
      </form:form>
    </div>
    </c:if>
    <script src="<c:url value='/resources/js/recordsMainChangeTime.js'/>"></script>

  </body>
</html>