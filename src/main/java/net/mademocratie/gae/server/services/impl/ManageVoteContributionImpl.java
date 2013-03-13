package net.mademocratie.gae.server.services.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.Vote;
import net.mademocratie.gae.server.entities.VoteContribution;
import net.mademocratie.gae.server.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageVoteContributionImpl implements IManageVoteContributionImpl {
    // ~services
    @Inject
    private IManageProposal manageProposal;

    @Inject
    private IManageCitizen manageCitizen;

    @Inject
    private IManageVote manageVote;

    public List<VoteContribution> latest(int maxContributions) {
        List<Vote> lastVotes = manageVote.latest(maxContributions);
        // collect dependent items to load
        Map<Long, Proposal> relatedProposalsCache = new HashMap<Long, Proposal>();
        Map<String, Citizen> relatedCitizensCache = new HashMap<String, Citizen>();
        List<VoteContribution> voteContributions = new ArrayList<VoteContribution>(lastVotes.size());
        for (Vote vote : lastVotes) {
            String citizenEmail = vote.getCitizenEmail();
            Long proposalId = vote.getProposal();
            if (!relatedProposalsCache.containsKey(proposalId)) {
                relatedProposalsCache.put(proposalId, manageProposal.getById(proposalId));
            }
            if (!relatedCitizensCache.containsKey(citizenEmail)) {
                relatedCitizensCache.put(citizenEmail, manageCitizen.findCitizenByEmail(citizenEmail));
            }
            voteContributions.add(new VoteContribution(relatedCitizensCache.get(citizenEmail), relatedProposalsCache.get(proposalId), vote));
        }
        return voteContributions;
    }
}
