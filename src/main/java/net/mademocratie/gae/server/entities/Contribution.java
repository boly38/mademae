package net.mademocratie.gae.server.entities;

import java.util.Date;

public abstract class Contribution {
    public Contribution() {};

    public abstract Date getDate();

    public abstract String getContributionDetails();
}
