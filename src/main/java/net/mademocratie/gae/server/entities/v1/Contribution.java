package net.mademocratie.gae.server.entities.v1;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.services.helper.DateHelper;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Transient;
import java.util.Date;

@Entity
public abstract class Contribution implements IContribution {
    @Transient
    public static final String SOMENONE = "someone";

    @Id
    protected Long contributionId;

    @Index
    protected Date date;

    @Index
    protected Key<Citizen> author;

    public Contribution() {
        this.date = new Date();
    };

    protected Contribution(Citizen author) {
        this.date = new Date();
        setAuthorFromValue(author);
    }

    public Contribution(Contribution c) {
        setContributionId(c.getContributionId());
        setDate(c.getDateValue());
        if (getDateValue() == null) {
            setDate(new Date());
        }
        this.setAuthor(c.getAuthor());
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public String getDate() {
        return DateHelper.getDateSerializeFormat(getDateValue());
    }

    @JsonIgnore
    public Date getDateValue() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormat() {
        return DateHelper.getDateFormat(getDateValue());
    }

    public String getAge() {
        return DateHelper.getDateDuration(getDateValue());
    }

    public abstract String getContributionDetails();

    public abstract String getContributionType();

    public void setAuthor(Key<Citizen> author) {
        this.author = author;
    }
    public void setAuthorFromValue(Citizen author) {
        setAuthor(Key.create(Citizen.class, author.getId()));
    }

    @JsonIgnore
    public Key<Citizen> getAuthor() {
        return author;
    }
    public Long getAuthorId() {
        return (author != null ? author.getId() : null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contribution{");
        sb.append("author=").append(author);
        sb.append(", contributionId=").append(contributionId);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contribution)) return false;

        Contribution that = (Contribution) o;

        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (!contributionId.equals(that.contributionId)) return false;
        if (!date.equals(that.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contributionId.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
