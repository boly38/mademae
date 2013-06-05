package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.DbExportResult;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.CommentContribution;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.Vote;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageComment;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.IManageVote;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/ops")
@Produces(MediaType.APPLICATION_JSON)
public class OpsService {
    Logger log = Logger.getLogger(OpsService.class.getName());

    @Inject
    IManageProposal manageProposals;

    @Inject
    IManageCitizen manageCitizen;

    @Inject
    IManageVote manageVote;

    @Inject
    IManageComment manageComment;


    @GET
    @Path("/dbexport")
    @Produces(MediaType.APPLICATION_JSON)
    public String dbExport() throws MaDemocratieException {
        if (!manageCitizen.isGoogleUserAdmin()) {
            return "unable to send dbExport : not allowed";
        }
        List<Citizen> citizens = manageCitizen.latest(100000);
        List<Proposal> proposals = manageProposals.latest(100000);
        List<Vote> votes = manageVote.latest(100000);
        List<CommentContribution> comments = manageComment.latest(100000);
        DbExportResult exportResult = new DbExportResult(
                new ArrayList<Citizen>(citizens),
                new ArrayList<Proposal>(proposals),
                new ArrayList<Vote>(votes),
                new ArrayList<CommentContribution>(comments)
        );
        return exportResult.toJSON().toString();
    }


    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonServiceResponse sendReport() throws MaDemocratieException {
        if (!manageCitizen.isGoogleUserAdmin()) {
            return new JsonServiceResponse("unable to send report : not allowed", JsonServiceResponse.ResponseStatus.FAILED);
        }
        try {
            manageCitizen.notifyAdminReport();
            return new JsonServiceResponse("", JsonServiceResponse.ResponseStatus.OK);
        } catch (MaDemocratieException e) {
            log.warning("unable to notify admin report, details:" + e.getMessage());
            e.printStackTrace();
            return new JsonServiceResponse("unable to send report", JsonServiceResponse.ResponseStatus.FAILED);
        }
    }
}
