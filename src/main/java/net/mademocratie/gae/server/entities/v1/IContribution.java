package net.mademocratie.gae.server.entities.v1;

import java.io.Serializable;
import java.util.Date;

/**
 * IContribution
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public interface IContribution extends Serializable {
    public abstract Long getContributionId();

    public abstract Date getDate();

    public abstract String getContributionDetails();

    public abstract String getContributionType();

    enum ContributionType {
        VOTE, PROPOSAL, COMMENT
    }
}
