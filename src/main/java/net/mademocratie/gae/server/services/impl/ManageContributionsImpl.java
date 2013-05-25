package net.mademocratie.gae.server.services.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.services.IManageComment;
import net.mademocratie.gae.server.services.IManageContributions;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.IManageVote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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

    //~services
    @Inject
    private IManageVote manageVote;

    @Inject
    private IManageProposal manageProposal;

    @Inject
    private IManageComment manageComment;


    public List<Contribution> getLastContributions(int maxContributions) {
        List<Proposal> latestProposals = manageProposal.latest(maxContributions);
        List<Vote> latestVotes = manageVote.latest(maxContributions);
        List<CommentContribution> latestComments = manageComment.latest(maxContributions);
        List<VoteOnProposal> latestVotesOnProposal = manageVote.fetchProposalsVotes(latestVotes);
        List<CommentOnProposal> latestCommentsOnProposal = manageComment.fetchProposalsComments(latestComments);
        List<Contribution> latestContributions = new ArrayList<Contribution>();
        latestContributions.addAll(latestProposals);
        latestContributions.addAll(latestVotesOnProposal);
        latestContributions.addAll(latestCommentsOnProposal);
        if (latestContributions.size() == 0) {
            return latestContributions;
        }
        Collections.sort(latestContributions, new ContributionDateComparator());
        Collections.reverse(latestContributions);
        int subListLastIndex = Math.min(latestContributions.size(), maxContributions);
        LOGGER.info("returning " + subListLastIndex + " contributions");
        return latestContributions.subList(0, subListLastIndex);
    }
}
