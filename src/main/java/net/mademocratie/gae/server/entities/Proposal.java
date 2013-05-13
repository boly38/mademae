package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
@Entity
public class Proposal extends Contribution implements IContribution {
    @Index
    private String authorEmail;
    private String authorPseudo = SOMENONE;
    private String title;
    private Text content;

    public Proposal() {
        this.content = new Text("");
    }

    public Proposal(String title, String content) {
        this.title = title;
        this.content = new Text(content != null ? content : "");
    }

    @Override                   // json need id
    public Date getDate() {
        return super.getDate();
    }

    @Override                    // json need id
    public Long getItemIt() {
        return super.getItemIt();
    }

    @Override
    public String getContributionType() {
        return ContributionType.PROPOSAL.toString();
    }

    @Override
    public String getContributionDetails() {
        return "create proposition " + getTitle();
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorPseudo() {
        if (authorPseudo != null) {
            return authorPseudo;
        }
        return super.getAuthorPseudo();
    }

    public void setAuthorPseudo(String authorPseudo) {
        this.authorPseudo = authorPseudo;
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

    public String toString() {
            return toLogString();
    }
    /*
    return new JSONObject()
            .put("contributionId", getContributionId())
            .put("contributionType", getContributionType())
            .put("contributionDetails", getContributionDetails())
            .put("proposalId", getProposalId())
            .put("title", getTitle())
            .put("date", getDate())
            .put("authorPseudo", getAuthorPseudo())
            .put("content", (content!= null ?content.getValue() : ""))
            .toString();
            */

    public String toLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proposal[");
        sb.append("id:").append(getItemIt());
        sb.append(", title:").append(getTitle());
        if (getAuthorPseudo() != null)
            sb.append(", authorPseudo:").append(getAuthorPseudo());
        if (getAuthorEmail() != null)
            sb.append(", authorEmail:").append(getAuthorEmail());
        sb.append(", content:").append(getContent());
        sb.append("]");
        return sb.toString();
    }
}
