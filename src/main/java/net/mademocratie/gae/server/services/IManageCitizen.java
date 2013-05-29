package net.mademocratie.gae.server.services;

import com.google.appengine.api.users.User;
import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.exception.*;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;

import java.util.List;

@ImplementedBy(ManageCitizenImpl.class)
public interface IManageCitizen {
    /**
     * suggest Citizen attributes by analysing current session.
     * @return
     */
    Citizen suggestCitizen();

    /**
     * Return the latest citizens, ordered by descending date.
     *
     * @param max the maximum number of citizens to return
     * @return the citizens
     */
    List<Citizen> latest(int max);

    public Citizen findCitizenByEmail(String email);

    /**
     * Authenticate a citizen and return it (or null)
     * @param email
     * @param password
     * @return
     */
    Citizen authenticateCitizen(String email, String password);

    User getGoogleUser();
    boolean isGoogleUserAdmin();


    String getGoogleLoginURL(String destination);
    String getGoogleLogoutURL(String destination);

    Citizen register(String pseudo, User googleUser, boolean isAdmin) throws RegisterFailedException;
    Citizen register(String pseudo, String email) throws RegisterFailedException;
    void registerNotifyCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException;
    void notifyAdminReport() throws MaDemocratieException;

    Citizen getById(Long cId);

    Citizen activateCitizenByKey(Long cId, String activationKey) throws DeprecatedActivationLinkException, WrongActivationLinkException;

    void changeCitizenPassword(Long cId, String newPassword) throws ChangePasswordException;

    Citizen signInGoogleCitizen() throws MaDemocratieException;

    Citizen addCitizen(Citizen myAuthorA) throws CitizenAlreadyExistsException;

    void removeAll();

    void delete(Citizen testUser);

    Citizen getAuthenticatedUser(String authToken);
}
