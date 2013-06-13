package net.mademocratie.gae.server.entities;

import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Contribution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentList extends ContributionList<Comment> {
    public CommentList(List<Comment> contributions) {
        super(contributions);
    }

    public Set<Key<Contribution>> fetchParentContributionsIds() {
        Set<Key<Contribution>> parentContributionsIds = new HashSet<Key<Contribution>>();
        if (contributions == null) {
            return parentContributionsIds;
        }
        for(Comment contribution: contributions) {
            if (contribution.getParentContributionId() != null) {
                parentContributionsIds.add(contribution.getParentContribution());
            }
        }
        return parentContributionsIds;
    }
}