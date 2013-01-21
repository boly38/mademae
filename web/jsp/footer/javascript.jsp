<%--
Placed at the end of the document so the pages load faster
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="/js/${jquery_version}.js"></script>
<script src="/${bootstrap_version}/js/bootstrap.js"></script>
<script src="/${bootstrap_version}/js/bootstrap-alert.js"></script>
<script src="/${bootstrap_version}/js/bootstrap-button.js"></script>
<script src="/${bootstrap_version}/js/bootstrap-dropdown.js"></script>
<script src="/${bootstrap_version}/js/bootstrap-typeahead.js"></script>
<script>
    $(document).ready(function () {
    $('.dropdown-toggle').dropdown()
    });
</script>
</html>