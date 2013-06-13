package net.mademocratie.gae.server.entities.dto;

import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Contribution;

/**
 * CommentDTO
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class CommentDTO extends ContributionDTO {
    protected Contribution parentContribution;
    private String parentContributionType = ContributionType.PROPOSAL.toString();
    private String content;

    public CommentDTO() {
    }

    public CommentDTO(Citizen author, Comment comment, Contribution parentContribution) {
        super(author, comment);
        this.parentContribution = parentContribution;
        this.parentContributionType = comment.getParentContributionType();
        this.content = comment.getContent();
    }

    @Override
    public String getContributionType() {
        return ContributionType.COMMENT.toString();
    }

    @Override
    public String getContributionDetails() {
        return "comment on '" + parentContributionType + "'";
    }
}
