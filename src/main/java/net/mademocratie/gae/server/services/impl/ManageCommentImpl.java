package net.mademocratie.gae.server.services.impl;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.services.IManageComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageCommentImpl implements IManageComment {
    private final static Logger LOGGER = Logger.getLogger(ManageCommentImpl.class.getName());


    public CommentContribution comment(Citizen citizen, CommentContribution inComment) {
        CommentContribution commentContribution = new CommentContribution(citizen, inComment);
        addComment(commentContribution);
        LOGGER.info("* Comment ADDED : " + commentContribution);
        return commentContribution;
    }

    public List<CommentContribution> getProposalComments(Long proposalId) {
        List<CommentContribution> comments= ofy().load().type(CommentContribution.class)
                .filter("parentContribution", Key.create(Contribution.class, proposalId))
                .order("-date")
                .list();

        LOGGER.info("getProposalComments (partial) result " + (comments != null ? comments.size() : "(none)"));
        List<CommentContribution> proposalComments = new ArrayList<CommentContribution>();
        for(CommentContribution comm : comments) {
            if (IContribution.ContributionType.PROPOSAL.toString().equals(comm.getParentContributionType())) {
                proposalComments.add(comm);
            }
        }
        LOGGER.info("getProposalComments result " + (proposalComments != null ? proposalComments.size() : "(none)"));
        return proposalComments;

    }

    private CommentContribution addComment(CommentContribution commentContribution) {
        ofy().save().entity(commentContribution).now();
        LOGGER.fine("addComment result " + commentContribution.toString());
        return commentContribution;
    }

    public List<CommentContribution> latest() {
        return latest(0);
    }
    public List<CommentContribution> latest(int max) {
        Query<CommentContribution> orderedComments = ofy().load().type(CommentContribution.class).order("-date");
        if (max > 0) {
            orderedComments = orderedComments.limit(max);
        }
        List<CommentContribution> latestComments = orderedComments.list();
        int resultCount = latestComments != null ? latestComments.size() : 0;
        LOGGER.info("* latest comments asked " + (max > 0 ? max : "unlimited") + " result " +resultCount);
        return latestComments;
    }

    public List<CommentOnProposal> fetchProposalsComments(List<CommentContribution> comms) {
        int commsCount = comms.size();
        List<Long> proposalIds = new ArrayList<Long>(commsCount);
        for (CommentContribution c : comms) {
            proposalIds.add(c.getParentContribution());
        }
        Map<Long, Proposal> proposalMap = ofy().load().type(Proposal.class).ids(proposalIds);
        List<CommentOnProposal> commsOnProposals = new ArrayList<CommentOnProposal>(commsCount);
        for(CommentContribution c : comms){
            Proposal commProposalContent = proposalMap.get(c.getParentContribution());
            CommentOnProposal commOnProposal = new CommentOnProposal(c, commProposalContent);
            commsOnProposals.add(commOnProposal);
        }
        return commsOnProposals;
    }

}
