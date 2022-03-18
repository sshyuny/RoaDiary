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

    <div class="position-relative">
      <div class="position-absolute top-0 end-0">
        <img src="<c:url value='/resources/image/menu.svg'/>" class="img-thumbnail" alt="">
      </div>
    </div>

    <div class="container">
      <div class="row">

        <div class="col-2" style="color:white;"><!-- 가장 큰, 왼쪽 col -->
          <form:form action="records" modelAttribute="thingsReqDto">
            <div class="row">
              <p class="text-center fs-4">새로운 기록 입력
            </div>
            <div class="row">
              <p class="text-center fs-6">날짜
              <input name="date" type="text" class="form-control mb-3" placeholder="yyyymmdd"/></p>
            </div>
            <div class="row">
              <p class="text-center fs-6">시간
              <input name="time" type="text" class="form-control mb-3" placeholder="hhmm"/></p>
            </div>
            <div class="row">
              <p class="text-center fs-6">내용
              <input name="content" type="text" class="form-control mb-3" placeholder="내용"/></p>
            </div>
            <div class="row">
              <p class="text-center fs-6">카테고리</p>
            </div>
            <div class="row justify-content-md-center">
              <input class="form-check-input" type="radio" name="category" path="category" value="1" checked />한 것
            </div>
            <div class="row justify-content-md-center">
              <input class="form-check-input" type="radio" name="category" path="category" value="2" />먹은 것
            </div>
            <div class="row justify-content-md-center">
              <input class="form-check-input mb-3" type="radio" name="category" path="category" value="3" />건강 관련
            </div>
            <div class="row">
              <p class="text-center fs-6">태그
              <input name="tag1" type="text" class="form-control" placeholder="태그1"/>
              <input name="tag2" type="text" class="form-control" placeholder="태그2"/>
              <input name="tag3" type="text" class="form-control" placeholder="태그3"/>
              <input name="tag4" type="text" class="form-control mb-3" placeholder="태그4"/></p>
            </div>
            <div class="row">
              <button type="submit" class="btn btn-dark btn-outline-primary">제출하기</button>
            </div>
          </form:form>
        </div><!-- 가장 큰, 왼쪽 col -->

        <div class="col" style="color:white;"><!-- 가장 큰, 오른쪽 col -->
          <div class="container"><!-- 이 container 안에는 두 form이 들어있습니다. -->
            <%-- 이전날, 다음날, 오늘 버튼 제어 form --%>
            <div class="row justify-content-md-center">
              <div class="col-3">
              </div>
              <div class="col-3 mb-2"><!-- 첫번째 form 포함 열 -->
                <form method="post" id="frm"  action="recordsShow">
                <input type="hidden" id="someday" name="someday" value="${stringDate}"/>
                <input type="hidden" id="onlyDate" name="onlyDate" value="${onlyDate}"/>
                  <button type="submit" class="btn btn-dark"  name="minusDate" id="minusDate">이전날</button>
                  <button type="submit" class="btn btn-dark" name="plusDate" id="plusDate" >다음날</button>
                  <button type="submit" class="btn btn-dark">오늘</button>
                </form>
              </div>
              <div class="col-4"><!-- 두번째 form  포함 열 -->
                <form method="post" action="recordsShowingCalander" onsubmit="checkCalanderDay()">
                  <input type="date" name="calanderDay" id="calanderDay" />
                  <button type="submit" class="btn btn-dark btn-outline-primary">날짜 제출</button>
                </form>
              </div>
              <div class="col-2">
              </div>
            </div>
          </div>
          <script src="<c:url value='/resources/js/recordsMain.js'/>"></script>
          
          <div class="container"><!-- container: 기록들 보여주는 부분 -->

            <div class="row mb-2"><!-- 첫번째 줄(row) -->
              <div class="col col-1">
                <figure class="text-center">
                  <p class="fw-bold text-center border rounded-3">
                    시간
                  </p>
                </figure>
              </div>
              <div class="col">
                <div class="row">
                  <div class="col col-1">
                    <p class="fw-bold text-center border rounded-3">
                      세부 <br> 시간
                    </p>
                  </div>
                  <div class="col col-2">
                    <p class="fw-bold text-center border rounded-3">
                      한 것
                    </p>
                  </div>
                  <div class="col col-2">
                    <p class="fw-bold text-center border rounded-3">
                      먹은 것
                    </p>
                  </div>
                  <div class="col col-2">
                    <p class="fw-bold text-center border rounded-3">
                      건강 관련
                    </p>
                  </div>
                  <div class="col col-2">
                    <p class="fw-bold text-center border rounded-3">
                      태그
                    </p>
                  </div>
                  <div class="col col-1">
                    <p class="fw-bold text-center border rounded-3">
                      수정
                    </p>
                  </div>
                  <div class="col col-1">
                    <p class="fw-bold text-center border rounded-3">
                      삭제
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <c:forEach var="i" begin="0" end="23">
              <div class="row"><!-- row: 0~23시까지 각 시간별 row -->
              
                <div class="col col-1 mb-1">
                  <button class="btn btn-outline-light " type="button" data-bs-toggle="collapse" data-bs-target="#eachHour${i}" aria-controls="eachHour${i}">
                    ${i}시
                  </button>
                </div>
                <!-- 빠른내용 추가 -->
                <div class="collapse" id="eachHour${i}">
                  <form id="forOnlyDatefrm" method="post" action="recordsQuickInsert" >
                    <input type="hidden" id="onlyDate" name="onlyDate" value="${onlyDate}"/>
                    <input type="hidden" id="eachHour" name="eachHour" value="${i}"/>
                    <div class="row">
                      <div class="col col-lg-2">
                        <input name="content" type="text" class="form-control" placeholder="내용"/>
                      </div>
                      <div class="col col-lg-2">  
                        <input type="radio" name="category" path="category" value="1" checked />한 것
                        <input type="radio" name="category" path="category" value="2" />먹은 것
                        <input type="radio" name="category" path="category" value="3" />건강 관련
                      </div>
                      <div class="col col-lg-2">
                        <input name="tag1" type="text" class="form-control" placeholder="태그1"/>
                        <input name="tag2" type="text" class="form-control" placeholder="태그2"/>
                        <input name="tag3" type="text" class="form-control" placeholder="태그3"/>
                        <input name="tag4" type="text" class="form-control" placeholder="태그4"/>
                      </div>
                      <div class="col col-lg-2">
                        <button type="submit" class="btn btn-dark btn-outline-primary">빠른 제출</button>
                      </div>
                    </div>
                  </form>
                </div><!-- 빠른내용 추가 닫기 -->

                <c:if test="${!empty joinThingTagResDtos}">
                <div class="col"><!-- 시간 오른쪽 col: 기록들 보여주는 부분 -->
                  <c:forEach var="i2" begin="0" end="${fn:length(joinThingTagResDtos)-1}"> <!--begin이 0부터 시작돼야 해서, end에 'joinThingTagResDtos 길이- 1' 을 넣어줌-->
                    <c:if test="${joinThingTagResDtos[i2].hour == i}">
                      <form:form action="recordsChange" modelAttribute="thingsReqDto">
                        <input type="hidden" id="onlyDate" name="onlyDate" value="${onlyDate}"/>
                        <input type="hidden" name="thingsId" value="${joinThingTagResDtos[i2].thingsId}"/>
                        <div class="row">
                          <div class="col col-1">
                            <button class="btn bg-secondary btn-outline-light mb-1" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTime${i2}" aria-expanded="false" aria-controls="collapseTime${i2}">
                              ${joinThingTagResDtos[i2].time}
                            </button>
                          </div>

                          <div class="col col-2 d-grid">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 1}">
                              <button class="btn bg-secondary btn-outline-light mb-1" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinThingTagResDtos[i2].content}
                              </button>
                            </c:if>
                          </div>
                          <div class="col col-2 d-grid">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 2}">
                              <button class="btn bg-secondary btn-outline-light mb-1" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinThingTagResDtos[i2].content}
                              </button>
                            </c:if>
                          </div>
                          <div class="col col-2 d-grid">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 3}">
                              <button class="btn bg-secondary btn-outline-light mb-1" type="button" data-bs-toggle="collapse" data-bs-target="#collapseContent${i2}" aria-expanded="false" aria-controls="collapseContent${i2}">
                                ${joinThingTagResDtos[i2].content}
                              </button>
                            </c:if>
                          </div>
                          <div class="col col-2">
                            <button class="btn bg-secondary btn-outline-light mb-1" type="button" data-bs-toggle="collapse" data-bs-target="#collapseName${i2}" aria-expanded="false" aria-controls="collapseName${i2}">
                              ${joinThingTagResDtos[i2].tagName}
                            </button>
                          </div>
                          <div class="col col-1 mb-3">
                            <button type="submit" name="save" id="save" class="btn btn-dark btn-outline-primary">수정</button>
                          </div>
                          <div class="col col-1 mb-3">
                            <button type="submit" name="delete" id="delete" class="btn btn-dark btn-outline-danger">삭제</button>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col col-lg-1">
                            <div class="collapse" id="collapseTime${i2}">
                              <input name="time" type="text" class="form-control" placeholder="hhmm"/>
                            </div>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 1}">
                              <div class="collapse" id="collapseContent${i2}">
                                <input name="content" type="text" class="form-control" placeholder="내용"/>
                              </div>
                            </c:if>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 2}">
                              <div class="collapse" id="collapseContent${i2}">
                                <input name="content" type="text" class="form-control" placeholder="내용"/>
                              </div>
                            </c:if>
                          </div>
                          <div class="col col-lg-2">
                            <c:if test="${joinThingTagResDtos[i2].categoryId == 3}">
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