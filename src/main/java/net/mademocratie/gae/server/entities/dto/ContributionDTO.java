package net.mademocratie.gae.server.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Contribution;
import net.mademocratie.gae.server.services.helper.DateHelper;

import java.util.Date;

/**
 * ContributionDTO
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public abstract class ContributionDTO implements IContribution {
    protected Long contributionId;
    protected Date date;
    protected CitizenDTO author;

    protected ContributionDTO() {
    }

    public ContributionDTO(Citizen author, Long contributionId, Date date) {
        this.author = setAuthorFromCitizen(author);
        this.contributionId = contributionId;
        this.date = date;
    }

    public ContributionDTO(Citizen author, Contribution contribution) {
        this.author = setAuthorFromCitizen(author);
        this.contributionId = contribution.getContributionId();
        this.date = contribution.getDate();
    }

    private CitizenDTO setAuthorFromCitizen(Citizen author) {
        return (author != null ? new CitizenDTO(author) : null);
    }

    @JsonProperty
    public CitizenDTO getAuthor() {
        if (author == null) {
            return Citizen.createAnonymous();
        }
        return author;
    }

    public void setAuthor(CitizenDTO author) {
        this.author = author;
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

    public String getDateFormat() {
        return DateHelper.getDateFormat(getDate());
    }

    @JsonProperty("age")
    public String getAge() {
        return DateHelper.getDateDuration(getDate());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract String getContributionDetails();
    public abstract String getContributionType();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContributionDTO{");
        sb.append("author=").append(author);
        sb.append(", contributionId=").append(contributionId);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
