package net.mademocratie.gae.server.entities;

import net.mademocratie.gae.server.services.helper.DateHelper;

import java.util.Date;

/**
 * VoteOnProposal
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
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
        return proposal.getId();
    }


    @Override
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    @Override
    public String getContributionDetails() {
        String title = getProposalContent() != null ? getProposalContent().getTitle() : getItemIt().toString();
        return "vote on proposal '" + title + "'";
    }
}
