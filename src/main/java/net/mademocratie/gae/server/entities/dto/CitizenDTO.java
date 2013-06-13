package net.mademocratie.gae.server.entities.dto;

import net.mademocratie.gae.server.entities.v1.Citizen;

import java.util.Date;

/**
 * CitizenDTO
 */
public class CitizenDTO {
    private Long id;
    private Date date;
    private String pseudo;
    private String email;
    private String location;
    private boolean admin;

    public CitizenDTO() {
    }

    public CitizenDTO(Citizen citizen) {
        this.id = citizen.getId();
        this.date = citizen.getDate();
        this.pseudo = citizen.getPseudo();
        this.email = citizen.getEmail();
        this.location = citizen.getLocation();
        this.admin = citizen.isAdmin();
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
