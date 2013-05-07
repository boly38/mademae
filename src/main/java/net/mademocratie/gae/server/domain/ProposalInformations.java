package net.mademocratie.gae.server.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.ProposalVotes;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ProposalInformations
 */
@XmlRootElement
public class ProposalInformations {
    Proposal proposal;
    ProposalVotes proposalVotes;

    public ProposalInformations() {
    }

    public ProposalInformations(Proposal proposal, ProposalVotes proposalVotes) {
        this.proposal = proposal;
        this.proposalVotes = proposalVotes;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public ProposalVotes getProposalVotes() {
        return proposalVotes;
    }

    public void setProposalVotes(ProposalVotes proposalVotes) {
        this.proposalVotes = proposalVotes;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("proposal", proposal)
                .add("proposalVotes", proposalVotes)
                .toString();
    }
}
