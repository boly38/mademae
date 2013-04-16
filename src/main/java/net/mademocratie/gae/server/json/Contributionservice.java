package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageContributions;
import net.mademocratie.gae.server.services.IManageProposal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/contribution")
@Produces(MediaType.APPLICATION_JSON)
public class Contributionservice {
    Logger log = Logger.getLogger(Contributionservice.class.getName());

    @Inject
    IManageProposal manageProposals;
    @Inject
    IManageContributions manageContributions;


    @GET
    @Path("/last")
    public String getContributions() {
        List<net.mademocratie.gae.server.entities.Contribution> lastContributions = manageContributions.getLastContributions(10);
        String resultTitle = lastContributions.size() + " last contributions";
        GetContributionsResult result = new GetContributionsResult(resultTitle, new ArrayList<net.mademocratie.gae.server.entities.Contribution>(lastContributions));
        return result.toJSON().toString();
    }


    @GET
    @Path("/addSample")
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
