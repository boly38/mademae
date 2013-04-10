<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="EN">
<head>
<%@include file="header/head.jsp" %>
</head>
<body>
<div id="mainMenu">&#160;</div>
<div class="container" style="margin-top:2.7%;">
    <div id="feedback" class="row">
    </div>
    <div id="mainPanel">
      <div id="mainContent">
            <div class="row well">
                Welcome on MaD&eacutemocratie ${mademocratie.version}
            </div>
        </div>
    </div>
</div>

<%@include file="footer/javascript.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
        md.init("mainMenu", "mainContent", "feedback");
    });
</script>
<%@include file="footer/analytics.jsp" %>
</body>
</html>