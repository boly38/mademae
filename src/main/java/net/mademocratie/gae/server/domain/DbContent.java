package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.CommentContribution;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.Vote;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class DbContent {
    private ArrayList<Citizen> citizens;
    private ArrayList<Proposal> proposals;
    private ArrayList<Vote> votes;
    private ArrayList<CommentContribution> comments;

    public DbContent() {
    }

    public DbContent(ArrayList<Citizen> citizens, ArrayList<Proposal> proposals, ArrayList<Vote> votes, ArrayList<CommentContribution> commentContributions) {
        this.citizens = citizens;
        this.proposals = proposals;
        this.votes= votes;
        this.comments = commentContributions;
    }

    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    public ArrayList<CommentContribution> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentContribution> comments) {
        this.comments = comments;
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }
}
