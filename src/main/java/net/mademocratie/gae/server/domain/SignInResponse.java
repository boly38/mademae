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
    String authToken;

    public SignInResponse() {
    }

    public SignInResponse(String authToken) {
        this.setStatus(ResponseStatus.OK);
        this.authToken = authToken;
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
}
