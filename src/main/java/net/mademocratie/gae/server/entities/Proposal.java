package net.mademocratie.gae.server.entities;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
@Entity
public class Proposal extends Contribution {
    @Id
    Long proposalId;
    private String authorEmail;
    private String authorPseudo;
    private String title;
    private Text content;
    private Date date;

    public Proposal() {
    }

    public Proposal(String content, String title) {
        this.title = title;
        this.content = new Text(content);
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
        return authorPseudo;
    }

    public void setAuthorPseudo(String authorPseudo) {
        this.authorPseudo = authorPseudo;
    }

    public String getContent() {
        return (content != null ? content.getValue():null);
    }
    public void setContent(String content) {
        this.content = new Text(content);
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        try {
            return new JSONObject()
                    .put("proposalId", proposalId)
                    .put("title", title)
                    .put("authorPseudo", authorPseudo)
                    .put("content", (content!= null ?content.getValue() : ""))
                    .toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public String toLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proposal[");
        sb.append("proposalId:").append(proposalId);
        sb.append(", title:").append(title);
        if (authorPseudo != null)
            sb.append(", authorPseudo:").append(authorPseudo);
        if (authorEmail != null)
            sb.append(", authorEmail:").append(authorEmail);
        sb.append(", content:").append(content);
        sb.append("]");
        return sb.toString();
    }
}
