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

        $(document).ready(function() {
            $.ajax({
                type : "GET",
                url : newUrl,
                error : function(error) {
                    console.log("error");
                    alert("error");
                },
                success : function(data) {
                    checkIfItIsNoLoginInfo(data);
                    // beforeDrawChart(data);
                }
            });
        });

        function checkIfItIsNoLoginInfo(data) {
          if (data[0].eachTime == "noLoginInfo") {
            alert("check");
            // $.get("http://localhost:8080/RoaDiary/");
            $.ajax({
                type : "GET",
                url : "../../../account/requiredLogin/",  //@@ Url연결 안되는 부분 수정하기
                error : function(error) {
                    console.log("error");
                    alert("checkIfItIsNoLoginInfo error");
                },
                success : function(data) {
                    alert("success");
                }
            });
          } else {
            beforeDrawChart(data);
          }
        }

        function beforeDrawChart(data) {
          var dataLength = Object.keys(data).length;
          // for (var i = dataLength - 1; i >= 0 ; i--) {
          //   daysArr[i] = dateStandard.getFullYear() + "-" + ((dateStandard.getMonth() + 1) > 9 ? (dateStandard.getMonth() + 1).toString() : "0" + (dateStandard.getMonth() + 1)) + "-" + (dateStandard.getDate() > 9 ? dateStandard.getDate().toString() : "0" + dateStandard.getDate().toString());
          //   dateStandard.setDate(dateStandard.getDate() - 7);
          // }          
          var dataTime = [];
          var daysArr = [];
          for (var i = 0; i < dataLength; i++) {
            dataTime[i] = data[i].extent;
            daysArr[i] = data[i].eachTime;
          }
          drawChart(dataTime, daysArr);
        }

        function drawChart(dataTime, daysArr) {
          const ctx = document.getElementById('myChart').getContext('2d');
          const myChart = new Chart(ctx, {
              type: 'line',
              data: {
                  labels: daysArr,
                  datasets: [{
                      label: '수행 시간(분)',
                      data: dataTime,
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