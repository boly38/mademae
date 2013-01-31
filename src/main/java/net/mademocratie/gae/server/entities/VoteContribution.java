package net.mademocratie.gae.server.entities;

public class VoteContribution extends Vote implements IContribution{

    private Citizen citizen;
    private Proposal proposal;

    public VoteContribution(Citizen citizen, Proposal proposal, Vote vote) {
        super(citizen != null ? citizen.getEmail() : null, proposal.getId(), vote.getKind());
        setId(vote.getId());
        setDate(vote.getDate());
        this.citizen = citizen;
        this.proposal = proposal;
    }

    public String getContributionDetails() {
        return "vote on proposal '" + proposal.getTitle() + "'";
    }

    public String toString() {
        return "contribution [vote " + super.toString() + " on " + (proposal != null ? proposal.toString() : "null proposal") + "]";
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public Proposal getProposal() {
        return proposal;
    }
}
