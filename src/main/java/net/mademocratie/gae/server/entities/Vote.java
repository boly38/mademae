package net.mademocratie.gae.server.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Vote implements Serializable {
    public static final String VOTE_DATE = "date";
    @Id
    private Long id;

    private Date date;

    private String citizenEmail;

    private Long proposalId;

    private VoteKind kind;

    public Vote(String citizenEmail, Long proposalId, VoteKind kind) {
        this.citizenEmail = citizenEmail;
        this.kind = kind;
        this.proposalId = proposalId;
        this.date = new Date();
    }

    public String getCitizenEmail() {
        return citizenEmail;
    }

    public void setCitizenEmail(String citizenEmail) {
        this.citizenEmail = citizenEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
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
                .append(" on proposal#").append(getProposalId())
                .append("]");
        return sb.toString();
    }
}
