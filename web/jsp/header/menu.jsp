<%--
Placed at the top of the document
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse navbar-fixed-top" id="topmenu">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="/">Ma d&eacute;mocratie</a>
            <div class="nav-collapse collapse">
                <ul class="nav nav-pills"><!--  class="active" for the current menu  -->
                    <li><a href="javascript:md.home()"><i class="icon-home icon-white"></i>&#160;home</a></li>
                </ul>
                <ul class="nav pull-right">
                    <!-- *** helpmenu -->
                    <li class="dropdown pull-right" id="help-menu-dropdown">
                        <a href="#help-menu-dropdown" class="dropdown-toggle">
                            <i class="icon-question-sign icon-white"></i> Help <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:md.about()">About</a></li>
                            <li><a href="javascript:md.contact()"><i class="icon-envelope"></i> Contact</a></li>
                        </ul>
                    </li><!-- *** /helpmenu -->
                </ul>
            </div>
        </div>
    </div>
</div>