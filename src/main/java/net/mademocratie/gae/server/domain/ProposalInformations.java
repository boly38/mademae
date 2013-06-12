package net.mademocratie.gae.server.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.entities.v1.ProposalVotes;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

/**
 * ProposalInformations
 */
@XmlRootElement
public class ProposalInformations {
    Proposal proposal;
    ProposalVotes proposalVotes;
    Collection<Comment> proposalComments;

    public ProposalInformations() {
    }

    public ProposalInformations(Proposal proposalRetrieved, ProposalVotes proposalVotes, List<Comment> proposalComments) {
        this.proposal = proposalRetrieved;
        this.proposalVotes = proposalVotes;
        this.proposalComments = proposalComments;
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

    public Collection<Comment> getProposalComments() {
        return proposalComments;
    }

    public void setProposalComments(Collection<Comment> proposalComments) {
        this.proposalComments = proposalComments;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("proposal", proposal)
                .add("proposalVotes", proposalVotes)
                .add("proposalComments", proposalComments)
                .toString();
    }
}
