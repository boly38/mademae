package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.DbContent;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Comment;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.entities.v1.Vote;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageComment;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.IManageVote;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/ops")
@Produces(MediaType.APPLICATION_JSON)
public class OpsService extends AbstractMaDemocratieJsonService {
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
    public javax.ws.rs.core.Response dbExport() throws MaDemocratieException {
        if (!manageCitizen.isGoogleUserAdmin()) {
            log.warning("unable to send dbExport : not allowed");
            return returnForbidden();
        }
        List<Citizen> citizens = manageCitizen.latest();
        List<Proposal> proposals = manageProposals.latest();
        List<Vote> votes = manageVote.latest();
        List<Comment> comments = manageComment.latest();
        DbContent exportResult = new DbContent(
                new ArrayList<Citizen>(citizens),
                new ArrayList<Proposal>(proposals),
                new ArrayList<Vote>(votes),
                new ArrayList<Comment>(comments)
        );
        //return exportResult.toJSON().toString();
        return javax.ws.rs.core.Response.ok(exportResult).type(MediaType.APPLICATION_JSON_TYPE).build();
    }



    @POST
    @Path("/dbimport")
    public javax.ws.rs.core.Response dbImport(DbImport dbImport) {
        if (dbImport== null) return null;
        JSONObject jsonDbImport = new JSONObject(dbImport);
        log.info("dbImport POST received : " + jsonDbImport.toString());
        return javax.ws.rs.core.Response.ok().build();
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

    @Override
    IManageCitizen getManageCitizen() {
        return manageCitizen;
    }
}
