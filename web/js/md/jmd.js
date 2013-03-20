function MaDemocratie() {
    this.init= function() {
        $('.dropdown-toggle').dropdown();
    };

    this.contact= function() {
        $('#mainContent').html("<i>info - at - mademocratie (dot) net</i> should be able to answer ! ;)");
    }
    this.about= function() {
        $.getJSON('json/about', function(aboutJsonData) {
            $.get('/js-templates/about.html', function(htmlTemplate) {
                $.template("aboutHtmlTemplate", htmlTemplate);
                var aboutHtmlResult = $.tmpl("aboutHtmlTemplate", aboutJsonData);
                $('#mainContent').html(aboutHtmlResult);
            });
        });
    }
}
