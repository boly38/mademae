package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.Contribution;
import net.mademocratie.gae.server.entities.Proposal;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class GetContributionsResult {
    private String contributionsDescription;
    private ArrayList<Contribution> contributions;
    private String proposalsDescription;
    private ArrayList<Proposal> proposals;

    public GetContributionsResult() {
    }

    public GetContributionsResult(ArrayList<Contribution> contributions, String contributionsDescription, ArrayList<Proposal> proposals, String proposalsDescription) {
        this.contributions = contributions;
        this.contributionsDescription = contributionsDescription;
        this.proposals = proposals;
        this.proposalsDescription = proposalsDescription;
    }

    public GetContributionsResult(ArrayList<Proposal> proposals, String proposalsTitle) {
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

    public ArrayList<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<Contribution> contributions) {
        this.contributions = contributions;
    }

    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(ArrayList<Proposal> proposals) {
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
