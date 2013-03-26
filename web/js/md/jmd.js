// MaDemocratie instance
var md = new MaDemocratie();
// MaDemocratie object
function MaDemocratie() {
    this.init= function() {
        $('.dropdown-toggle').dropdown();
        this.home();
    };

    this.home= function() {
        var parentMd = this;
        $.getJSON('json/contributions/last', function(contributionsJsonData) {
            $.get('/js-templates/contributions.html', function(htmlTemplate) {
                $.template("contributionsTemplate", htmlTemplate);
                var contributionsHtmlResult = $.tmpl("contributionsTemplate", contributionsJsonData);
                parentMd.updateContent(contributionsHtmlResult);
            });
        });
    };

    this.addSampleProposition= function() {
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

    this.contact= function() {
        $('#mainContent').html("<i>info - at - mademocratie (dot) net</i> should be able to answer ! ;)");
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
        $('#mainContent').html(htmlContent);
    };
}
