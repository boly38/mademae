package net.mademocratie.gae.server.entities;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ProposalVotes
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVotes {

    private Collection<Vote> votes;

    private int votesProCountCache = -1;
    private int votesNeutralCountCache = -1;
    private int votesConCountCache = -1;

    public ProposalVotes(Collection<Vote> proposalVotes) {
        if (proposalVotes == null) {
            this.votes = new ArrayList<Vote>();
            return;
        }
        this.votes = proposalVotes;
    }

    public int voteCount() {
        return this.votes.size();
    }

    public int voteProCount() {
        if (votesProCountCache == -1) {
            calculateVotesCounts();
        }
        return this.votesProCountCache;
    }

    public int voteNeutralCount() {
        if (votesNeutralCountCache == -1) {
            calculateVotesCounts();
        }
        return this.votesNeutralCountCache;
    }

    public int voteConCount() {
        if (votesConCountCache == -1) {
            calculateVotesCounts();
        }
        return this.votesConCountCache;
    }

    private void calculateVotesCounts() {
        this.votesProCountCache = 0;
        this.votesNeutralCountCache = 0;
        this.votesConCountCache = 0;

        for(Vote vote: votes) {
            switch (vote.getKind()) {
                case NEUTRAL: this.votesNeutralCountCache++; break;
                case PRO: this.votesProCountCache++; break;
                default:
                case CON: this.votesConCountCache++; break;

            }
        }
    }

    public Collection<Vote> getVotes() {
        return votes;
    }
}
