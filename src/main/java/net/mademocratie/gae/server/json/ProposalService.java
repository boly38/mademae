package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.ProposalInformations;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.IManageVote;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/proposal")
@Produces(MediaType.APPLICATION_JSON)
public class ProposalService extends AbstractMaDemocratieJsonService {
    Logger log = Logger.getLogger(ProposalService.class.getName());

    @Inject
    IManageProposal manageProposals;

    @Inject
    IManageCitizen manageCitizen;

    @Inject
    IManageVote manageVote;

    @POST
    @Path("/add")
    public String addProposal(Proposal proposal, @Context HttpHeaders httpHeaders) {
        if (proposal == null) return null;
        Citizen authenticatedUser = getAuthenticatedCitizen(httpHeaders);
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
    public ProposalInformations getProposal(@PathParam("id") String proposalId) {
        if (proposalId == null) return null;
        Long propId = Long.valueOf(proposalId);
        Proposal proposalRetrieved = manageProposals.getById(propId);
        ProposalVotes proposalVotes = manageVote.getProposalVotes(propId);
        ProposalInformations proposalInformations = new ProposalInformations(proposalRetrieved, proposalVotes);
        log.info("getProposal " + propId + " : " + proposalInformations.toString());
        return proposalInformations;
    }

    private Vote voteProposal(String proposalId, HttpHeaders httpHeaders, VoteKind voteKind) {
        Citizen authenticatedUser = getAuthenticatedCitizen(httpHeaders);
        String voteAuthorEmail = null;
        if (authenticatedUser != null) {
            voteAuthorEmail = authenticatedUser.getEmail();
        }
        return manageVote.vote(voteAuthorEmail, Long.valueOf(proposalId), voteKind);
    }

    @GET
    @Path("/proposal/vote/pro/{id}")
    public Vote voteProposalPro(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) {
        VoteKind voteKind = VoteKind.PRO;
        return voteProposal(proposalId, httpHeaders, voteKind);
    }

    @GET
    @Path("/proposal/vote/con/{id}")
    public Vote voteProposalCon(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) {
        VoteKind voteKind = VoteKind.CON;
        return voteProposal(proposalId, httpHeaders, voteKind);
    }

    @GET
    @Path("/proposal/vote/neutral/{id}")
    public Vote voteProposalNeutral(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) {
        VoteKind voteKind = VoteKind.NEUTRAL;
        return voteProposal(proposalId, httpHeaders, voteKind);
    }

    public IManageCitizen getManageCitizen() {
        return manageCitizen;
    }

}
