package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ResourceBundle;

@XmlRootElement
public class AboutInformations {
    String version;
    String buildDate;

    public AboutInformations() {
        ResourceBundle messages = ResourceBundle.getBundle("messages");
        version = messages.getString("about.version");
        buildDate = messages.getString("about.buildDate");
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
