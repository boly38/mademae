package net.mademocratie.gae.server.services.impl;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.services.IManageVote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageVoteImpl implements IManageVote {

    private final static Logger LOGGER = Logger.getLogger(ManageVoteImpl.class.getName());

    public List<Vote> getProposalVotesOfACitizen(String citizenEmail, Long proposalId) {
        return findProposalVotesByUserEmail(citizenEmail, proposalId);
    }

    private List<Vote> findProposalVotesByUserEmail(String citizenEmail, Long proposalId) {
        Email citizenEmailVal = new Email(citizenEmail);
        List<Vote> votes = ofy().load().type(Vote.class)
                .filter("authorEmail", citizenEmailVal)
                .list();
        List<Vote> proposalVotes = new ArrayList<Vote>();
        for(Vote vote : votes) {
            if (proposalId.equals(vote.getProposal())) {
                proposalVotes.add(vote);
            }
        }
        LOGGER.info("findProposalVotesByUserEmail result " + (proposalVotes != null ? proposalVotes.size() : "(none)"));
        return proposalVotes;
    }

    public Vote vote(String citizenEmail, Long proposalId, VoteKind kind) {
        List<Vote> existingVotes = findProposalVotesByUserEmail(citizenEmail, proposalId);
        if (existingVotes.size() > 0) {
            removeVotes(existingVotes);
        }
        Vote vote = new Vote(citizenEmail, proposalId, kind);
        addVote(vote);
        LOGGER.info("* Vote ADDED : " + vote);
        return vote;
    }

    private void removeVotes(List<Vote> existingVotes) {
        if (existingVotes == null
         || existingVotes.size() == 0) return;
        Result<Void> entitiesToDelete = ofy().delete().entities(existingVotes);
        entitiesToDelete.now();
        LOGGER.info("vote removed :" + existingVotes.size());

    }

    private Vote addVote(Vote vote) {
        ofy().save().entity(vote).now();
        LOGGER.fine("addVote result " + vote.toString());
        return vote;
    }

    private void removeVote(Vote voteToDelete) {
        ofy().delete().entity(voteToDelete).now();
        LOGGER.info("vote removed :" + voteToDelete.toString());
    }

    public ProposalVotes getProposalVotes(Long proposalId) {
        List<Vote> votes= ofy().load().type(Vote.class)
                .filter("proposal", Key.create(Proposal.class, proposalId))
                .list();
        return new ProposalVotes(votes);
    }

    public void removeProposalVotes(Long proposalId) {
        // votesQueries.removeProposalVotes(proposalId);
    }

    public List<Vote> latest(int max) {
        List<Vote> latestVotes = ofy().load().type(Vote.class)
                .order("-date")
                .limit(max)
                .list();
        LOGGER.info("* latest votes asked " + max + " result " + latestVotes.size());
        return latestVotes;
    }

    public List<VoteOnProposal> fetchProposalsVotes(List<Vote> votes) {
        int votesCount = votes.size();
        List<Long> proposalIds = new ArrayList<Long>(votesCount);
        for (Vote v : votes) {
            proposalIds.add(v.getProposal());
        }
        Map<Long, Proposal> proposalMap = ofy().load().type(Proposal.class).ids(proposalIds);
        List<VoteOnProposal> votesOnProposals = new ArrayList<VoteOnProposal>(votesCount);
        for(Vote v: votes){
            Proposal voteProposalContent = proposalMap.get(v.getProposal());
            VoteOnProposal voteOnProposal = new VoteOnProposal(v, voteProposalContent);
            votesOnProposals.add(voteOnProposal);
        }
        return votesOnProposals;
    }
}
