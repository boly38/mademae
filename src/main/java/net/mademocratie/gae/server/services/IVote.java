package net.mademocratie.gae.server.services;

import net.mademocratie.gae.server.entities.Vote;

import java.util.Collection;
import java.util.List;

public interface IVote {
    /**
     * Return the latest votes, ordered by descending date.
     *
     * @param max the maximum number of votes to return
     * @return the votes
     */
    List<Vote> latest(int max);

    Vote findProposalVoteByUserEmail(String citizenEmail, Long proposalId);

    void removeVoteByUserEmail(String citizenEmail, Long proposalId);

    Collection<Vote> findProposalVotes(Long proposalId);

    void removeProposalVotes(Long proposalId);

    void removeAll();
}
