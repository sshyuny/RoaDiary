<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
  <head>
    <title>기록 정리</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
  </head>
  
  <body>
    <label>
      카테고리:
      <input type="radio" name="category" path="category" value="1" checked />한 일
      <input type="radio" name="category" path="category" value="2" />먹은 거
      <input type="radio" name="category" path="category" value="3" />건강
      <br>
    </label>

    <br>
    첫 주에 ${arrayTime[0]}분 함
    둘째 주에 ${arrayTime[1]}분 함
    <%-- <button type="button" class="btn btn-dark" id="test" value="${arrayFrequency[0]}" onclick="test(this.value)">test</button> --%>
    <%-- <input type="hidden" id="week1" value="${arrayFrequency[0]}"/> --%>
    <c:forEach var="i" begin="0" end="11">
      <input type="hidden" id="week${i}" value="${arrayTime[i]}"/>
    </c:forEach>
    <br>

    <div style="width:600px; height:600px;">
      <canvas id="myChart" width="100" height="100"></canvas>
      <script>
        // function test(value) {
        //   alert(value);
        // }
        
        var week0 = document.getElementById('week0').value;
        var week1 = document.getElementById('week1').value;
        var week2 = document.getElementById('week2').value;
        var week3 = document.getElementById('week3').value;
        var week4 = document.getElementById('week4').value;
        var week5 = document.getElementById('week5').value;
        var week6 = document.getElementById('week6').value;
        var week7 = document.getElementById('week7').value;
        var week8 = document.getElementById('week8').value;
        var week9 = document.getElementById('week9').value;
        var week10 = document.getElementById('week10').value;
        var week11 = document.getElementById('week11').value;

        const ctx = document.getElementById('myChart').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['week0', 'week1', 'week2', 'week3', 'week4', 'week5', 'week6', 'week7', 'week8', 'week9', 'week10', 'week11'],
                datasets: [{
                    label: '# of Votes',
                    data: [week0, week1, week2, week3, week4, week5, week6, week7, week8, week9, week10, week11],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
      </script>
    </div>

    

  </body>
</html>