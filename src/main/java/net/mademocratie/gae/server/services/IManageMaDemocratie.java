package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.json.entities.DbImport;
import net.mademocratie.gae.server.entities.v1.DatabaseContentV1;
import net.mademocratie.gae.server.json.entities.GetContributionsResult;
import net.mademocratie.gae.server.json.entities.ProfileInformations;
import net.mademocratie.gae.server.json.entities.ProposalInformations;
import net.mademocratie.gae.server.entities.dto.CommentDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.impl.ManageMaDemocratieImpl;

import java.util.List;

@ImplementedBy(ManageMaDemocratieImpl.class)
public interface IManageMaDemocratie {
    ProfileInformations getProfileInformations(Citizen authenticatedUser) throws MaDemocratieException;

    GetContributionsResult getLastContributions(int maxContrib, int maxProposals);

    List<ProposalDTO> latestProposalsDTO(int max);
    List<CommentDTO> latestCommentsDTO(int max);

    ProposalInformations getProposalInformations(Long propId);

    boolean isUserAdmin();

    void notifyAdminReport() throws MaDemocratieException;

    Citizen getAuthenticatedUser(String authToken);

    DatabaseContentV1 dbExportV1();

    void dbImportV1(DbImport dbImport) throws MaDemocratieException;
}
