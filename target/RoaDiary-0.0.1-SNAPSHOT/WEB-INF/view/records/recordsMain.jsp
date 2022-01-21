<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
  <head>
    <title>기록</title>
    
  </head>
  <body>
    <h1>오늘의 기록</h1>
    <p>
      <form:form action="records" modelAttribute="thingsReqDto">
        <label>
          시간:
          <form:input path="time"/>
          <br>
        </label>
        <label>
          내용:
          <form:input path="content" />
          <br>
        </label>
        <label>
          카테고리:
          <input type="radio" name="category" path="category" value="1" checked />한 일
          <input type="radio" name="category" path="category" value="2" />먹은 거
          <input type="radio" name="category" path="category" value="3" />건강
          <br>
        </label>
        <label>
          태그:
          <form:input path="tag1"/>
          <form:input path="tag2"/>
          <form:input path="tag3"/>
          <form:input path="tag4"/>
          <br>
        </label>
        <input type="submit" value="제출하기">
      </form:form>
    </p>

      
    <p>
    <form method="post" id="frm"  action="recordsShow">

      <input type="button" id="minusDate" value="이전날" onclick="returnMinusDate()"/>
      <input type="button" id="plusDate" value="다음날"  onclick="returnPlusDate()"/>
      <input type="button" id="todayDate" value="오늘"  onclick="returnTodayDate()"/> 
      <input type="date" id="selectDate" />
      <input type="hidden" id="someday" name="someday" value="${stringDate}"/>


      <input type="button" id="selectDateBt" value="제출"/>
      
      
    </form>
    <script src="<c:url value='/resources/js/recordsMain.js'/>"></script>
    </p>
    

    <c:if test="${!empty joinTagTbs}">
    <table>
      <tr>
        <th>시간</th><th>내용</th><th>태그</th>
      </tr>
      
      <tr>
      <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)}">
        <td value="${joinTagTbs[i].thingsId}" id="${joinTagTbs[i].thingsId}" onclick="changeTime()"><c:out value="${joinTagTbs[i].time}"/></td>
        <td value="${joinTagTbs[i].thingsId}" onclick="changeContent()"><c:out value="${joinTagTbs[i].content}"/></td>
        <c:choose>
          <c:when test="${joinTagTbs[i].thingsId == joinTagTbs[i+1].thingsId}">
            <td><c:out value="${joinTagTbs[i].name}"/></td>
          </c:when>
          <c:otherwise>
            <td><c:out value="${joinTagTbs[i].name}"/></td>
        </tr>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </table>
    </c:if>

  </body>
</html>