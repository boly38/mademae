package net.mademocratie.gae.server.entities.dto;

import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Proposal;

import java.util.Date;

public class ProposalDTO extends ContributionDTO {
    private String title;
    private String content;

    public ProposalDTO() {
    }

    @Override
    public String getContributionDetails() {
        return "create proposition '" + getTitle() + "'";
    }

    @Override
    public String getContributionType() {
        return ContributionType.PROPOSAL.toString();
    }

    public ProposalDTO(Citizen author, Proposal proposal) {
        super(author, proposal);
        this.content = proposal.getContent();
        this.title = proposal.getTitle();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
