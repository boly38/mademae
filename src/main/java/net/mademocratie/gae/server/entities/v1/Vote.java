package net.mademocratie.gae.server.entities.v1;

import org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.services.helper.DateHelper;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@Entity
public class Vote extends Contribution implements IContribution {
    @Index
    @JsonProperty("proposal")
    protected Key<Proposal> proposal;

    protected VoteKind kind;

    public Vote() {}

    public Vote(Citizen author, Proposal proposal, VoteKind kind) {
        super(author);
        this.kind = kind;
        this.proposal = Key.create(Proposal.class, proposal.getContributionId());
    }

    public Vote(Vote v) {
        super(v);
        this.setKind(v.getKind());
        this.proposal = v.getProposalKey();
    }


    @Override
    public Long getAuthor() {
        return super.getAuthor();
    }

    @Override
    public void setAuthor(Long authorId) {
        super.setAuthor(authorId);
    }

    public String getDate() {
        return DateHelper.getDateSerializeFormat(getDateValue());
    }

    @Override                    // json need id
    public Long getContributionId() {
        return super.getContributionId();
    }

    @Override
    @JsonIgnore
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    @Override
    @JsonIgnore
    public String getContributionDetails() {
        return "vote on proposal '" + getProposalId() + "'";
    }

    @Override
    public Date getDateValue() {
        return super.getDateValue();
    }

    @Override
    @JsonIgnore
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDateValue());
    }

    @Override
    @JsonIgnore
    public String getAge() {
        return super.getAge();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public VoteKind getKind() {
        return kind;
    }

    public void setKind(VoteKind kind) {
        this.kind = kind;
    }

    @JsonIgnore
    public Key<Proposal> getProposalKey() {
        return proposal;
    }

    public void setProposalKey(Key<Proposal> proposal) {
        this.proposal = proposal;
    }

    public Long getProposal() {
        return (proposal != null ? proposal.getId() : null);
    }

    public void setProposal(Long proposalId) {
        setProposalKey(Key.create(Proposal.class, proposalId));
    }

    @JsonIgnore
    public Long getProposalId() {
        return getProposal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Vote");
        if (getContributionId() != null) {
            sb.append("#").append(getContributionId());
        }
        sb.append(":").append(getDateValue());
        sb.append("|").append(getKind())
                .append(" by citizen#").append(getAuthorKey())
                .append(" on proposal#").append(getProposalId())
                .append("]");
        return sb.toString();
    }



}
