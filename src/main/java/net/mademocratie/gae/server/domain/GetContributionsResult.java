package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.Contribution;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class GetContributionsResult {
    private String description;
    private ArrayList<Contribution> contributions;

    public GetContributionsResult() {
    }

    public GetContributionsResult(String description, ArrayList<Contribution> contributions) {
        this.description = description;
        this.contributions = contributions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<Contribution> contributions) {
        this.contributions = contributions;
    }
    public JSONObject toJSON() {
        return new JSONObject(this);
    }
}
