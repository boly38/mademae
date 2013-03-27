<%--
Placed at the top of the document
--%><%@ page contentType="text/html;charset=UTF-8" language="java" %><!-- Navbar
================================================== -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="/">Ma d&eacute;mocratie</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class=""><a href="javascript:md.home()"><i class="icon-home icon-white"></i>&#160;home</a></li>
                    <li class=""><a href="javascript:md.addSampleProposition()"><i class="icon-home icon-plus-sign"></i>&#160;add a sample</a></li>
                </ul>
                <ul class="nav pull-right">
                    <!-- *** help menu -->
                    <li class="dropdown pull-right" id="help-menu-dropdown">
                        <a href="#help-menu-dropdown" class="dropdown-toggle">
                            <i class="icon-question-sign icon-white"></i> Help <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:md.about()">About</a></li>
                            <li><a href="javascript:md.contact()"><i class="icon-envelope"></i> Contact</a></li>
                        </ul>
                    </li><!-- *** /help menu -->
                </ul>
            </div>
        </div>
    </div>
</div>