package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.services.helper.DateHelper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class Vote extends Contribution implements IContribution {
    @Index
    protected Key<Proposal> proposal;

    protected VoteKind kind;

    public Vote() {}

    public Vote(String authorEmail, Long proposalId, VoteKind kind) {
        super(authorEmail);
        this.kind = kind;
        this.proposal = Key.create(Proposal.class, proposalId);
    }

    public Vote(Vote v) {
        super(v);
        this.setKind(v.getKind());
        this.setProposal(v.getProposal());
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @Override
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
                .append(" by ").append(getAuthorEmail())
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

    @Override
    public Email getAuthorEmail() {
        return super.getAuthorEmail();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getAuthorPseudo() {
        return super.getAuthorPseudo();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
