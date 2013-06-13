package net.mademocratie.gae.server.services.impl;

import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.services.IManageContributions;

import java.util.*;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * ManageContributionsImpl
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ManageContributionsImpl implements IManageContributions {

    private final static Logger LOGGER = Logger.getLogger(ManageContributionsImpl.class.getName());

    public Map<Key<Contribution>, Contribution> getContributionsByIds(Set<Key<Contribution>> keys) {
        Map<Key<Contribution>, Contribution> contributions = ofy().load().keys(keys);
        return contributions;
    }
}
