package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.Proposal;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class ProfileInformations {
    List<Proposal> proposals;
    String pseudo;
    Date registrationDate;

    public ProfileInformations() {
        proposals = new ArrayList<Proposal>();
    }

    public ProfileInformations(Citizen authenticatedUser) {
        proposals = new ArrayList<Proposal>();
        registrationDate = authenticatedUser.getDate();
        pseudo = authenticatedUser.getPseudo();
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
