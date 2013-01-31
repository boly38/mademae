package net.mademocratie.gae.server.entities;

import java.util.Date;

/**
 * IContribution
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public interface IContribution {
    public Date getDate();
    public String getContributionDetails();
}
