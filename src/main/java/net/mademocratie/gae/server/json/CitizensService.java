package net.mademocratie.gae.server.json;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import net.mademocratie.gae.server.domain.*;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageCitizen;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * Citizen
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@Path("/citizen")
public class CitizensService extends AbstractMaDemocratieJsonService {
    Logger log = Logger.getLogger(CitizensService.class.getName());

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

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public MenuInformations getMenuInfo(@Context HttpHeaders httpHeaders) {
        Citizen authenticatedUser = getAuthenticatedCitizen(httpHeaders);
        if (authenticatedUser != null) {
            MenuInformations menuInformations = new MenuInformations();
            menuInformations.setUserPseudo(authenticatedUser.getPseudo());
            menuInformations.setUserAdmin(authenticatedUser.isAdmin());
            return menuInformations;
        }
        MenuInformations menuInformations = new MenuInformations();
        menuInformations.setUserPseudo(null);
        menuInformations.setUserAdmin(false);
        return menuInformations;
    }

    @POST
    @Path("/signIn")
    public SignInResponse singIn(SignInInformations signInInformations) {
        if (signInInformations== null) return null;
        log.info("singIn POST received : " + signInInformations.toLogString());
        Citizen citizen;
        try {
            citizen = manageCitizen.signInGoogleCitizen();
        } catch (MaDemocratieException e) {
            log.warning("unable to register notify a google user, details:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        if (citizen != null) {
            return new SignInResponse(citizen.getAuthToken(), citizen.getPseudo());
        }
        return new SignInResponse("unable to authenticate you", JsonServiceResponse.ResponseStatus.FAILED);
    }

    public IManageCitizen getManageCitizen() {
        return manageCitizen;
    }
}
