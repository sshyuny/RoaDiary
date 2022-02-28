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

        <div class="col-2" style="color:white;"><!-- 가장 큰, 왼쪽 col -->
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
        </div><!-- 가장 큰, 왼쪽 col -->

        <div class="col" style="color:white;"><!-- 가장 큰, 오른쪽 col -->

          <div class="container">
            <form method="post" id="frm"  action="recordsShow">
              <div class="row justify-content-md-center">
                <div class="col-auto">
                  <button type="button" class="btn btn-dark" id="minusDate" onclick="returnMinusDate()">이전날</button>
                </div>
                <div class="col-auto">
                  <button type="button" class="btn btn-dark" id="plusDate" onclick="returnPlusDate()">다음날</button>
                </div>
                <div class="col-auto">
                  <button type="button" class="btn btn-dark" id="todayDate" onclick="returnTodayDate()">오늘</button>
                </div>
                <div class="col col-2">
                  <input type="date" id="selectDate" />
                </div>
                <div class="col col-1">
                  <input type="button" id="selectDateBt" value="제출"/>
                </div>
                <input type="hidden" id="someday" name="someday" value="${stringDate}"/>
              </div>
            </form>
          </div>
          <script src="<c:url value='/resources/js/recordsMain.js'/>"></script>

          
          <div class="container"><!-- container: 기록들 보여주는 부분 -->

            <div class="row"><!-- 첫번째 줄(row) -->
              <div class="col col-1">
                시간
              </div>
              <div class="col">
                <div class="row">
                  <div class="col col-1">
                    세부
                  </div>
                  <div class="col col-2">
                    한일
                  </div>
                  <div class="col col-2">
                    먹은거
                  </div>
                  <div class="col col-2">
                    건강
                  </div>
                  <div class="col col-2">
                    태그
                  </div>
                  <div class="col col-1">
                    수정
                  </div>
                </div>
              </div>
            </div>

            <c:forEach var="i" begin="0" end="23">
              <div class="row"><!-- row: 0~23시까지 각 시간별 row -->
              
                <div class="col col-1 bg-success bg-opacity-50 mb-1">
                  <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#eachHour${i}" aria-controls="eachHour${i}">
                    ${i}시
                  </button>
                </div>
                <!-- 빠른내용 변경 -->
                <div class="collapse" id="eachHour${i}">
                  <form:form action="recordsQuickInsert" modelAttribute="thingsReqDto">
                    <form:hidden path="thingsId" value="${joinTagTbs[i2].thingsId}"/>
                    <input type="hidden" id="eachHour" name="eachHour" value="${i}"/>
                    <div class="row">
                      <div class="col col-lg-2">
                        <input name="content" type="text" class="form-control" placeholder="내용"/>
                      </div>
                      <div class="col col-lg-2">
                        <input type="radio" name="category" path="category" value="1" checked />한 일
                        <input type="radio" name="category" path="category" value="2" />먹은 거
                        <input type="radio" name="category" path="category" value="3" />건강
                      </div>
                      <div class="col col-lg-2">
                        <input name="tag1" type="text" class="form-control" placeholder="태그1"/>
                        <input name="tag2" type="text" class="form-control" placeholder="태그2"/>
                        <input name="tag3" type="text" class="form-control" placeholder="태그3"/>
                        <input name="tag4" type="text" class="form-control" placeholder="태그4"/>
                      </div>
                      <div class="col col-lg-2">
                        <button type="submit" class="btn btn-dark">빠른 기록 제출</button>
                      </div>
                    </div>
                  </form:form>
                </div><!-- 빠른내용 변경 닫기 -->

                <c:if test="${!empty joinTagTbs}">
                <div class="col"><!-- 시간 오른쪽 col: 기록들 보여주는 부분 -->
                  <c:forEach var="i2" begin="0" end="${fn:length(joinTagTbs)-1}"> <!--begin이 0부터 시작돼야 해서, end에 'joinTagTbs 길이- 1' 을 넣어줌-->
                    <c:if test="${joinTagTbs[i2].hour == i}">
                      <form:form action="recordsChange" modelAttribute="thingsReqDto">
                        <div class="row">
                          <div class="col col-1">
                            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTime${i2}" aria-expanded="false" aria-controls="collapseTime${i2}">
                              ${joinTagTbs[i2].time}
                            </button>
                          </div>

                          <div class="col col-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 1}">
                              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinTagTbs[i2].content}
                              </button>
                            </c:if>
                          </div>
                          <div class="col col-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 2}">
                              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinTagTbs[i2].content}
                              </button>
                            </c:if>
                          </div>
                          <div class="col col-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 3}">
                              <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinTagTbs[i2].content}
                              </button>
                            </c:if>
                          </div>

                          <div class="col col-2">
                            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseName${i2}" aria-expanded="false" aria-controls="collapseName${i2}">
                              ${joinTagTbs[i2].name}
                            </button>
                          </div>
                          <div class="col col-2 mb-3">
                            <button type="submit" class="btn btn-dark">수정 제출</button>
                          </div>
                        </div>

                        <div class="row">
                          <form:hidden path="thingsId" value="${joinTagTbs[i2].thingsId}"/>
                          <div class="col col-lg-1">
                            <div class="collapse" id="collapseTime${i2}">
                              <%-- <input name="date" type="text" class="form-control"/> --%>
                              <input name="time" type="text" class="form-control" placeholder="시간"/>
                            </div>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 1}">
                              <div class="collapse" id="collapseContent${i2}">
                                <input name="content" type="text" class="form-control" placeholder="내용"/>
                              </div>
                            </c:if>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 2}">
                              <div class="collapse" id="collapseContent${i2}">
                                <input name="content" type="text" class="form-control" placeholder="내용"/>
                              </div>
                            </c:if>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinTagTbs[i2].categoryId == 3}">
                              <div class="collapse" id="collapseContent${i2}">
                                <input name="content" type="text" class="form-control" placeholder="내용"/>
                              </div>
                            </c:if>
                          </div>
                          <div class="col col-lg-2">
                            <div class="collapse" id="collapseName${i2}">
                              <input name="tag1" type="text" class="form-control" placeholder="태그1"/>
                              <input name="tag2" type="text" class="form-control" placeholder="태그2"/>
                              <input name="tag3" type="text" class="form-control" placeholder="태그3"/>
                              <input name="tag4" type="text" class="form-control" placeholder="태그4"/>
                            </div>
                          </div>
                        </div>
                        <div class="col col-lg-2">
                          <div class="collapse" id="collapseName${i2}">
                            <!-- 위 버튼과 간격 맞추기 위해, 빈 공간 넣어둠 -->
                          </div>
                        </div>
                      </form:form>
                    </c:if>
                  </c:forEach>
                  
                </div><!-- 시간 오른쪽 col: 기록들 보여주는 부분 -->
                </c:if>

              </div><!-- row: 0~23시까지 각 시간별 row -->
            </c:forEach>
            
          </div><!-- container: 기록들 보여주는 부분 -->

          <script src="<c:url value='/resources/js/recordsMainChangeTime.js'/>"></script>

        </div><!-- 가장 큰, 오른쪽 col -->

      </div><!-- 첫번째 row -->
    </div> <!-- 첫번째 container -->

  </body>
</html>