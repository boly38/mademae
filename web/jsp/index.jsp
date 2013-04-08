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
        <div class="alert fade in">
            <button type="button" class="close" data-dismiss="alert">x</button>
            <strong><i class="icon-warning-sign"></i> Warning!</strong> This website is an alpha version. See also <a href="javascript:md.about()">about page</a> for more details
            (<a href="http://code.google.com/p/ma-dem-ae/wiki/MaDemAe">MaDemAe project</a>).
        </div>
    </div>
    <div id="mainPanel">
      <div id="mainContent">
            <div class="row well">
                Welcome on MaD&eacutemocratie ${mademocratie.version} - ${version}
            </div>
        </div>
    </div>
</div>

<%@include file="footer/javascript.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
        md.init("mainMenu", "mainContent");
    });
</script>
<%@include file="footer/analytics.jsp" %>
</body>
</html>