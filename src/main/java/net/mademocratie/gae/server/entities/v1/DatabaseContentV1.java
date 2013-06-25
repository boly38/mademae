package net.mademocratie.gae.server.entities.v1;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import net.mademocratie.gae.server.entities.IDatabaseContent;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class DatabaseContentV1 implements IDatabaseContent {
    private final String schemaVersion = "v1";
    private List<Citizen> citizens;
    private List<Proposal> proposals;
    private List<Vote> votes;
    private List<Comment> comments;

    public DatabaseContentV1() {
    }

    public DatabaseContentV1(List<Citizen> citizens, List<Proposal> proposals, List<Vote> votes, List<Comment> comments) {
        this.citizens = citizens;
        this.proposals = proposals;
        this.votes= votes;
        this.comments = comments;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<Citizen> citizens) {
        this.citizens = citizens;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DatabaseContentV1{");
        sb.append("citizens=").append(citizens);
        sb.append(", schemaVersion='").append(schemaVersion).append('\'');
        sb.append(", proposals=").append(proposals);
        sb.append(", votes=").append(votes);
        sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }
}
