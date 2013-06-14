package net.mademocratie.gae.server.entities.dto;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import net.mademocratie.gae.server.entities.dto.VoteDTO;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ProposalVotesDTO
 */
public class ProposalVotesDTO {
    private Collection<VoteDTO> votes;

    private int votesCount = -1;
    private int votesProCount = -1;
    private int votesNeutralCount = -1;
    private int votesConCount = -1;

    public ProposalVotesDTO() {
        this.votes = new ArrayList<VoteDTO>();
    }

    public ProposalVotesDTO(Collection<VoteDTO> proposalVotes) {
        if (proposalVotes == null) {
            this.votes = new ArrayList<VoteDTO>();
            return;
        }
        this.votes = proposalVotes;
        calculateVotesCounts();
    }

    private void calculateVotesCounts() {
        this.votesProCount = 0;
        this.votesNeutralCount = 0;
        this.votesConCount = 0;
        this.votesCount = votes.size();

        for(VoteDTO vote: votes) {
            switch (vote.getKind()) {
                case NEUTRAL: this.votesNeutralCount++; break;
                case PRO: this.votesProCount++; break;
                default:
                case CON: this.votesConCount++; break;

            }
        }
    }

    public int getVotesCount() {
        return this.votes.size();
    }

    public int getVotesProCount() {
        return this.votesProCount;
    }

    public int getVotesNeutralCount() {
        return this.votesNeutralCount;
    }

    public int getVotesConCount() {
        return this.votesConCount;
    }

    public Collection<VoteDTO> getVotes() {
        return votes;
    }

    public void setVotes(Collection<VoteDTO> votes) {
        this.votes = votes;
        calculateVotesCounts();
    }

    public void setVotesConCount(int votesConCount) {
        this.votesConCount = votesConCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }

    public void setVotesNeutralCount(int votesNeutralCount) {
        this.votesNeutralCount = votesNeutralCount;
    }

    public void setVotesProCount(int votesProCount) {
        this.votesProCount = votesProCount;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("votesCount", votesCount)
                .add("votesProCount", votesProCount)
                .add("votesNeutralCount", votesNeutralCount)
                .add("votesConCount", votesConCount)
                .toString();
    }
}
