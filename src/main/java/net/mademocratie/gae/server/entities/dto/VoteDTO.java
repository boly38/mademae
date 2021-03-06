package net.mademocratie.gae.server.entities.dto;

import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.services.helper.DateHelper;

import java.util.Date;

/**
 * VoteDTO
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class VoteDTO extends ContributionDTO {
    protected ProposalDTO proposalContent;
    protected VoteKind kind;

    public VoteDTO() {
    }

    public VoteDTO(Citizen author, Vote vote, ProposalDTO proposal) {
        super(author, vote);
        this.proposalContent = proposal;
        this.kind = vote.getKind();
    }

    public VoteDTO(Citizen author, Vote vote) {
        super(author, vote);
        this.kind = vote.getKind();
    }

    @Override                   // json need id
    public Date getDateValue() {
        return super.getDateValue();
    }

    @Override
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDateValue());
    }

    @Override
    public String getAge() {
        return super.getAge();
    }

    public CitizenDTO getAuthor() {
        return Citizen.createSomeone(); // prevent vote authoring display
    }

    @Override
    public Long getContributionId() {
        return (proposalContent != null ? proposalContent.getContributionId() : null);
    }

    @Override
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    @Override
    public String getContributionDetails() {
        String onWhat = proposalContent != null ? proposalContent.getTitle() : "proposal";
        return "vote on '" + onWhat + "'";
    }

    public ProposalDTO getProposalContent() {
        return proposalContent;
    }

    public void setProposalContent(ProposalDTO proposalContent) {
        this.proposalContent = proposalContent;
    }

    public VoteKind getKind() {
        return kind;
    }

    public void setKind(VoteKind kind) {
        this.kind = kind;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VoteDTO{");
        sb.append(super.toString());
        sb.append(", kind=").append(kind);
        sb.append(", proposalContent=").append(proposalContent);
        sb.append('}');
        return sb.toString();
    }
}