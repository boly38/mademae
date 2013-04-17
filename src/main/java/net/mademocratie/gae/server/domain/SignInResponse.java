package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * SignInResponse
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@XmlRootElement
public class SignInResponse extends JsonServiceResponse {
    String userPseudo;
    String authToken;

    public SignInResponse() {
    }

    public SignInResponse(String authToken, String userPseudo) {
        this.setStatus(ResponseStatus.OK);
        this.authToken = authToken;
        this.userPseudo = userPseudo;
    }

    public SignInResponse(String message, ResponseStatus status) {
        super(message, status);
        this.authToken = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }
}
