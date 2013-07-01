package net.mademocratie.gae.server.entities.v1;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.services.helper.DateHelper;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

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

    @Override
    @JsonProperty
    public Long getAuthor() {
        return super.getAuthor();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setAuthor(Long authorId) {
        super.setAuthor(authorId);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getDate() {
        return DateHelper.getDateSerializeFormat(getDateValue());
    }

    @Override                   // json need id
    @JsonIgnore
    public Date getDateValue() {
        return super.getDateValue();
    }

    @Override
    @JsonIgnore
    public String getDateFormat() {
        return DateHelper.getDateFormat(getDateValue());
    }

    @Override
    @JsonIgnore
    public String getAge() {
        return super.getAge();
    }

    @Override                    // json need id
    public Long getContributionId() {
        return super.getContributionId();
    }

    @Override
    @JsonIgnore
    public String getContributionType() {
        return ContributionType.PROPOSAL.toString();
    }

    @Override
    @JsonIgnore
    public String getContributionDetails() {
        return "create proposition '" + getTitle() + "'";
    }

    public String getContent() {
        return (content != null ? content.getValue():null);
    }
    public void setContentFromString(String content) {
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
        final StringBuilder sb = new StringBuilder("Proposal{");
        sb.append(super.toString());
        sb.append(", content=").append(content);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposal)) return false;

        Proposal proposal = (Proposal) o;

        if (content != null ? !content.equals(proposal.content) : proposal.content != null) return false;
        if (!title.equals(proposal.title)) return false;

        return super.equals(o) && true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
