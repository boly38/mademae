package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageCitizen;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/ops")
@Produces(MediaType.APPLICATION_JSON)
public class OpsService {
    Logger log = Logger.getLogger(OpsService.class.getName());

    @Inject
    IManageCitizen manageCitizen;

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
