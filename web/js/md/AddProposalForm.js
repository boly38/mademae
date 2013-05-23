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