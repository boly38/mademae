// jQuery plugin to serialize in json a form object
(function( $ ){
    $.fn.serializeJSON=function() {
        var json = {};
        jQuery.map($(this).serializeArray(), function(n, i){
            json[n['name']] = n['value'];
        });
    return json;
    };
})( jQuery );

// MaDemocratie instance
var md = new MaDemocratie();

function Home() {
    this.init= function(mainDivId) {
        this.track("home");
        this.mainDivId = mainDivId;
        this.showHome();
    };

    this.showHome= function() {
        var parentHome = this;
        $.getJSON('json/contribution/last', function(contributionsJsonData) {
            $.get('/js-templates/contributions.html', function(contributionsTemplate) {
                $.template("contributionsTemplate", contributionsTemplate);
                var contributionsHtmlResult = $.tmpl("contributionsTemplate", contributionsJsonData);
                parentHome.updateContent(contributionsHtmlResult);
            });
        });
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}



function AddProposalForm() {
    this.init= function(mainDivId) {
        this.mainDivId = mainDivId;
        this.showForm();
    };

    this.showForm= function() {
        var parentAP = this;
        $.get('/js-templates/addProposal.html', function(addProposalTemplate) {
            $.template("addProposalTemplate", addProposalTemplate);
            var addProposalHtmlResult = $.tmpl("addProposalTemplate", "");
            parentAP.updateContent(addProposalHtmlResult);
        });
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}


function ProposalDetails() {
    this.init= function(mainDivId, proposalId) {
        this.mainDivId = mainDivId;
        this.proposalId = proposalId;
        this.showDetails();
    };

    this.showDetails= function() {
        var parentPD = this;
        $.getJSON('json/proposal/proposal/' + this.proposalId, function(proposalJsonData) {
            $.get('/js-templates/proposal.html', function(proposalTemplate) {
                $.template("proposalTemplate", proposalTemplate);
                var proposalHtmlResult = $.tmpl("proposalTemplate", proposalJsonData);
                parentPD.updateContent(proposalHtmlResult);
            });
        });
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}

// MaDemocratie object
function MaDemocratie() {
    this.setup= function() {
        $.ajaxSetup({ cache:false });
        $.ajaxSetup({
                error: function(jqXHR, exception) {
                    if (jqXHR.status === 0) {
                        md.warn('Not connect.\n Verify Network.');
                    } else if (jqXHR.status == 404) {
                        md.warn('Requested page not found. [404]');
                    } else if (jqXHR.status == 500) {
                        md.warn('Internal Server Error [500].');
                    } else if (exception === 'parsererror') {
                        md.warn('Requested JSON parse failed.');
                    } else if (exception === 'timeout') {
                        md.warn('Time out error.');
                    } else if (exception === 'abort') {
                        md.warn('Ajax request aborted.');
                    } else {
                        md.warn('Uncaught Error.\n' + jqXHR.responseText);
                    }
                }
        });
        $.ajaxSetup({ beforeSend: md.ajaxBeforeSend });
    };

    this.hasToken = function() {
        return (typeof md.token != 'undefined');
    };

    this.ajaxBeforeSend = function(xhr){
        if (md.hasToken()) {
            xhr.setRequestHeader('md-authentification', md.token);
        }
    };

    this.init= function(mainMenuDivId, mainDivId, feedbackDivId) {
        var parentMD = this;
        this.setup();
        this.mainMenuDivId = mainMenuDivId;
        this.mainDivId = mainDivId;
        this.feedbackDivId = feedbackDivId;
        this.feedbackMessages = [];
        this.menu(function(){
            parentMD.afterInit();
        });
    };

    this.afterInit = function() {
        $('.dropdown-toggle').dropdown();
        if ($.urlParameter('redirect') == "login") {
            this.login();
        } else {
            this.welcome();
            this.home();
        }
    };

    this.menu= function(callback) {
        var parentMD = this;
        if (this.hasToken()) {
            $.getJSON('json/citizen/menu', function(menuJsonData) {
              $.get('/js-templates/mainMenu.html', function(mainMenuTemplate) {
                $.template("mainMenuTemplate", mainMenuTemplate);
                var mainMenuHtmlResult = $.tmpl("mainMenuTemplate", menuJsonData);
                parentMD.updateMenu(mainMenuHtmlResult);
                callback();
                });
            });
        } else {
          $.get('/js-templates/mainMenu.html', function(mainMenuTemplate) {
            $.template("mainMenuTemplate", mainMenuTemplate);
            var mainMenuHtmlResult = $.tmpl("mainMenuTemplate", "");
            parentMD.updateMenu(mainMenuHtmlResult);
            callback();
            });
        }
    };

    this.login= function() {
        var parentMd = this;
        $.getJSON('json/citizen/login', function(loginJsonData) {
            $.get('/js-templates/login.html', function(loginTemplate) {
                $.template("loginTemplate", loginTemplate);
                var loginHtmlResult = $.tmpl("loginTemplate", loginJsonData);
                parentMd.updateContent(loginHtmlResult);
            });
        });
        this.track("login");
    };

    this.welcome= function() {
        this.warn("This website is an alpha version. See also <a href='javascript:md.about()'>about page</a> for more details (<a href='http://code.google.com/p/ma-dem-ae/wiki/MaDemAe'>MaDemAe project</a>)");
    }

    this.home= function() {
        var parentMd = this;
        $.getJSON('json/contribution/last', function(contributionsJsonData) {
            $.get('/js-templates/contributions.html', function(contributionsTemplate) {
                $.template("contributionsTemplate", contributionsTemplate);
                var contributionsHtmlResult = $.tmpl("contributionsTemplate", contributionsJsonData);
                parentMd.updateContent(contributionsHtmlResult);
            });
        });
        this.track("home");
    };

    this.addSampleProposal= function() {
        var parentMd = this;
        $.getJSON('json/contribution/addSample', function(addSampleJsonData) {
            /* console.info(addSampleJsonData); */
            $.get('/js-templates/jsonServiceResponse.html', function(htmlTemplate) {
                $.template("jsonServiceResponse", htmlTemplate);
                var jsonServiceResponseHtmlResult = $.tmpl("jsonServiceResponse", addSampleJsonData);
                parentMd.updateContent(jsonServiceResponseHtmlResult);
            });
        });
        this.track("addSampleProposal");
    };

    this.addProposalAction= function() {
        this.addProposalForm = new AddProposalForm();
        this.addProposalForm.init(this.mainDivId);
        this.track("addProposalAction");
    };

    this.addProposal= function(addProposalFormId) {
         var addProposalEndPoint = "json/proposal/add";
         var proposalData = $("#" + addProposalFormId).serializeJSON();
         var title = proposalData.title;
         $.ajax({
           type: "POST",
           url: addProposalEndPoint,
           data: JSON.stringify(proposalData),
           dataType: "json",
           contentType: 'application/json',
           success: function() {
             md.info("you just add a proposal (title:" + title + ")");
             md.home();
           }
         });
        this.track("addProposal");
    };

    this.signInGoogle=function(signInFormUsingGoogleFormId) {
         var signInGoogleEndPoint = "json/citizen/signIn";
         var signInData = $("#" + signInFormUsingGoogleFormId).serializeJSON();
         $.ajax({
           type: "POST",
           url: signInGoogleEndPoint,
           data: JSON.stringify(signInData),
           dataType: "json",
           contentType: 'application/json',
           success: function(data, textStatus, jqXHR) {
             /* console.info(data); */
             if (data.status == "FAILED") {
                md.warn(data.message);
                md.login();
             } else {
                var login = data.userPseudo;
                md.token = data.authToken;
                md.info("welcome =) " + login);
                md.menu(function(){
                    $('.dropdown-toggle').dropdown();
                    md.home();
                });
             }
           }
         });
        this.track("signInGoogle");
    };
    this.signIn=function(signInFormId) {
        this.warn("not yet implemented!");
        this.track("signIn");
    };

    this.logout=function() {
        delete md.token;
        // md.info("bye bye =) ");
        md.clear();
        md.menu(function(){
            $('.dropdown-toggle').dropdown();
            md.home();
        });
    };

    this.contact= function() {
        this.updateContent("<div class='row well'><h4>Contact</h4><p>info - at - mademocratie (dot) net</i> should be able to answer ! ;)</p></div>");
        this.track("contact");
    };

    this.about= function() {
        var parentMd = this;
        $.getJSON('json/about/info', function(aboutJsonData) {
            $.get('/js-templates/about.html', function(htmlTemplate) {
                $.template("aboutHtmlTemplate", htmlTemplate);
                var aboutHtmlResult = $.tmpl("aboutHtmlTemplate", aboutJsonData);
                parentMd.updateContent(aboutHtmlResult);
            });
        });
        this.track("about");
    };

    this.contribution = function(contributionId, contributionType) {
        if (contributionType == 'PROPOSAL') {
            this.proposalDetails = new ProposalDetails();
            this.proposalDetails.init(this.mainDivId, contributionId);
            this.track("proposalDetails", contributionId);
        }
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
    this.updateMenu= function(htmlContent) {
            $('#' + this.mainMenuDivId).html(htmlContent);
    };
    this.info = function(infoMessage) {
        var htmlContent = "<div class='alert alert-info'>"
                    + "<strong><i class='icon-info-sign'></i> Info!</strong>&#160;&#160;"
                    + infoMessage
                    + "</div>";
        this.feedback(htmlContent);
    };

    this.warn = function(warningMessage) {
        var htmlContent = "<div class='alert fade in'>"
                    + "<strong><i class='icon-warning-sign'></i> Warning!</strong>&#160;&#160;"
                    + warningMessage
                    + "</div>";
        this.feedback(htmlContent);
    };

    this.clear = function() {
        this.feedbackMessages = [];
        $('#' + this.feedbackDivId).html("");
    }

    this.feedback = function(htmlFeedback) {
        this.feedbackMessages.unshift(htmlFeedback);
        this.feedbackMessages = this.feedbackMessages.slice(0, 4);
        $('#' + this.feedbackDivId).html("<button type='button' class='close' data-dismiss='alert' onclick='javascript:md.clear();'>x</button>");
        for (var i=0,len=this.feedbackMessages.length; i<len; i++) {
            $('#' + this.feedbackDivId).append(this.feedbackMessages[i]);
        }
    };

    this.track = function(trackView, trackValue) {
        _gaq.push(['_trackEvent', 'web-gui', trackView, trackValue]);
    };
    this.track = function(trackView) {
        _gaq.push(['_trackEvent', 'web-gui', trackView]);
    };
}
