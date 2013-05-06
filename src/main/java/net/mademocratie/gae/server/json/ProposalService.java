package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageProposal;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.logging.Logger;

@Path("/proposal")
@Produces(MediaType.APPLICATION_JSON)
public class ProposalService {
    Logger log = Logger.getLogger(ProposalService.class.getName());

    @Inject
    IManageProposal manageProposals;

    @Inject
    IManageCitizen manageCitizen;

    @POST
    @Path("/add")
    public String addProposal(Proposal proposal, @Context HttpHeaders httpHeaders) {
        MultivaluedMap<String, String> headerParams = httpHeaders.getRequestHeaders();
        String authTokenKey = "md-authentification";
        Citizen authenticatedUser = null;
        if (headerParams.containsKey(authTokenKey)) {
            authenticatedUser = manageCitizen.getAuthenticatedUser(headerParams.getFirst(authTokenKey));
        }
        if (proposal == null) return null;
        Proposal newProposal = new Proposal(proposal.getTitle(), proposal.getContent());
        if (authenticatedUser != null) {
            newProposal.setAuthorEmail(authenticatedUser.getEmail());
            newProposal.setAuthorPseudo(authenticatedUser.getPseudo());
        }
        log.info("addProposal POST received : " + proposal.toString());
        Proposal addedProposal = manageProposals.addProposal(newProposal, null);
        log.info("addProposal POST received ; result=" + addedProposal.toString());
        return addedProposal.getItemIt().toString();
    }

    /*
     src: http://stackoverflow.com/questions/7430270/post-put-delete-http-json-with-additional-parameters-in-jersey-general-design
     */
    @GET
    @Path("/proposal/{id}")
    public Proposal getProposal(@PathParam("id") String proposalId) {
        if (proposalId == null) return null;
        return manageProposals.getById(Long.valueOf(proposalId));
    }


}
