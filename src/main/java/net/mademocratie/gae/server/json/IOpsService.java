package net.mademocratie.gae.server.json;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.json.impl.OpsService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ops")
@Produces(MediaType.APPLICATION_JSON)
@ImplementedBy(OpsService.class)
public interface IOpsService {
    @GET
    @Path("/dbexport")
    @Produces(MediaType.APPLICATION_JSON)
    javax.ws.rs.core.Response dbExport() throws MaDemocratieException;

    @POST
    @Path("/dbimport")
    javax.ws.rs.core.Response dbImport(DbImport dbImport);

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    JsonServiceResponse sendReport() throws MaDemocratieException;
}
