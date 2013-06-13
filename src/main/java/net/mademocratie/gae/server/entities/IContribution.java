package net.mademocratie.gae.server.entities;

import java.io.Serializable;
import java.util.Date;

public interface IContribution extends Serializable {
    public abstract Long getContributionId();

    public abstract Date getDate();
    public abstract String getDateFormat();

    public abstract String getContributionDetails();

    public abstract String getContributionType();

    enum ContributionType {
        VOTE, PROPOSAL, COMMENT
    }
}
