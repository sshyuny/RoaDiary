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

  <body style="background-color:black;">
    <figure class="text-center">
      <h1 style="color:white;">오늘의 기록</h1>
    </figure>
    <div class="container">
      <form:form action="records" modelAttribute="thingsReqDto">

      <div class="row justify-content-md-center">
        <div class="col col-lg-2" style="color:white;">
          시간
        </div>
        <div class="col col-lg-2" style="color:white;">
          내용
        </div>
        <div class="col col-lg-2" style="color:white;">
          카테고리
        </div>
        <div class="col col-lg-2" style="color:white;">
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
        <div class="col col-lg-1">
          <input type="submit" value="제출하기">
        </div>
      </div>
      </form:form>
    </div>

    <form method="post" id="frm"  action="recordsShow">
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col col-lg-1">
          <button type="button" class="btn btn-dark" id="minusDate" onclick="returnMinusDate()">이전날</button>
        </div>
        <div class="col col-lg-1">
          <button type="button" class="btn btn-dark" id="plusDate" onclick="returnPlusDate()">다음날</button>
        </div>
        <div class="col col-lg-1">
          <button type="button" class="btn btn-dark" id="todayDate" onclick="returnTodayDate()">오늘</button>
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
      <div class="row justify-content-md-center">
        <div class="col col-lg-2">
          시간
        </div>
        <div class="col col-lg-2">
          내용
        </div>
        <div class="col col-lg-2">
          태그
        </div>
      </div>

      
      <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)-1}"> <!--begin이 0부터 시작돼야 해서, end에 'joinTagTbs 길이- 1' 을 넣어줌-->
        <form:form action="recordsChange" modelAttribute="thingsReqDto">
          <div class="row justify-content-md-center">
            <div class="col col-lg-2">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTime${i}" aria-expanded="false" aria-controls="collapseTime${i}">
                ${joinTagTbs[i].time}
              </button>
            </div>
            <div class="col col-lg-2">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i}" aria-expanded="false" aria-controls="collapseContent${i}">
                ${joinTagTbs[i].content}
              </button>
            </div>
            <div class="col col-lg-2">
              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseName${i}" aria-expanded="false" aria-controls="collapseName${i}">
                ${joinTagTbs[i].name}
              </button>
            </div>
            <div class="col col-lg-1">
              <button type="submit" class="btn btn-dark">수정 제출</button>
            </div>
          </div>

          <div class="row justify-content-md-center">
            <form:hidden path="thingsId" value="${joinTagTbs[i].thingsId}"/>
            <div class="col col-lg-2">
              <div class="collapse" id="collapseTime${i}">
                <form:input path="time"/>
              </div>
            </div>
            <div class="col col-lg-2">
              <div class="collapse" id="collapseContent${i}">
                <form:input path="content" />
              </div>
            </div>
            <div class="col col-lg-2">
              <div class="collapse" id="collapseName${i}">
                <form:input path="tag1"/>
                <form:input path="tag2"/>
                <form:input path="tag3"/>
                <form:input path="tag4"/>
              </div>
            </div>
          </div>
        </form:form>
      </c:forEach>
      
    </div>
    </c:if>
    <script src="<c:url value='/resources/js/recordsMainChangeTime.js'/>"></script>

  </body>
</html>