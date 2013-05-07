package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.ProposalVotes;
import net.mademocratie.gae.server.entities.Vote;
import net.mademocratie.gae.server.entities.VoteKind;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;

import java.util.List;

@ImplementedBy(ManageVoteImpl.class)
public interface IManageVote {

    List<Vote> getProposalVotesOfACitizen(String citizenEmail, Long proposalId);

    Vote vote(String citizenEmail, Long proposalId, VoteKind kind);

    ProposalVotes getProposalVotes(Long proposalId);

    List<Vote> latest(int maxVotes);

    void removeProposalVotes(Long proposalId);
}
