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
    <script src="<c:url value='/resources/lib/bootstrap/popper.min.js'/>"></script>
    <script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>
    <!-- ajax -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  </head>
  
  <body style="background-color:black;">

    테스트

    <script type="text/javascript">

        $(document).ready(function() {
          
            $.ajax({
                type : "GET",
                url : "get.do",
                data : {
                    kor : "${kor}",
                    us : "${us}"
                },
                error : function(error) {
                    console.log("error");
                },
                success : function(data) {
                    console.log("success");
                    alert("check");
                    alert(data);
                }
            });
        });

        function clicktest() { 
          $.ajax({ 
              type: "GET", 
              url: "get.do",
              data: {
                  kor : "${kor}",
                  us : "${us}"
              }, error : function(error) {
                  console.log("error");
              },
              success: function (data) { 
                  alert(data);
                  alert("clicktest"); 
                  } 
              })
          }

    </script>

    <h1>Ajax Test</h1>
    <div id="button_test">
        <button type="button" id="ajaxBtn" onclick="clicktest()">Ajax Test</button>
    </div>

  </body>
</html>