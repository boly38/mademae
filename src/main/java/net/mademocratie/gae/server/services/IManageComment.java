package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.CommentList;
import net.mademocratie.gae.server.entities.ProposalList;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.services.impl.ManageCommentImpl;

import java.util.List;
import java.util.Map;

@ImplementedBy(ManageCommentImpl.class)
public interface IManageComment {
    Comment comment(Citizen citizen, Comment inComment);

    void importComments(List<Comment> comments);

    List<Comment> getProposalComments(Long propId);

    CommentList getProposalCommentsAsList(Long propId);


    List<Comment> latest(int max);

    CommentList latestAsList(int max);

    List<Comment> latest();

    Map<Long, Proposal> fetchCommentsProposals(List<Comment> comms);

    int removeAll();
}
