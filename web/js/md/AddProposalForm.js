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
            $("#content").htmlarea({
                toolbar: ["html","bold", "italic", "underline", "|", "h1", "h2", "|", "link", "unlink"
                ]
            });
        });
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}