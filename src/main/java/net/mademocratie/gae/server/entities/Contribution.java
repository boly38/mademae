package net.mademocratie.gae.server.entities;

import java.util.Date;

public abstract class Contribution implements IContribution {
    public Contribution() {};

    public abstract String getContributionId();

    public abstract Date getDate();

    public abstract String getContributionDetails();

    public String getAuthorPseudo() {
        return "someone";
    }

    public abstract String getContributionType();

}
