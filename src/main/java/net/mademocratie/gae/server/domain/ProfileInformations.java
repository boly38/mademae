package net.mademocratie.gae.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.services.helper.DateHelper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class ProfileInformations {
    List<ProposalDTO> proposals;
    String pseudo;
    Date registrationDate;

    public ProfileInformations() {
        proposals = new ArrayList<ProposalDTO>();
    }

    public ProfileInformations(Citizen authenticatedUser) {
        proposals = new ArrayList<ProposalDTO>();
        if (authenticatedUser == null) {
            return;
        }
        registrationDate = authenticatedUser.getDateValue();
        pseudo = authenticatedUser.getPseudo();
    }

    public void setProposals(List<ProposalDTO> proposals) {
        this.proposals = proposals;
    }

    public List<ProposalDTO> getProposals() {
        return proposals;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationDateFormat() {
        return DateHelper.getDateFormat(getRegistrationDate());
    }

    @JsonProperty("age")
    public String getRegistrationAge() {
        return DateHelper.getDateDuration(getRegistrationDate());
    }
}
