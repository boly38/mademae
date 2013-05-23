package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.services.helper.DateHelper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class CommentContribution extends Contribution implements IContribution {
    @Index
    protected Key<Contribution> parentContribution;

    private String parentContributionType = ContributionType.PROPOSAL.toString();

    private Text content;

    public CommentContribution() {
    }

    public CommentContribution(Citizen citizen, CommentContribution inComment) {
        setDate(new Date());
        if (citizen != null && citizen.getEmail() != null) {
            this.setAuthorEmailString(citizen.getEmail());
        }
        if (citizen != null) {
            this.setAuthorPseudo(citizen.getPseudo());
        }
        this.setContent(inComment.getContent());
        parentContribution = Key.create(Contribution.class, inComment.getParentContribution());
        parentContributionType = inComment.getParentContributionType();
    }

    @Override
    public Date getDate() {
        return date;
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
    public Long getItemIt() {
        return super.getItemIt();
    }


    @Override
    public String getContributionType() {
        return ContributionType.COMMENT.toString();
    }

    @Override
    public String getContributionDetails() {
        return "comment on '" + parentContributionType + "'";
    }

    @Override
    public Email getAuthorEmail() {
        return super.getAuthorEmail();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getAuthorPseudo() {
        return super.getAuthorPseudo();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getContent() {
        return (content != null ? content.getValue():null);
    }
    public void setContent(String content) {
        this.content = new Text(content != null ? content : "");
    }

    public void setContent(Text content) {
        this.content = content;
    }

    public Long getParentContribution() {
        return parentContribution.getId();
    }

    public void setParentContribution(Long parentContributionId) {
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
