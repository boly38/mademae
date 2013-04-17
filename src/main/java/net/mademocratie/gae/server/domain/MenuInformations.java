package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MenuInformations {
    String userPseudo;
    boolean userAdmin;

    public MenuInformations() {
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }
}
