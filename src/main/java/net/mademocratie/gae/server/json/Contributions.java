package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageContributions;
import net.mademocratie.gae.server.services.IManageProposal;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("/contributions")
public class Contributions {
    Logger log = Logger.getLogger(Contributions.class.getName());

    @Inject
    IManageProposal manageProposals;
    @Inject
    IManageContributions manageContributions;

    public class GetContributionsResult {
        private String description;
        private List<IContribution> contributions;

        GetContributionsResult(String description, List<IContribution> contributions) {
            this.description = description;
            this.contributions = contributions;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<IContribution> getContributions() {
            return contributions;
        }

        public void setContributions(List<IContribution> contributions) {
            this.contributions = contributions;
        }
        public JSONObject toJSON() {
             return new JSONObject(this);
        }
    }

    @GET
    @Path("/last")
    @Produces(MediaType.APPLICATION_JSON)
    public String getContributions() {
        List<IContribution> lastContributions = manageContributions.getLastContributions(10);
        String resultTitle = lastContributions.size() + " last contributions";
        GetContributionsResult result = new GetContributionsResult(resultTitle, lastContributions);
        return result.toJSON().toString();
    }


    @GET
    @Path("/addSample")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonServiceResponse addSample() {
        try {
            Proposal proposal = addSampleProposition();
            return new JsonServiceResponse(proposal.toString(), "add sample proposition is a success", JsonServiceResponse.ResponseStatus.OK);
        } catch (Exception e) {
            return new JsonServiceResponse(e.getMessage(), "unable to add sample proposition", JsonServiceResponse.ResponseStatus.FAILED);
        }
    }

    private Proposal addSampleProposition() {
        Proposal testProposal = new Proposal("Test My App", "This is a generated proposal used to test the product");
        log.info("addProposal input " + testProposal.toString());
        Proposal addedProposal = manageProposals.addProposal(testProposal, null);
        return addedProposal;
    }

}
