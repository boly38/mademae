package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.domain.ProposalInformations;
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
}
