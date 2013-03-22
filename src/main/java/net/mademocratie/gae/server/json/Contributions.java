package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.ContributionsInformations;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageContributions;
import net.mademocratie.gae.server.services.IManageProposal;

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

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ContributionsInformations getInJSON() {
        addSampleProposition();
        List<IContribution> lastContributions = manageContributions.getLastContributions(10);
        return new ContributionsInformations(lastContributions);
    }

    private void addSampleProposition() {
        Proposal testProposal = new Proposal("Test My App", "This is a generated proposal used to test the product");
        log.info("addProposal input " + testProposal.toString());
        manageProposals.addProposal(testProposal, null);
    }

}