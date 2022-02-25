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
      <h1 style="color:white;">RoaDiary</h1>
      <h2 style="color:white;">${stringDate}의 기록</h2>
    </figure>

    <div class="container">
      <div class="row">

        <div class="col-2" style="color:white;">
          <form:form action="records" modelAttribute="thingsReqDto">
            <div class="row">
              날짜
              <input name="date" type="text" class="form-control"/>
            </div>
            <div class="row">
              시간
              <input name="time" type="text" class="form-control"/>
            </div>
            <div class="row">
              내용
              <input name="content" type="text" class="form-control"/>
            </div>
            <div class="row">
              카테고리
              <input type="radio" name="category" path="category" value="1" checked />한 일
              <input type="radio" name="category" path="category" value="2" />먹은 거
              <input type="radio" name="category" path="category" value="3" />건강
            </div>
            <div class="row">
              태그
              <input name="tag1" type="text" class="form-control"/>
              <input name="tag2" type="text" class="form-control"/>
              <input name="tag3" type="text" class="form-control"/>
              <input name="tag4" type="text" class="form-control"/>
            </div>
            <div class="row">
              <input type="submit" value="제출하기">
            </div>
          </form:form>
        </div>

        <div class="col" style="color:white;">

          <div class="container">
            <form method="post" id="frm"  action="recordsShow">
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
            </form>
          </div>
          <script src="<c:url value='/resources/js/recordsMain.js'/>"></script>

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

              <%-- show --%>
              <c:forEach var="i" begin="0" end="23">
                <div class="row">

                <div class="col col-lg-2">
                  ${i}시
                </div>
                

                <c:forEach var="i2" begin="0" end="${fn:length(joinTagTbs)-1}"> <!--begin이 0부터 시작돼야 해서, end에 'joinTagTbs 길이- 1' 을 넣어줌-->
                  <c:if test="${joinTagTbs[i2].hour == i}">
                    <form:form action="recordsChange" modelAttribute="thingsReqDto">
                      <div class="row justify-content-md-center">
                        <div class="col col-lg-2">
                          <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTime${i2}" aria-expanded="false" aria-controls="collapseTime${i2}">
                            ${joinTagTbs[i2].time}
                          </button>
                        </div>

                        <div class="col col-lg-2">
                          <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                            ${joinTagTbs[i2].content}
                          </button>
                        </div>
                        <div class="col col-lg-2">
                          <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseName${i2}" aria-expanded="false" aria-controls="collapseName${i2}">
                            ${joinTagTbs[i2].name}
                          </button>
                        </div>
                        <div class="col col-lg-1">
                          <button type="submit" class="btn btn-dark">수정 제출</button>
                        </div>
                      </div>

                      <div class="row justify-content-md-center">
                        <form:hidden path="thingsId" value="${joinTagTbs[i2].thingsId}"/>
                        <div class="col col-lg-2">
                          <div class="collapse" id="collapseTime${i2}">
                            <input name="date" type="text" class="form-control"/>
                            <input name="time" type="text" class="form-control"/>
                          </div>
                        </div>
                        <div class="col col-lg-2">
                          <div class="collapse" id="collapseContent${i2}">
                            <input name="content" type="text" class="form-control"/>
                          </div>
                        </div>
                        <div class="col col-lg-2">
                          <div class="collapse" id="collapseName${i2}">
                            <input name="tag1" type="text" class="form-control"/>
                            <input name="tag2" type="text" class="form-control"/>
                            <input name="tag3" type="text" class="form-control"/>
                            <input name="tag4" type="text" class="form-control"/>
                          </div>
                        </div>
                      </div>
                    </form:form>
                  </c:if>
                </c:forEach>

              </c:forEach>
              
            </div>
          </c:if>
          <script src="<c:url value='/resources/js/recordsMainChangeTime.js'/>"></script>

        </div>

      </div><!-- 첫번째 row -->
    </div> <!-- 첫번째 container -->

  </body>
</html>