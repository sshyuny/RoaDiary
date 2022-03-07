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
  
  <body style="background-color:black;">
    <figure class="text-center">
      <h1 style="color:white;">RoaDiary</h1>
      <h3 style="color:white;">태그 시간 분석</h3>
      <p style="color:white;">12주 동안 "${tag}"의 사용 시간(분)</p>
    </figure>

    <c:forEach var="i" begin="0" end="11">
      <input type="hidden" id="week${i}" value="${arrayTime[i]}"/>
    </c:forEach>
    <br>

    <div style="width:600px; height:600px;" class="mx-auto">
      <canvas id="myChart" width="100" height="100"></canvas>
      <script>
        
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
            type: 'line',
            data: {
                labels: ['week0', 'week1', 'week2', 'week3', 'week4', 'week5', 'week6', 'week7', 'week8', 'week9', 'week10', 'week11'],
                datasets: [{
                    label: '수행 시간(분)',
                    data: [week0, week1, week2, week3, week4, week5, week6, week7, week8, week9, week10, week11],
                    fill: false,
                    borderColor: 'rgb(255, 99, 132)',
                    tension: 0.1
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