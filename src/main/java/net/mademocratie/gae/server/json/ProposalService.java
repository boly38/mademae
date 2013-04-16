package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.services.IManageProposal;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/proposal")
@Produces(MediaType.APPLICATION_JSON)
public class ProposalService {
    Logger log = Logger.getLogger(ProposalService.class.getName());

    @Inject
    IManageProposal manageProposals;

    @POST
    @Path("/add")
    public String addProposal(net.mademocratie.gae.server.entities.Proposal proposal) {
        if (proposal == null) return null;
        net.mademocratie.gae.server.entities.Proposal newProposal = new net.mademocratie.gae.server.entities.Proposal(proposal.getTitle(), proposal.getContent());
        log.info("addProposal POST received : " + proposal.toString());
        net.mademocratie.gae.server.entities.Proposal addedProposal = manageProposals.addProposal(newProposal, null);
        log.info("addProposal POST received ; result=" + addedProposal.toString());
        return addedProposal.getItemIt().toString();
    }

    /*
     src: http://stackoverflow.com/questions/7430270/post-put-delete-http-json-with-additional-parameters-in-jersey-general-design
     */
    @GET
    @Path("/proposal/{id}")
    public net.mademocratie.gae.server.entities.Proposal getProposal(@PathParam("id") String proposalId) {
        if (proposalId == null) return null;
        return manageProposals.getById(Long.valueOf(proposalId));
    }


}
