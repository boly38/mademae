package net.mademocratie.gae.server.json;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.LoginInformations;
import net.mademocratie.gae.server.services.IManageCitizen;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Citizen
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@Path("/citizen")
public class Citizen {
    @Inject
    IManageCitizen manageCitizen;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginInformations getLoginInfo() {
        User googleUser = manageCitizen.getGoogleUser();
        LoginInformations loginInformations = new LoginInformations();
        if (googleUser != null) {
            loginInformations.setGoogleUserPseudo(googleUser.getNickname());
        }
        loginInformations.setGoogleSignOutUrl(manageCitizen.getGoogleLogoutURL("/?redirect=login"));
        loginInformations.setGoogleSignInUrl(manageCitizen.getGoogleLoginURL("/?redirect=login"));
        return loginInformations;
    }
}
