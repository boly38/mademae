package net.mademocratie.gae.server.json;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.json.entities.*;
import net.mademocratie.gae.server.services.IManageCitizen;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

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
        loginInformations.setGoogleSignInUrl(manageCitizen.getGoogleLoginURL("/?redirect=autologin"));
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
    public SignInResponse singIn() {
        log.info("singIn POST received");
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

    @GET
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public String geProfile(@Context HttpHeaders httpHeaders) throws MaDemocratieException {
        Citizen authenticatedUser = getAuthenticatedCitizen(httpHeaders);
        ProfileInformations profileInfos = manageMD.getProfileInformations(authenticatedUser);
        JSONObject jsonProfileInformations = new JSONObject(profileInfos);
        return jsonProfileInformations.toString();
    }
}
