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
        $.getJSON('json/contributions', function(contributionsJsonData) {
            $.get('/js-templates/contributions.html', function(htmlTemplate) {
                $.template("contributionsTemplate", htmlTemplate);
                var contributionsHtmlResult = $.tmpl("contributionsTemplate", contributionsJsonData);
                parentMd.updateContent(contributionsHtmlResult);
            });
        });
    };

    this.contact= function() {
        $('#mainContent').html("<i>info - at - mademocratie (dot) net</i> should be able to answer ! ;)");
    };

    this.about= function() {
        var parentMd = this;
        $.getJSON('json/about', function(aboutJsonData) {
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
