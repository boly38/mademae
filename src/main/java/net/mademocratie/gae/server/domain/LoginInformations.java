package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginInformations {
    String googleUserPseudo;
    String googleSignInUrl;
    String googleSignOutUrl;
    String authToken;

    public LoginInformations() {
    }

    public String getGoogleSignInUrl() {
        return googleSignInUrl;
    }

    public void setGoogleSignInUrl(String googleSignInUrl) {
        this.googleSignInUrl = googleSignInUrl;
    }

    public String getGoogleSignOutUrl() {
        return googleSignOutUrl;
    }

    public void setGoogleSignOutUrl(String googleSignOutUrl) {
        this.googleSignOutUrl = googleSignOutUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getGoogleUserPseudo() {
        return googleUserPseudo;
    }

    public void setGoogleUserPseudo(String googleUserPseudo) {
        this.googleUserPseudo = googleUserPseudo;
    }
}
