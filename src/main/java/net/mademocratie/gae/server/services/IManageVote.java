package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.VoteList;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;

import java.util.List;

@ImplementedBy(ManageVoteImpl.class)
public interface IManageVote {

    List<Vote> getProposalVotesOfACitizen(Citizen citizen, Long proposalId);

    Vote vote(Citizen author, Long proposalId, VoteKind kind) throws MaDemocratieException;
    Vote vote(Citizen author, Proposal proposal, VoteKind kind) throws MaDemocratieException;

    VoteList getProposalVotes(Long proposalId);

    List<Vote> latest(int maxVotes);

    VoteList latestAsList(int max);

    List<Vote> latest();

    void removeProposalVotes(Long proposalId);

    List<VoteOnProposal> fetchProposalsVotes(List<Vote> latestVotes);

    void removeAll();
}
