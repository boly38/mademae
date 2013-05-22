package net.mademocratie.gae.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.services.helper.DateHelper;
import org.joda.time.Duration;

import javax.persistence.Transient;
import java.util.Date;

@Entity
public abstract class Contribution implements IContribution {
    @Transient
    public static final String SOMENONE = "someone";

    @Id
    protected Long itemIt;

    @Index
    protected Date date;

    @Transient
    String age = null;

    @Index
    protected Email authorEmail;

    @Index
    private String authorPseudo = SOMENONE;


    protected Contribution(String authorEmail) {
        this.date = new Date();
        setAuthorEmailString(authorEmail);
    }

    public Contribution() {};

    public Contribution(Contribution c) {
        setItemIt(c.getItemIt());
        setDate(c.getDate());
        this.setAuthorEmail(c.getAuthorEmail());
        this.setAuthorPseudo(c.getAuthorPseudo());
    }

    public Long getItemIt() {
        return itemIt;
    }

    public void setItemIt(Long itemIt) {
        this.itemIt = itemIt;
    }

    public Date getDate() {
        return date;
    }

    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @JsonProperty("age")
    public String getAge() {
        // return Minutes.minutesBetween(new DateTime(getDate()), new DateTime()).toString();
        Duration duration = new Duration(getDate().getTime(), new Date().getTime());
        if (duration.getStandardDays() > 1) {
            return duration.getStandardDays() + " days ago";
        }
        if (duration.getStandardDays() == 1) {
            return "1 day ago";
        }
        if (duration.getStandardHours() >= 1) {
            return duration.getStandardHours()+ " hours ago";
        }
        if (duration.getStandardMinutes() >= 1) {
            return duration.getStandardMinutes()+ " minutes ago";
        }
        return "a moment ago";
    }

    public Email getAuthorEmail() {
        return authorEmail;
    }

    public String getAuthorEmailString() {
        return (authorEmail != null ? authorEmail.getEmail() : null);
    }

    public void setAuthorEmailString(String authorEmail) {
        this.authorEmail = (authorEmail != null ? new Email(authorEmail) : null);
    }

    public void setAuthorEmail(Email authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorPseudo() {
        if (authorPseudo != null) {
            return authorPseudo;
        }
        return SOMENONE;
    }

    public void setAuthorPseudo(String authorPseudo) {
        this.authorPseudo = authorPseudo;
    }

    public abstract String getContributionDetails();

    public abstract String getContributionType();

    public void setDate(Date date) {
        this.date = date;
    }
}
