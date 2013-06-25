package net.mademocratie.gae.server.json.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageMaDemocratie;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * AbstractMaDemocratieJsonService
 */
public abstract class AbstractMaDemocratieJsonService {

    @Inject
    IManageMaDemocratie manageMD;


    protected Response returnForbidden() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    protected Response redirectHome() {
        try {
            return Response.temporaryRedirect(new URI("/")).build();
        } catch (URISyntaxException e) {
            return Response.serverError().build();
        }
    }

    protected Citizen getAuthenticatedCitizen(HttpHeaders httpHeaders) {
        MultivaluedMap<String, String> headerParams = httpHeaders.getRequestHeaders();
        String authTokenKey = "md-authentication";
        Citizen authenticatedUser = null;
        if (headerParams.containsKey(authTokenKey)) {
            authenticatedUser = manageMD.getAuthenticatedUser(headerParams.getFirst(authTokenKey));
        }
        return authenticatedUser;
    }

}
