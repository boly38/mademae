package net.mademocratie.gae.server.services.impl;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.services.IManageComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageCommentImpl implements IManageComment {
    private final static Logger LOGGER = Logger.getLogger(ManageCommentImpl.class.getName());


    public Comment comment(Citizen citizen, Comment inComment) {
        Comment comment = inComment;
        if (citizen != null) {
            comment = new Comment(citizen, inComment);
        }
        addComment(comment);
        LOGGER.info("* Comment ADDED : " + comment);
        return comment;
    }

    public List<Comment> getProposalComments(Long proposalId) {
        List<Comment> comments= ofy().load().type(Comment.class)
                .filter("parentContribution", Key.create(Contribution.class, proposalId))
                .order("-date")
                .list();

        LOGGER.info("getProposalComments (partial) result " + (comments != null ? comments.size() : "(none)"));
        List<Comment> proposalComments = new ArrayList<Comment>();
        for(Comment comm : comments) {
            if (IContribution.ContributionType.PROPOSAL.toString().equals(comm.getParentContributionType())) {
                proposalComments.add(comm);
            }
        }
        LOGGER.info("getProposalComments result " + (proposalComments != null ? proposalComments.size() : "(none)"));
        return proposalComments;

    }

    private Comment addComment(Comment comment) {
        ofy().save().entity(comment).now();
        LOGGER.fine("addComment result " + comment.toString());
        return comment;
    }

    public List<Comment> latest() {
        return latest(0);
    }
    public List<Comment> latest(int max) {
        Query<Comment> orderedComments = ofy().load().type(Comment.class).order("-date");
        if (max > 0) {
            orderedComments = orderedComments.limit(max);
        }
        List<Comment> latestComments = orderedComments.list();
        int resultCount = latestComments != null ? latestComments.size() : 0;
        LOGGER.info("* latest comments asked " + (max > 0 ? max : "unlimited") + " result " +resultCount);
        return latestComments;
    }

    public Map<Long, Proposal> fetchCommentsProposals(List<Comment> comms) {
        int commsCount = comms.size();
        List<Long> proposalIds = new ArrayList<Long>(commsCount);
        for (Comment c : comms) {
            proposalIds.add(c.getParentContribution());
        }
        Map<Long, Proposal> proposalMap = ofy().load().type(Proposal.class).ids(proposalIds);
        return proposalMap;
    }

}
