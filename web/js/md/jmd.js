function MaDemocratie() {
    this.init= function() {
        $('.dropdown-toggle').dropdown();
    };

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
