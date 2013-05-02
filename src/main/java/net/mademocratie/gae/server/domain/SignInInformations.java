package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SignInInformations {
    String username;
    String password;

    public SignInInformations() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String toLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SignInInformations[");
        sb.append("username:").append(getUsername());
        sb.append(", password: ***hiden***").append("]");
        return sb.toString();
    }
}