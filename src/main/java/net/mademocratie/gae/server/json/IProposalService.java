package net.mademocratie.gae.server.json;

import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.entities.v1.Vote;
import net.mademocratie.gae.server.exception.AnonymousCantVoteException;
import net.mademocratie.gae.server.exception.MaDemocratieException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/proposal")
@Produces(MediaType.APPLICATION_JSON)
public interface IProposalService {
    @GET
    @Path("/last")
    String getProposals();

    @POST
    @Path("/add")
    String addProposal(Proposal proposal, @Context HttpHeaders httpHeaders);

    @POST
    @Path("/addcomment")
    String addProposalComment(Comment inComment, @Context HttpHeaders httpHeaders);

    /*
         src: http://stackoverflow.com/questions/7430270/post-put-delete-http-json-with-additional-parameters-in-jersey-general-design
         */
    @GET
    @Path("/proposal/{id}")
    String getProposal(@PathParam("id") String proposalId);

    @GET
    @Path("/proposal/vote/pro/{id}")
    Vote voteProposalPro(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) throws AnonymousCantVoteException, MaDemocratieException;

    @GET
    @Path("/proposal/vote/con/{id}")
    Vote voteProposalCon(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) throws AnonymousCantVoteException, MaDemocratieException;

    @GET
    @Path("/proposal/vote/neutral/{id}")
    Vote voteProposalNeutral(@PathParam("id") String proposalId, @Context HttpHeaders httpHeaders) throws AnonymousCantVoteException, MaDemocratieException;
}
