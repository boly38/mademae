package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Vote extends Contribution implements IContribution {
    public static final String VOTE_DATE = "date";
    @Id
    private Long id;

    private Date date;

    @Index
    private Email citizenEmail;

    @Index
    private Key<Proposal> proposal;

    private VoteKind kind;

    public Vote() {
    }

    public Vote(String citizenEmail, Long proposalId, VoteKind kind) {
        this.citizenEmail = (citizenEmail != null ? new Email(citizenEmail) : null);
        this.kind = kind;
        this.proposal = Key.create(Proposal.class, proposalId);
        this.date = new Date();
    }

    @Override
    public String getContributionId() {
        return String.valueOf(getId());
    }

    @Override
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    public String getCitizenEmail() {
        return (citizenEmail != null ? citizenEmail.getEmail() : null);
    }

    public void setCitizenEmail(String citizenEmail) {
        this.citizenEmail = (citizenEmail != null ? new Email(citizenEmail) : null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getDate() {
        return date;
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

    public Long getProposal() {
        return proposal.getId();
    }

    public void setProposal(Long proposal) {
        this.proposal =  Key.create(Proposal.class, proposal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Vote");
        if (getId() != null) {
            sb.append("#").append(getId());
        }
        sb.append(":").append(getDate());
        sb.append("|").append(getKind())
                .append(" by ").append(getCitizenEmail())
                .append(" on proposal#").append(getProposal())
                .append("]");
        return sb.toString();
    }

    @Override
    public String getContributionDetails() {
        return "vote on proposal '" + getProposal() + "'";
    }
}
