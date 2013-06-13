package net.mademocratie.gae.server.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import net.mademocratie.gae.server.entities.dto.CommentDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.ProposalVotes;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

/**
 * ProposalInformations
 */
@XmlRootElement
public class ProposalInformations {
    ProposalDTO proposal;
    ProposalVotes proposalVotes;
    Collection<CommentDTO> proposalComments;

    public ProposalInformations() {
    }

    public ProposalInformations(ProposalDTO proposalRetrieved, ProposalVotes proposalVotes, List<CommentDTO> proposalComments) {
        this.proposal = proposalRetrieved;
        this.proposalVotes = proposalVotes;
        this.proposalComments = proposalComments;
    }

    public ProposalDTO getProposal() {
        return proposal;
    }

    public void setProposal(ProposalDTO proposal) {
        this.proposal = proposal;
    }

    public ProposalVotes getProposalVotes() {
        return proposalVotes;
    }

    public void setProposalVotes(ProposalVotes proposalVotes) {
        this.proposalVotes = proposalVotes;
    }

    public Collection<CommentDTO> getProposalComments() {
        return proposalComments;
    }

    public void setProposalComments(Collection<CommentDTO> proposalComments) {
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
