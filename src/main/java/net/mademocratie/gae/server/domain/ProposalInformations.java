package net.mademocratie.gae.server.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import net.mademocratie.gae.server.entities.dto.CommentDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.dto.ProposalVotesDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

/**
 * ProposalInformations
 */
@XmlRootElement
public class ProposalInformations {
    ProposalDTO proposal;
    ProposalVotesDTO proposalVotesDTO;
    Collection<CommentDTO> proposalComments;

    public ProposalInformations() {
    }

    public ProposalInformations(ProposalDTO proposalRetrieved, ProposalVotesDTO proposalVotesDTO, List<CommentDTO> proposalComments) {
        this.proposal = proposalRetrieved;
        this.proposalVotesDTO = proposalVotesDTO;
        this.proposalComments = proposalComments;
    }

    public ProposalDTO getProposal() {
        return proposal;
    }

    public void setProposal(ProposalDTO proposal) {
        this.proposal = proposal;
    }

    public ProposalVotesDTO getProposalVotesDTO() {
        return proposalVotesDTO;
    }

    public void setProposalVotesDTO(ProposalVotesDTO proposalVotesDTO) {
        this.proposalVotesDTO = proposalVotesDTO;
    }

    public int getProposalCommentsCount() {
        return (proposalComments != null ? proposalComments.size():0);
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
                .add("proposalVotesDTO", proposalVotesDTO)
                .add("proposalComments", proposalComments)
                .toString();
    }
}
