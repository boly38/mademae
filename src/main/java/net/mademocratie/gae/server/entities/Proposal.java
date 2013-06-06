package net.mademocratie.gae.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.googlecode.objectify.annotation.Entity;
import net.mademocratie.gae.server.services.helper.DateHelper;
import org.joda.time.Duration;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
@Entity
public class Proposal extends Contribution implements IContribution {
    private String title;
    private Text content;

    public Proposal() {
        this.content = new Text("");
    }

    public Proposal(String title, String content) {
        super();
        this.title = title;
        this.content = new Text(content != null ? content : "");
    }

    @Override                   // json need id
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
    public Long getItemIt() {
        return super.getItemIt();
    }

    @Override
    public Email getAuthorEmail() {
        return super.getAuthorEmail();
    }

    @Override
    public String getAuthorPseudo() {
        return super.getAuthorPseudo();
    }

    @Override
    public String getContributionType() {
        return ContributionType.PROPOSAL.toString();
    }

    @Override
    public String getContributionDetails() {
        return "create proposition '" + getTitle() + "'";
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("dateFormat", getDateFormat())
                .add("age", getAge())
                .add("title", title)
                .add("content", content)
                .toString();
    }
}
