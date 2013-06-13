package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.ProposalList;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;

import java.util.List;

@ImplementedBy(ManageProposalImpl.class)
public interface IManageProposal {

    /**
     * add a new proposal to the database
     */
    public Proposal addProposal(Proposal inputProposal, Citizen author);
    public Proposal addProposal(Proposal inputProposal);

    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);

    ProposalList latestAsList(int max);

    List<Proposal> latest();

    /**
     * remove all proposals from the repository (test usage only)
     */
    void removeAll();

    Proposal getById(Long proposalId);

    List<ProposalDTO> findByAuthor(Citizen author) throws MaDemocratieException;
}
