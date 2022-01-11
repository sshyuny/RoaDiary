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
        window.onload = function() {
          var date = new Date().toLocaleDateString();
          document.getElementById('todayDate').onclick = function() {
            document.getElementById('someday').value = date;
            // alert(date); // yyyy.MM.dd. 형식으로 반환됨
            document.getElementById('frm').submit();
          }
        }

        /*function minusDate() {
          date.setDate(date.getDate()-1);
          document.getElementById('someday').value=date;
          //document.uesrinput.action="recordsShow";
        }
        function todayDate() {
          date.setDate(Date());
          document.getElementById('someday').value=new Date();
          document.todayDate.action="recordsShow";
        }
        function plusDate() {
          date.setDate(date.getDate()+1);
          document.getElementById('someday').value=date;
          //document.uesrinput.action="recordsShow";
        }
        function selectDate() {
          date=docment.getElementById('selectDate').value;
          document.getElementById('someday').value=date;
          //document.uesrinput.action="recordsShow";
        }*/
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