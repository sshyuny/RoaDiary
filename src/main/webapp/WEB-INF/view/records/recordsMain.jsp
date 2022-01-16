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
    <input type="button" id="minusDate" value="이전날" />
    <input type="button" id="plusDate" value="다음날" />
    <input type="button" id="todayDate" value="오늘" /> 
    <input type="date" id="selectDate" />
    <form method="post" id="frm">
      <input type="hidden" id="someday" name="someday" value=""/>
      <input type="button" id="selectDateBt" value="제출"/>

      <script type="text/javascript">

        function fromDatetoString(yD, mD, dD) {
          var dateS;
          yearString = yD.toString();
          if(mD <= 9) {
            monthString = "0" + mD.toString();
          } else {
            monthString = mD.toString();
          }
          if(dD <= 9) {
            dayString = "0" + dD.toString();
          } else {
            dayString = dD.toString();
          }
          var dateS = yearString + "-" + monthString + "-" + dayString;
          return dateS;
        }

        function plusDateUrl() {
          var url = new URL(location).searchParams;
          var pParam = Number(url.get("p"));
          var mParam = Number(url.get("m"));
          pParam += 1;
          newUrl = "recordsShow?p=" + pParam + "&m=" + mParam;
          return newUrl;
        }
        function minusDateUrl() {
          var url = new URL(location).searchParams;
          var pParam = Number(url.get("p"));
          var mParam = Number(url.get("m"));
          mParam += 1;
          newUrl = "recordsShow?p=" + pParam + "&m=" + mParam;
          return newUrl;
        }

        function setNewValue(newDate) {
          //
          var yearMinus = newDate.getFullYear();
          var monthMinus = newDate.getMonth()+1;
          var dayMinus = newDate.getDate();
          var dateString = fromDatetoString(yearMinus, monthMinus, dayMinus);
          document.getElementById('someday').value = dateString;
        }

        window.onload = function() {
          var date = new Date();
          var year = date.getFullYear();
          var month = date.getMonth()+1;
          var day = date.getDate();
          document.getElementById('selectDate').value = fromDatetoString(year, month, day);
          
          document.getElementById('todayDate').onclick = function() {
            var dateString = fromDatetoString(year, month, day);
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').action="recordsShow?p=0&m=0";
            document.getElementById('frm').submit();
            //location.href="./recordsShow?p=0m=0";
          }

          document.getElementById('minusDate').onclick = function() {
            var url = new URL(location).searchParams;
            var pmParam = Number(url.get("p")) - Number(url.get("m"));

            var newDate = new Date(year, month-1, day+pmParam-1);
            setNewValue(newDate);
            

            var newUrl = minusDateUrl();
            document.getElementById('frm').action=newUrl;
            document.getElementById('frm').submit();
          }

          document.getElementById('plusDate').onclick = function() {
            var url = new URL(location).searchParams;
            var pmParam = Number(url.get("p")) - Number(url.get("m"));

            var newDate = new Date(year, month-1, day+pmParam+1);
            setNewValue(newDate);
            

            var newUrl = plusDateUrl();
            document.getElementById('frm').action=newUrl;
            document.getElementById('frm').submit();
          }

          document.getElementById('selectDateBt').onclick = function() {
            var dateString = document.getElementById('selectDate').value;
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').action="recordsShow?p=0&m=0";
            document.getElementById('frm').submit();
          }

        }

    </script>
    </form>
    </p>
    

    <c:if test="${!empty joinTagTbs}">
    <table>
      <tr>
        <th>시간</th><th>내용</th><th>태그</th>
      </tr>
      
      <tr>
      <c:forEach var="i" begin="0" end="${fn:length(joinTagTbs)}">
        <td><c:out value="${joinTagTbs[i].time}"/></td>
        <td><c:out value="${joinTagTbs[i].content}"/></td>
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