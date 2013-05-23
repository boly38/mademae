package net.mademocratie.gae.server.services;

import com.google.appengine.api.datastore.Text;
import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.CommentContribution;
import net.mademocratie.gae.server.services.impl.ManageCommentImpl;

import java.util.List;

@ImplementedBy(ManageCommentImpl.class)
public interface IManageComment {
    CommentContribution comment(Citizen citizen, CommentContribution inComment);

    List<CommentContribution> getProposalComments(Long propId);
}
