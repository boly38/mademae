package net.mademocratie.gae.server.entities.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import net.mademocratie.gae.server.entities.dto.CitizenDTO;
import sun.util.calendar.BaseCalendar;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Citizen {

    @Id
    private Long id;


    @Index
    private String authToken;

    /*
     * citizen creation date
     */
    public static final String CITIZEN_DATE = "date";
    @Index
    private Date date;

    private String pseudo;

    /**
     * citizen password: null if googleuser is set
     */
    private String password;

    @Index
    private Email email;

    private String citizenState;

    /**
     * depend on citizenState value :
     * case CREATED : citizenStateData = registrationUniqueKey
     * case ACTIVE  : citizenStateData = null
     * case SUSPENDED : citizenStateData = (admin) reason
     * case REMOVED : citizenStateData = date removed, old pseudo used, reason why removed.
     */
    private String citizenStateData;

    private String location;

    private boolean admin;

    private CitizenAuthProvider authProvider = CitizenAuthProvider.NONE;

    public Citizen() {
    }

    public Citizen(String pseudo, String ggEmail) {
        this.pseudo = pseudo;
        this.email = new Email(ggEmail);
        this.date = new Date();
        this.citizenState = CitizenState.ACTIVE.value();
        this.citizenStateData = (new Date()).toString();
        this.authProvider = CitizenAuthProvider.GOOGLE;
    }

    public Citizen(String pseudo, String password, String email, String accessKey) {
        this.pseudo = pseudo;
        this.password = password;
        this.email = (email != null ? new Email(email) : null);
        date = new Date();
        citizenState = CitizenState.CREATED.value();
        citizenStateData = accessKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return (email != null ? email.getEmail() : null);
    }

    public void setEmailFromString(String email) {
        this.email = (email != null ? new Email(email) : null);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthProvider() {
        return authProvider.value();
    }

    @JsonIgnore
    public CitizenAuthProvider getAuthProviderEnum() {
        return authProvider;
    }

    public void setAuthProvider(CitizenAuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(getDateValue());
    }

    @JsonIgnore
    public Date getDateValue() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonIgnore
    public String getAuthToken() {
        return authToken;
    }

    @JsonProperty
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getCitizenState() {
        return citizenState;
    }
    @JsonIgnore
    public CitizenState getCitizenStateEnum() {
        return CitizenState.fromValue(citizenState);
    }

    public void setCitizenState(CitizenState citizenState) {
        this.citizenState = citizenState.value();
    }

    public String getCitizenStateData() {
        return citizenStateData;
    }

    public void setCitizenStateData(String citizenStateData) {
        this.citizenStateData = citizenStateData;
    }

    public boolean isPasswordEqualsTo(String password) {
        return this.password != null && this.password.equals(password);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static CitizenDTO createAnonymous() {
        CitizenDTO anonymous = new CitizenDTO();
        anonymous.setPseudo("anonymous");
        anonymous.setEmail(null);
        anonymous.setAdmin(false);
        return anonymous;
    }

    public static CitizenDTO createSomeone() {
        CitizenDTO anonymous = new CitizenDTO();
        anonymous.setPseudo("someone");
        anonymous.setEmail(null);
        anonymous.setAdmin(false);
        return anonymous;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Citizen{");
        sb.append("admin=").append(admin);
        sb.append(", id=").append(id);
        sb.append(", authToken='").append(authToken).append('\'');
        sb.append(", date=").append(date);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", password=**hashed='").append(password != null ? password.hashCode() : -1).append("'**");
        sb.append(", email=").append(email);
        sb.append(", citizenState=").append(citizenState);
        sb.append(", citizenStateData='").append(citizenStateData).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", authProvider=").append(authProvider);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Citizen)) return false;

        Citizen citizen = (Citizen) o;

        if (admin != citizen.admin) return false;
        if (authProvider != citizen.authProvider) return false;
        if (authToken != null ? !authToken.equals(citizen.authToken) : citizen.authToken != null) return false;
        if (!citizenState.equals(citizen.citizenState)) return false;
        if (citizenStateData != null ? !citizenStateData.equals(citizen.citizenStateData) : citizen.citizenStateData != null) return false;
        if (!date.equals(citizen.date)) return false;
        if (!email.equals(citizen.email)) return false;
        if (!id.equals(citizen.id)) return false;
        if (location != null ? !location.equals(citizen.location) : citizen.location != null) return false;
        if (!password.equals(citizen.password)) return false;
        if (!pseudo.equals(citizen.pseudo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (authToken != null ? authToken.hashCode() : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + pseudo.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + citizenState.hashCode();
        result = 31 * result + (citizenStateData != null ? citizenStateData.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + authProvider.hashCode();
        return result;
    }
}
