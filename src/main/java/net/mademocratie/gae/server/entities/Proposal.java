package net.mademocratie.gae.server.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;


@Entity
public class Proposal {
    @Id
    Long id;
    private String authorEmail;
    private String authorPseudo;
    private String title;
    private String content;
    private Date date;

    private Proposal() {
    }

    public Proposal(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorPseudo() {
        return authorPseudo;
    }

    public void setAuthorPseudo(String authorPseudo) {
        this.authorPseudo = authorPseudo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("proposal[");
        sb.append("id:").append(id);
        sb.append(", title:").append(title);
        if (authorPseudo != null)
            sb.append(", authorPseudo:").append(authorPseudo);
        if (authorEmail != null)
            sb.append(", authorEmail:").append(authorEmail);
        sb.append(", content:").append(content);
        sb.append("]");
        return sb.toString();
    }
}