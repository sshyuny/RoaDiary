<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
  <head>
    <title>기록 정리</title>
    <!-- chart.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    <!-- bootstrap -->
    <link href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
    <!-- ajax -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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
        var pathfull = new URL(window.location.href);
        // var pathParam = pathfull.searchParams.get('tag');
        // var pathNameStr = pathfull.pathname;
        var pathNameArr = pathfull.pathname.split("/");
        var pArrLength = pathNameArr.length;
        // var newUrl = pathfull.hostname + ":8080/RoaDiary/getSortingTime/" + pathNameArr[pArrLength - 3] + "/" + pathNameArr[pArrLength - 2] + "/" + pathNameArr[pArrLength - 1];
        var newUrl = "../../../getSortingTime/" + pathNameArr[pArrLength - 3] + "/" + pathNameArr[pArrLength - 2] + "/" + pathNameArr[pArrLength - 1];
        alert(newUrl);

        $(document).ready(function() {
            $.ajax({
                type : "GET",
                url : newUrl,
                error : function(error) {
                    console.log("error");
                    alert("error");
                },
                success : function(data) {
                    alert("check " + data.week0 + " " + data.week2 + " " + data.week3 + " " + data.week4);
                    drawChart(data);
                }
            });
        });

        function drawChart(data) {
          const ctx = document.getElementById('myChart').getContext('2d');
          const myChart = new Chart(ctx, {
              type: 'line',
              data: {
                  labels: ['week0', 'week1', 'week2', 'week3', 'week4', 'week5', 'week6', 'week7', 'week8', 'week9', 'week10', 'week11'],
                  datasets: [{
                      label: '수행 시간(분)',
                      data: [data.week0, data.week1, data.week2, data.week3, data.week4, data.week5, data.week6, data.week7, data.week8, data.week9, data.week10, data.week11],
                      // data: dataArr,
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
        }
        
      </script>
    </div>

    

  </body>
</html>