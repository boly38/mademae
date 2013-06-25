package net.mademocratie.gae.server.json;

import net.mademocratie.gae.server.entities.v1.DatabaseContentV1;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.exception.MaDemocratieException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/ops")
@Produces(MediaType.APPLICATION_JSON)
public class OpsService extends AbstractMaDemocratieJsonService implements IOpsService {
    Logger log = Logger.getLogger(OpsService.class.getName());

    @GET
    @Path("/dbexport")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response dbExport() throws MaDemocratieException {
        if (!manageMD.isUserAdmin()) {
            log.warning("unable to send dbExport : not allowed");
            return returnForbidden();
        }
        DatabaseContentV1 exportResult = manageMD.dbExportV1();
        return javax.ws.rs.core.Response.ok(exportResult).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/dbimport")
    public javax.ws.rs.core.Response dbImport(DbImport dbImport) {
        manageMD.dbImportV1(dbImport);
        return javax.ws.rs.core.Response.ok().build();
    }


    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonServiceResponse sendReport() throws MaDemocratieException {
        try {
            manageMD.notifyAdminReport();
            return new JsonServiceResponse("", JsonServiceResponse.ResponseStatus.OK);
        } catch (MaDemocratieException e) {
            log.warning("unable to notify admin report, details:" + e.getMessage());
            e.printStackTrace();
            return new JsonServiceResponse("unable to send report", JsonServiceResponse.ResponseStatus.FAILED);
        }
    }
}
