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

url is  <a href="${url}">${url}</a>.

<%@include file="footer/javascript.jsp" %>
<script>
    $(document).ready(function () {
      $('.dropdown-toggle').dropdown()
    });
</script>
<%@include file="footer/analytics.jsp" %>
</body>
</html>