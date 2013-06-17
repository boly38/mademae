package net.mademocratie.gae.server.entities.v1;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.services.helper.DateHelper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class Comment extends Contribution implements IContribution {
    @Index
    protected Key<Contribution> parentContribution;

    private String parentContributionType = ContributionType.PROPOSAL.toString();

    private Text content;

    public Comment() {
    }


    public Comment(Comment inComment) {
        super(inComment);
        this.setContent(inComment.getContent());
        parentContribution = Key.create(Contribution.class, inComment.getParentContributionId());
        parentContributionType = inComment.getParentContributionType();
    }


    public Comment(Citizen citizen, Comment inComment) {
        super(inComment);
        this.setAuthor(Key.create(Citizen.class, citizen.getId()));
        this.setContent(inComment.getContent());
        parentContribution = Key.create(Contribution.class, inComment.getParentContributionId());
        parentContributionType = inComment.getParentContributionType();
    }

    public Comment(Citizen author, String content, Contribution parentContribution) {
        super(author);
        this.parentContribution = Key.create(Contribution.class, parentContribution.getContributionId());
        this.parentContributionType = parentContribution.getContributionType();
        this.content = new Text(content);
    }

    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @Override
    public String getAge() {
        return super.getAge();
    }

    @Override                    // json need id
    public Long getContributionId() {
        return parentContribution.getId();
    }


    @Override
    public String getContributionType() {
        return ContributionType.COMMENT.toString();
    }

    @Override
    public String getContributionDetails() {
        return "comment on '" + parentContributionType + "'";
    }

    public String getContent() {
        return (content != null ? content.getValue():null);
    }
    public void setContent(String content) {
        this.content = new Text(content != null ? content : "");
    }

    public Key<Contribution> getParentContribution() {
        return parentContribution;
    }

    public Long getParentContributionId() {
        return (parentContribution != null ? parentContribution.getId() : null);
    }

    public void setParentContributionId(Long parentContributionId) {
        this.parentContribution = Key.create(Contribution.class, parentContributionId);
    }

    public String getParentContributionType() {
        return parentContributionType;
    }

    public void setParentContributionType(String parentContributionType) {
        this.parentContributionType = parentContributionType;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("dateFormat", getDateFormat())
                .add("age", getAge())
                .add("content", content)
                .add("parentContribution", parentContribution)
                .add("parentContributionType", parentContributionType)
                .toString();
    }
}
