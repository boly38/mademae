package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Email;
import net.mademocratie.gae.server.services.helper.DateHelper;

import java.util.Date;

public class CommentOnProposal  extends CommentContribution {
    private Proposal proposalContent;

    public CommentOnProposal(CommentContribution commentContribution, Proposal proposal) {
        super(commentContribution);
        setProposalContent(proposal);
    }

    public Proposal getProposalContent() {
        return proposalContent;
    }

    public void setProposalContent(Proposal proposalContent) {
        this.proposalContent = proposalContent;
    }


    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @Override
    public String getAge() {
        return super.getAge();
    }

    @Override                    // json need id
    public Long getItemIt() {
        return super.getParentContribution();
    }


    @Override
    public String getContributionType() {
        return ContributionType.COMMENT.toString();
    }

    @Override
    public String getContributionDetails() {
        String title = getProposalContent() != null ? getProposalContent().getTitle() : getItemIt().toString();
        return "comment on proposal '" + title + "'";
    }

    @Override
    public Email getAuthorEmail() {
        return super.getAuthorEmail();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getAuthorPseudo() {
        return super.getAuthorPseudo();    //To change body of overridden methods use File | Settings | File Templates.
    }
}