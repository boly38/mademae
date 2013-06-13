package net.mademocratie.gae.server.entities.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        setAuthor(author);
    }

    public Contribution(Contribution c) {
        setContributionId(c.getContributionId());
        setDate(c.getDate());
        if (getDate() == null) {
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

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @JsonProperty("age")
    public String getAge() {
        return DateHelper.getDateDuration(getDate());
    }

    public abstract String getContributionDetails();

    public abstract String getContributionType();

    public void setAuthor(Key<Citizen> author) {
        this.author = author;
    }
    public void setAuthor(Citizen author) {
        setAuthor(Key.create(Citizen.class, author.getId()));
    }

    @JsonIgnore
    public Key<Citizen> getAuthor() {
        return author;
    }
    public Long getAuthorId() {
        return (author != null ? author.getId() : null);
    }
}
