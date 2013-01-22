<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="EN">
<head>
<%@include file="header/head.jsp" %>
</head>
<body>
<%@include file="header/menu.jsp" %>

<div id="aboutDiv">
   aboutdiv
</div>
<%@include file="footer/javascript.jsp" %>
<script>
  function async_get(addr,func) {
    $.ajax({
    url: addr,
    success: func
    });
  }

  $(document).ready(function () {
    $('.dropdown-toggle').dropdown()
    /*  async_get('http://localhost:8080/json/about',function(data){$('#aboutDiv').html(data)});
     */
    $.getJSON('json/about', function(data) {
        $('#aboutDiv').html(data.version)
    });
  });

</script>
<%@include file="footer/analytics.jsp" %>
</body>
</html>