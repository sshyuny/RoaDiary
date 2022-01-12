<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form action="recordsShow" method="post" id="frm">
      <input type="hidden" id="someday" name="someday" value=""/>
      <input type="button" id="minusDate" onclick="minusDate()" />어제
      <input type="button" id="todayDate" onclick="todayDate()" />오늘
      <input type="button" id="plusDate" onclick="plusDate()" />내일
      <input type="date" id="selectDate" onclick="selectDate()"/>

      <script type="text/javascript">

        function fromDatetoString(yD, mD, dD) {
          var dateS;
          yearString = yD.toString();
          if(mD <= 9) {
            monthString = "0" + mD.toString();
          } else {
            monthString = mD.toString();
          }
          dayString = dD.toString();
          var dateS = yearString + "-" + monthString + "-" + dayString;
          return dateS;
        }

        var a=0;
        window.onload = function() {
          var date = new Date();
          var year = date.getFullYear();
          var month = date.getMonth()+1;
          var day = date.getDate();
          
          document.getElementById('todayDate').onclick = function() {
            alert("check");
            var dateString = fromDatetoString(year, month, day);
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').submit();
          }

          document.getElementById('minusDate').onclick = function() {
            a -= 1;
            var newDate = new Date(year, month-1, day+a);
            var yearMinus = newDate.getFullYear();
            var monthMinus = newDate.getMonth()+1;
            var dayMinus = newDate.getDate();
            var dateString = fromDatetoString(yearMinus, monthMinus, dayMinus);
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').submit();
          }
        }

    </script>
    </form>
    </p>
    

    <c:if test="${!empty thingsTbs}">
    <table>
      <tr>
        <th>시간</th><th>내용</th>
      </tr>
      <c:forEach var="thing" items="${thingsTbs}">
      <tr>
        <td>${thing.time}</td>
        <td>${thing.content}</td>
      </tr>
      </c:forEach>
    </table>
    </c:if>

  </body>
</html>