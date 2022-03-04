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
    <a href="./sorting?category=1&tag=${listCategory1[0].tagName}" class="btn btn-secondary" role="button">${listCategory1[0].tagName}</a>
    ${listCategory1[0].frequency}회 기록됨
    <a href="./sorting?category=1&tag=${listCategory1[1].tagName}" class="btn btn-secondary" role="button">${listCategory1[1].tagName}</a>
    ${listCategory1[1].frequency}회 기록됨
    <a href="./sorting?category=1&tag=${listCategory1[2].tagName}" class="btn btn-secondary" role="button">${listCategory1[2].tagName}</a>
    ${listCategory1[2].frequency}회 기록됨
    <br>
    <a href="./sorting?category=2&tag=${listCategory2[0].tagName}" class="btn btn-dark" role="button">${listCategory2[0].tagName}</a>
    <a href="./sorting?category=2&tag=${listCategory2[1].tagName}" class="btn btn-dark" role="button">${listCategory2[1].tagName}</a>
    <a href="./sorting?category=2&tag=${listCategory2[2].tagName}" class="btn btn-dark" role="button">${listCategory2[2].tagName}</a>
    <br>
    <a href="./sorting?category=3&tag=${listCategory3[0].tagName}" class="btn btn-secondary" role="button">${listCategory3[0].tagName}</a>
    <a href="./sorting?category=3&tag=${listCategory3[1].tagName}" class="btn btn-secondary" role="button">${listCategory3[1].tagName}</a>
    <a href="./sorting?category=3&tag=${listCategory3[2].tagName}" class="btn btn-secondary" role="button">${listCategory3[2].tagName}</a>

    <div style="width:600px; height:600px;">
      <canvas id="myChart" width="100" height="100"></canvas>
      <script>
        const ctx = document.getElementById('myChart').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
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