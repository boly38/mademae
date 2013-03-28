package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageProposal;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;

@Path("/proposals")
public class Proposals {
    Logger log = Logger.getLogger(Proposals.class.getName());


    @Inject
    IManageProposal manageProposals;

    @POST
    @Path("/add")
    public void addProposal(Proposal proposal) {
        if (proposal == null) return;
        Proposal newProposal = new Proposal(proposal.getTitle(), proposal.getContent());
        Proposal addedProposal = manageProposals.addProposal(newProposal, null);
        log.info("addProposal POST received ; result=" + addedProposal.toString());
    }
}
