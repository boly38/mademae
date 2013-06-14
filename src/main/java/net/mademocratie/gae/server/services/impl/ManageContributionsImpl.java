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

    public Map<Long, Contribution> getContributionsByIds(Set<Key<Contribution>> keys) {
        List<Long> contribsId = new ArrayList<Long>();
        for (Key<Contribution> key : keys) {
            contribsId.add(key.getId());
        }
        LOGGER.info("load contributions by ids " + keys.toString());
        Map<Long, Contribution> contributions = new HashMap<Long, Contribution>();
        Map<Long, Proposal> proposals = ofy().load().type(Proposal.class).ids(contribsId);
        Map<Long, Comment> comments= ofy().load().type(Comment.class).ids(contribsId);
        Map<Long, Vote> votes = ofy().load().type(Vote.class).ids(contribsId);
        contributions.putAll(proposals);
        contributions.putAll(comments);
        contributions.putAll(votes);
        LOGGER.info("load contributions by ids " + keys.size() + " result " + contributions.size());
        return contributions;
    }
}
