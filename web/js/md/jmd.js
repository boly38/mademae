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

// MaDemocratie object
function MaDemocratie() {
    this.init= function(mainDivId) {
        this.mainDivId = mainDivId;
        $('.dropdown-toggle').dropdown();
        this.home();
    };

    this.home= function() {
        var parentMd = this;
        $.getJSON('json/contributions/last', function(contributionsJsonData) {
            $.get('/js-templates/contributions.html', function(contributionsTemplate) {
                $.template("contributionsTemplate", contributionsTemplate);
                var contributionsHtmlResult = $.tmpl("contributionsTemplate", contributionsJsonData);
                parentMd.updateContent(contributionsHtmlResult);
            });
        });
    };

    this.addSampleProposal= function() {
        var parentMd = this;
        $.getJSON('json/contributions/addSample', function(addSampleJsonData) {
            console.info(addSampleJsonData);
            $.get('/js-templates/jsonServiceResponse.html', function(htmlTemplate) {
                $.template("jsonServiceResponse", htmlTemplate);
                var jsonServiceResponseHtmlResult = $.tmpl("jsonServiceResponse", addSampleJsonData);
                parentMd.updateContent(jsonServiceResponseHtmlResult);
            });
        });
    };

    this.addProposalAction= function() {
        var parentMd = this;
        this.addProposalForm = new addProposalForm();
        this.addProposalForm.init(this.mainDivId);
    };

    this.addProposal= function(title, content) {
         this.updateContent("<p>[implementation in progress] you wanna add a proposal title:<br/>" + title + "</p>");
         setTimeout(function() {md.home();}, 10000);
    };

    this.contact= function() {
        this.updateContent("<i>info - at - mademocratie (dot) net</i> should be able to answer ! ;)");
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
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}
