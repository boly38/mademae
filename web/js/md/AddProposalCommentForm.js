function AddProposalCommentForm() {
    this.init= function(divId, proposalId) {
        this.mainDivId = divId;
        this.proposalId = proposalId;
        this.showForm();
    };

    this.cancel= function() {
        this.updateContent("");
    };

    this.showForm= function() {
        var parentAP = this;
        var addCommentData = {};
        addCommentData.itemIt = this.proposalId;
        addCommentData.contributionType = 'PROPOSAL';
        $.get('/js-templates/addProposalComment.html', function(addProposalCommentTemplate) {
            $.template("addProposalCommentTemplate", addProposalCommentTemplate);
            var addProposalHtmlResult = $.tmpl("addProposalCommentTemplate", addCommentData);
            parentAP.updateContent(addProposalHtmlResult);
        });
    };

    this.updateContent= function(htmlContent) {
        $('#' + this.mainDivId).html(htmlContent);
    };
}