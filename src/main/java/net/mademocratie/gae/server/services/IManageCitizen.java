package net.mademocratie.gae.server.services;

import com.google.appengine.api.users.User;
import com.google.inject.ImplementedBy;
import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.exception.*;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<Citizen> latest();

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
    Citizen registerGoogleUser(String pseudo, String googleEmail, boolean isAdmin) throws RegisterFailedException;
    Citizen register(String pseudo, String email) throws RegisterFailedException;
    void registerNotifyCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException;

    void notifyAdminReport() throws MaDemocratieException;

    Citizen getById(Long cId);

    Citizen activateCitizenByKey(Long cId, String activationKey) throws DeprecatedActivationLinkException, WrongActivationLinkException;

    void changeCitizenPassword(Long cId, String newPassword) throws ChangePasswordException;

    Citizen signInGoogleCitizen() throws MaDemocratieException;

    Citizen addCitizen(Citizen myAuthorA) throws CitizenAlreadyExistsException;

    void importCitizens(List<Citizen> citizens);

    void removeAll();

    void delete(Citizen testUser);

    Citizen getAuthenticatedUser(String authToken);

    Citizen checkCitizen(Citizen author) throws MaDemocratieException;

    Map<Key<Citizen>, Citizen> getCitizensByIds(Set<Key<Citizen>> keys);
}
