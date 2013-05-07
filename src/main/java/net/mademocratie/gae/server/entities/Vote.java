package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class Vote extends Contribution implements IContribution {
    @Index
    private Email citizenEmail;

    @Index
    private Key<Proposal> proposal;

    private VoteKind kind;

    public Vote() {}

    public Vote(String citizenEmail, Long proposalId, VoteKind kind) {
        this.citizenEmail = (citizenEmail != null ? new Email(citizenEmail) : null);
        this.kind = kind;
        this.proposal = Key.create(Proposal.class, proposalId);
        this.date = new Date();
    }

    public String getCitizenEmail() {
        return (citizenEmail != null ? citizenEmail.getEmail() : null);
    }

    public void setCitizenEmail(String citizenEmail) {
        this.citizenEmail = (citizenEmail != null ? new Email(citizenEmail) : null);
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
        if (getItemIt() != null) {
            sb.append("#").append(getItemIt());
        }
        sb.append(":").append(getDate());
        sb.append("|").append(getKind())
                .append(" by ").append(getCitizenEmail())
                .append(" on proposal#").append(getProposal())
                .append("]");
        return sb.toString();
    }

    @Override                    // json need id
    public Long getItemIt() {
        return proposal.getId();
    }


    @Override
    public String getContributionType() {
        return ContributionType.VOTE.toString();
    }

    @Override
    public String getContributionDetails() {
        return "vote on proposal '" + getProposal() + "'";
    }
}
