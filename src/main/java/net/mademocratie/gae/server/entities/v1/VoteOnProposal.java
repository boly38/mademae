package net.mademocratie.gae.server.entities.v1;

import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.services.helper.DateHelper;

import java.util.Date;

public class VoteOnProposal extends Vote implements IContribution {
    private Proposal proposalContent;

    public VoteOnProposal(Vote v, Proposal proposal) {
        super(v);
        setProposalContent(proposal);
    }

    public Proposal getProposalContent() {
        return proposalContent;
    }

    public void setProposalContent(Proposal proposalContent) {
        this.proposalContent = proposalContent;
    }


    @Override
    public Date getDateValue() {
        return date;
    }

    @Override
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDateValue());
    }

    @Override
    public String getAge() {
        return super.getAge();
    }

    @Override                    // json need id
    public Long getContributionId() {
        return proposal.getId();
    }


    @Override
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    @Override
    public String getContributionDetails() {
        String title = getProposalContent() != null ? getProposalContent().getTitle() : getContributionId().toString();
        return "a vote on proposal '" + title + "'";
    }
}
