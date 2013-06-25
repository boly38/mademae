package net.mademocratie.gae.server.json;

import net.mademocratie.gae.server.domain.AboutInformations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/about")
public class AboutService {
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public AboutInformations getInfo() {
        return new AboutInformations();
    }
}
