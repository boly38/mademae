package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.dto.ContributionDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.Contribution;
import net.mademocratie.gae.server.entities.v1.Proposal;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class GetContributionsResult {
    private String contributionsDescription;
    private ArrayList<ContributionDTO> contributions;
    private String proposalsDescription;
    private ArrayList<ProposalDTO> proposals;

    public GetContributionsResult() {
    }

    public GetContributionsResult(ArrayList<ContributionDTO> contributions, String contributionsDescription, ArrayList<ProposalDTO> proposals, String proposalsDescription) {
        this.contributions = contributions;
        this.contributionsDescription = contributionsDescription;
        this.proposals = proposals;
        this.proposalsDescription = proposalsDescription;
    }

    public GetContributionsResult(ArrayList<ProposalDTO> proposals, String proposalsTitle) {
        this.contributions = null;
        this.contributionsDescription = null;
        this.proposals = proposals;
        this.proposalsDescription = proposalsTitle;
    }

    public String getContributionsDescription() {
        return contributionsDescription;
    }

    public void setContributionsDescription(String contributionsDescription) {
        this.contributionsDescription = contributionsDescription;
    }

    public ArrayList<ContributionDTO> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<ContributionDTO> contributions) {
        this.contributions = contributions;
    }

    public ArrayList<ProposalDTO> getProposals() {
        return proposals;
    }

    public void setProposals(ArrayList<ProposalDTO> proposals) {
        this.proposals = proposals;
    }

    public String getProposalsDescription() {
        return proposalsDescription;
    }

    public void setProposalsDescription(String proposalsDescription) {
        this.proposalsDescription = proposalsDescription;
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }
}
