package net.mademocratie.gae.server.services.impl;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.CitizenState;
import net.mademocratie.gae.server.exception.*;
import net.mademocratie.gae.server.services.IManageCitizen;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageCitizenImpl implements IManageCitizen {
    private final static Logger LOGGER = Logger.getLogger(ManageCitizenImpl.class.getName());

    private UserService userService = UserServiceFactory.getUserService();
    private static final String MADEM_FROM_EMAIL = "info@mademocratie.net";
    private static final String MADEM_FROM_NAME = "MaDemocratie";


    public Citizen suggestCitizen() {
        User user = userService.getCurrentUser();
        Citizen suggestCitizen = new Citizen();
        if (user != null) {
            suggestCitizen = new Citizen(user.getNickname(), user);
        }
        return suggestCitizen;
    }

    /**
     * add a new citizen to the database
     * @param inputCitizen citizen to add
     */
    public Citizen addCitizen(Citizen inputCitizen) throws CitizenAlreadyExistsException {
        if (findCitizenByEmail(inputCitizen.getEmail()) != null) {
            throw new CitizenAlreadyExistsException();
        }
        ofy().save().entity(inputCitizen).now();
        LOGGER.fine("addCitizen result " + inputCitizen.toString());
        return inputCitizen;
    }

    public Citizen findCitizenByEmail(String email) {
        Email emailVal = new Email(email);
        Citizen c= ofy().load().type(Citizen.class).filter("email", emailVal).first().get();
        LOGGER.info("findCitizenByEmail result " + (c != null ? c.toString() : "(none)"));
        return c;
    }

    public void removeAll() {
        // ofy().delete().type(Vote.class);
        // ofy().delete().type(Proposal.class);
        ofy().delete().type(Citizen.class);
    }

    public void delete(Citizen testUser) {
        LOGGER.info("delete testUser " + testUser.toString());
        ofy().delete().type(Citizen.class).id(testUser.getId()).now();
    }

    public Citizen getAuthenticatedUser(String authToken) {
        Citizen c= ofy().load().type(Citizen.class).filter("authToken", authToken).first().get();
        LOGGER.info("getAuthenticatedUser for " + authToken + " result " + (c != null ? c.toString() : "(none)"));
        return c;
    }

    public List<Citizen> latest(int max) {
        List<Citizen> latestCitizen = ofy().load().type(Citizen.class).limit(max).list();
        // TODO : add ".order("-date")" : desc order seems not working !?
        LOGGER.info("* latest citizens asked " + max + " result " + latestCitizen.size());
        return latestCitizen;
    }

    public Citizen authenticateGoogleCitizen() throws MaDemocratieException {
        User googleUser = getGoogleUser();
        if (googleUser == null) {
            return null;
        }
        String googleUserEmail = googleUser.getEmail();
        if (findCitizenByEmail(googleUserEmail) != null) {
            return openSession(googleUserEmail);
        }
        Citizen registeredCitizen = register(googleUser.getNickname(), googleUser, isGoogleUserAdmin());
        registerNotifyGoogleCitizen(registeredCitizen, "http://www.mademocratie.net");

        return openSession(googleUserEmail);
    }

    private Citizen openSession(String citizenEmail) {
        Citizen signedInCitizen = findCitizenByEmail(citizenEmail);
        String authToken = generateRandomString(10);
        signedInCitizen.setAuthToken(authToken);
        ofy().save().entity(signedInCitizen).now();
        LOGGER.fine("openSession for" + signedInCitizen.toString());
        return signedInCitizen;
    }

    public Citizen authenticateCitizen(String email, String password) {
        Citizen citizen = findCitizenByEmail(email);
        if (citizen == null) {
            LOGGER.warning("authenticate failed : unable to find user with this email : " + email);
            return null;
        }
        User googleUser = userService.getCurrentUser();
        if (googleUser != null
                && citizen.getGoogleUser() != null
                && googleUser.getEmail().equals(citizen.getEmail())) {
            LOGGER.info("authenticate google user " + email);
            return citizen;
        }
        if (citizen.isPasswordEqualsTo(password)) {
            LOGGER.info("authenticate citizen with this email : " + email);
            return citizen;
        }
        LOGGER.warning("authenticate failed : unable to match password for this email : " + email);
        return null;
    }


    public User getGoogleUser() {
        return userService.getCurrentUser();
    }
    public boolean isGoogleUserAdmin() {
        return userService.isUserAdmin();
    }

    public String getGoogleLoginURL(String destination) {
        return userService.createLoginURL(destination);
    }

    public String getGoogleLogoutURL(String destination) {
        return userService.createLogoutURL(destination);
    }


    public String generateRandomString(int size) {
        SecureRandom random = new SecureRandom();
        int base = 32;
        String str = new BigInteger(size*base, random).toString(base);
        if (str != null && str.length()>size) {
            return str.substring(0,size);
        }
        return str;
    }

    public void registerNotifyCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException {
        sendMail(justRegisteredCitizen.getEmail(),
                justRegisteredCitizen.getPseudo(),
                "Welcome on MaDemocratie.net",
                "To complete your registration, please follow this link : " + activateDestination);
    }

    public void registerNotifyGoogleCitizen(Citizen justRegisteredCitizen, String activateDestination) throws MaDemocratieException {
        sendMail(justRegisteredCitizen.getEmail(),
                justRegisteredCitizen.getPseudo(),
                "Welcome on MaDemocratie.net",
                "You just complete your registration, so hope you will be back soon : " + activateDestination);
    }

    private void sendMail(String toEmail, String toString, String title, String body) throws MaDemocratieException {
        Properties props = new Properties();
        // props.put("mail.smtp.host", "smtp");
        // props.put("mail.smtp.port", 25);
        Session session = Session.getDefaultInstance(props);
        Message msg = new MimeMessage(session);
        String sendMailLogStr = "sendMail to " + toEmail + " title=" + title + " body=" + body;
        try {
            msg.setFrom(new InternetAddress(MADEM_FROM_EMAIL, MADEM_FROM_NAME));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail, toString));
            msg.setSubject(title);
            msg.setText(body);
            Transport.send(msg);
            LOGGER.info(sendMailLogStr);
        } catch (MessagingException e) {
            LOGGER.severe("Exception while " + sendMailLogStr + ": " + e.getMessage());
            e.printStackTrace();
            throw new MaDemocratieException("Unable to send mail ; registration process is broken, please report this error to the support.");
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Exception while " + sendMailLogStr + ": " + e.getMessage());
            e.printStackTrace();
            throw new MaDemocratieException("Unable to send mail ; registration process is broken, please report this error to the support.");
        }
    }

    private void handleCitizenAlreadyExistWhenRegister(CitizenAlreadyExistsException e, String citizenDescription) throws RegisterFailedException {
        LOGGER.warning("CitizenAlreadyExistsException while register citizen " + citizenDescription + " : " + e.getMessage());
        throw new RegisterFailedException("Unable to register with this email, you should already been registered.");
    }

    public Citizen register(String pseudo, User googleUser, boolean isAdmin) throws RegisterFailedException {
        Citizen newCitizen = new Citizen(pseudo, googleUser);
        newCitizen.setAdmin(isAdmin);
        try {
            addCitizen(newCitizen);
        } catch (CitizenAlreadyExistsException e) {
            handleCitizenAlreadyExistWhenRegister(e, newCitizen.toString());
        }
        return newCitizen;
    }

    public Citizen register(String pseudo, String email) throws RegisterFailedException {
        Citizen newCitizen = new Citizen(pseudo, generateRandomString(10), email, generateRandomString(32));
        try {
            addCitizen(newCitizen);
        } catch (CitizenAlreadyExistsException e) {
            handleCitizenAlreadyExistWhenRegister(e, newCitizen.toString());
        }
        return newCitizen;

    }

    public Citizen getById(Long cId) {
        return ofy().load().type(Citizen.class).id(cId).get();
    }

    public Citizen activateCitizenByKey(Long cId, String activationKey) throws DeprecatedActivationLinkException, WrongActivationLinkException {
        Citizen citizen = getById(cId);
        activationCheckCitizenState(citizen);
        if (activationKey != null && citizen.getCitizenStateData().equals(activationKey)) {
            activateCitizen(citizen);
            return citizen;
        } else {
            throw new WrongActivationLinkException();
        }
    }

    private void activateCitizen(Citizen citizenToActivate) {
        citizenToActivate.setCitizenState(CitizenState.ACTIVE);
        citizenToActivate.setCitizenStateData((new Date()).toString());
        ofy().save().entity(citizenToActivate).now();
    }

    private void activationCheckCitizenState(Citizen justRegisteredCitizen) throws DeprecatedActivationLinkException {
        if (justRegisteredCitizen == null) {
            LOGGER.warning("deprecated activation link (citizen null)");
            throw new DeprecatedActivationLinkException();
        }
        CitizenState citizenState = justRegisteredCitizen.getCitizenState();
        if (citizenState == null) {
            LOGGER.warning("activation link of a wrong state citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.SUSPENDED.equals(citizenState)) {
            LOGGER.warning("activation link of a suspended citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.REMOVED.equals(citizenState)) {
            LOGGER.warning("activation link of a removed citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
        if (CitizenState.ACTIVE.equals(citizenState)) {
            LOGGER.warning("activation link of an active citizen " + justRegisteredCitizen);
            throw new DeprecatedActivationLinkException();
        }
    }

    private void changePasswordCheckCitizenState(Citizen citizen) throws ChangePasswordException {
        if (citizen == null) {
            LOGGER.warning("unable to change password of a null citizen");
            throw new ChangePasswordException();
        }
        CitizenState citizenState = citizen.getCitizenState();
        if (citizenState == null) {
            LOGGER.warning("unable to change password of a wrong state citizen " + citizen);
            throw new ChangePasswordException();
        }
        if (CitizenState.SUSPENDED.equals(citizenState)) {
            LOGGER.warning("unable to change password of a suspended citizen " + citizen);
            throw new ChangePasswordException("your account has been suspended, please contact administrator.");
        }
        if (CitizenState.REMOVED.equals(citizenState)) {
            LOGGER.warning("\"unable to change password of a removed citizen " + citizen);
            throw new ChangePasswordException("your account doesn't no more exist");
        }
    }


    public void changeCitizenPassword(Long cId, String newPassword) throws ChangePasswordException {
        Citizen citizen = getById(cId);
        changePasswordCheckCitizenState(citizen);
        citizen.setPassword(newPassword);
        ofy().save().entity(citizen).now();
    }

    public Citizen signInGoogleCitizen() throws MaDemocratieException {
        return authenticateGoogleCitizen();
    }


    //~ getters && setters
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
