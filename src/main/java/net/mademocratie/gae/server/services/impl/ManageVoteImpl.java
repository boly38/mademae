package net.mademocratie.gae.server.services.impl;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.ProposalVotes;
import net.mademocratie.gae.server.entities.Vote;
import net.mademocratie.gae.server.entities.VoteKind;
import net.mademocratie.gae.server.services.IManageVote;

import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * ManageVoteImpl
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ManageVoteImpl implements IManageVote {
    private final static Logger LOGGER = Logger.getLogger(ManageVoteImpl.class.getName());

    public Vote getProposalVoteOfACitizen(String citizenEmail, Long proposalId) {
        return findProposalVoteByUserEmail(citizenEmail, proposalId);
    }

    private Vote findProposalVoteByUserEmail(String citizenEmail, Long proposalId) {
        Email citizenEmailVal = new Email(citizenEmail);
        List<Vote> votes= ofy().load().type(Vote.class)
                .filter("citizenEmail", citizenEmailVal)
                .list();

        Vote vote = null;
        for(Vote v:votes) {
            if (proposalId.equals(v.getProposal())) {
                vote = v;
                break;
            }
        }
        LOGGER.info("findProposalVoteByUserEmail result " + (vote != null ? vote.toString() : "(none)"));
        return vote;
    }

    public Vote vote(String citizenEmail, Long proposalId, VoteKind kind) {
        Vote existingVote = findProposalVoteByUserEmail(citizenEmail, proposalId);
        if (existingVote != null) {
            removeVoteById(existingVote.getId());
        }
        Vote vote = new Vote(citizenEmail, proposalId, kind);
        addVote(vote);
        LOGGER.info("* Vote ADDED : " + vote);
        return vote;
    }

    private Vote addVote(Vote vote) {
        Vote existingVote = findVoteById(vote.getId());
        if (existingVote != null) return existingVote;
        ofy().save().entity(vote).now();
        LOGGER.fine("addVote result " + vote.toString());
        return vote;
    }

    private Vote findVoteById(Long id) {
        if (id == null) { return null;}
        return ofy().load().type(Vote.class).id(id).get();
    }

    private void removeVoteById(Long voteId) {
        ofy().delete().type(Vote.class).id(voteId);
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
        // return votesQueries.latest(max);
        return null;
    }
}
