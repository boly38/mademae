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

function addProposalForm() {
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
    this.init= function(mainMenuDivId, mainDivId) {
        var parentMD = this;
        $.ajaxSetup({ cache:false });
        this.mainMenuDivId = mainMenuDivId;
        this.mainDivId = mainDivId;
        this.menu(function(){
            parentMD.afterInit();
        });
    };

    this.afterInit = function() {
        $('.dropdown-toggle').dropdown();
        if ($.urlParameter('redirect') == "login") {
            this.login();
        } else {
            this.home();
        }
    };

    this.menu= function(callback) {
        var parentMD = this;
        $.get('/js-templates/mainMenu.html', function(mainMenuTemplate) {
            $.template("mainMenuTemplate", mainMenuTemplate);
            var mainMenuHtmlResult = $.tmpl("mainMenuTemplate", "");
            parentMD.updateMenu(mainMenuHtmlResult);
            callback();
        });
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
            console.info(addSampleJsonData);
            $.get('/js-templates/jsonServiceResponse.html', function(htmlTemplate) {
                $.template("jsonServiceResponse", htmlTemplate);
                var jsonServiceResponseHtmlResult = $.tmpl("jsonServiceResponse", addSampleJsonData);
                parentMd.updateContent(jsonServiceResponseHtmlResult);
            });
        });
        this.track("addSampleProposal");
    };

    this.addProposalAction= function() {
        this.addProposalForm = new addProposalForm();
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
             md.updateContent("<div class='container'><div class='row-fluid'><h4>Proposal addition</h4><p>you just add a proposal (title:" + title + ")</p></div></div>");
             setTimeout(function() {md.home();}, 5000);
           }
         });
        this.track("addProposal");
    };

    this.contact= function() {
        this.updateContent("<div class='container'><div class='row-fluid'><h4>Contact</h4><p>info - at - mademocratie (dot) net</i> should be able to answer ! ;)</p></div></div>");
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

    this.track = function(trackView, trackValue) {
        _gaq.push(['_trackEvent', 'web-gui', trackView, trackValue]);
    };
    this.track = function(trackView) {
        _gaq.push(['_trackEvent', 'web-gui', trackView]);
    };
}
