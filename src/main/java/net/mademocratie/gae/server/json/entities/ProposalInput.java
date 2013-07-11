package net.mademocratie.gae.server.json.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProposalInput {
    String title;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
