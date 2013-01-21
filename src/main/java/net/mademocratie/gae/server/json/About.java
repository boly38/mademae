package net.mademocratie.gae.server.json;

import net.mademocratie.gae.server.domain.AboutInformations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/about")
public class About {
    /*
    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }*/

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public AboutInformations getInJSON() {
        return new AboutInformations();
    }
}
