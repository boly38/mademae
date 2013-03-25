package net.mademocratie.gae.server.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * IContribution
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@XmlRootElement
public abstract class Contribution {
    Date date;
    String contributionDetails = "oO";

    public Contribution() {};

    public Date getDate() {
        return date;
    }

    public String getContributionDetails() {
        return contributionDetails;
    }
}
