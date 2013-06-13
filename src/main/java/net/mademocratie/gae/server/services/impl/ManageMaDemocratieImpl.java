package net.mademocratie.gae.server.services.impl;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.domain.ProposalInformations;
import net.mademocratie.gae.server.entities.CommentList;
import net.mademocratie.gae.server.entities.ProposalList;
import net.mademocratie.gae.server.entities.dto.CommentDTO;
import net.mademocratie.gae.server.entities.dto.ContributionDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ManageMaDemocratieImpl implements IManageMaDemocratie {
    private final static Logger LOGGER = Logger.getLogger(ManageMaDemocratieImpl.class.getName());

    @Inject
    IManageCitizen manageCitizen;

    @Inject
    IManageProposal manageProposal;

    @Inject
    IManageVote manageVote;

    @Inject
    IManageComment manageComment;

    @Inject
    IManageContributions manageContribution;


    public ProfileInformations getProfileInformations(Citizen user) throws MaDemocratieException {
        ProfileInformations profileInfos = new ProfileInformations(user);
        List<ProposalDTO> proposals = manageProposal.findByAuthor(user);
        profileInfos.setPseudo(user.getPseudo());
        profileInfos.setProposals(proposals);
        profileInfos.setRegistrationDate(user.getDate());
        return profileInfos;
    }

    public List<ContributionDTO> getLastContributions(int maxContributions) {
        List<ProposalDTO> latestProposals = latestProposalsDTO(maxContributions);
        List<CommentDTO> latestComments = latestCommentsDTO(maxContributions);
        /*
        List<Vote> latestVotes = manageVote.latest(maxContributions);
        List<VoteOnProposal> latestVotesOnProposal = manageVote.fetchProposalsVotes(latestVotes);
        */
        List<ContributionDTO> latestContributions = new ArrayList<ContributionDTO>();
        latestContributions.addAll(latestProposals);
        // latestContributions.addAll(latestVotesOnProposal);
        latestContributions.addAll(latestComments);
        if (latestContributions.size() == 0) {
            return latestContributions;
        }
        Collections.sort(latestContributions, new ContributionDateComparator());
        Collections.reverse(latestContributions);
        int subListLastIndex = Math.min(latestContributions.size(), maxContributions);
        LOGGER.info("returning " + subListLastIndex + " contributions");
        return latestContributions.subList(0, subListLastIndex);
    }

    public GetContributionsResult getLastContributions(int maxContrib, int maxProposals) {
        List<ContributionDTO> lastContributions = getLastContributions(maxContrib);
        String contributionsTitle = lastContributions.size() + " last contributions";
        List<ProposalDTO> lastProposals = latestProposalsDTO(maxProposals);
        String proposalsTitle = lastProposals.size() + " last proposals";
        return new GetContributionsResult(
                new ArrayList<ContributionDTO>(lastContributions),
                contributionsTitle,
                new ArrayList<ProposalDTO>(lastProposals),
                proposalsTitle
        );
    }

    public List<ProposalDTO> latestProposalsDTO(int max) {
        ProposalList proposalList = manageProposal.latestAsList(max);
        Map<Key<Citizen>,Citizen> authorsIds = manageCitizen.getCitizensByIds(proposalList.fetchAuthorsIds());
        List<ProposalDTO> proposalDTOs = new ArrayList<ProposalDTO>(proposalList.getCount());
        for(Proposal p: proposalList.getObject()){
            Citizen author = authorsIds.get(p.getAuthor());
            ProposalDTO dto = new ProposalDTO(author, p);
            proposalDTOs.add(dto);
        }
        return proposalDTOs;
    }

    public List<CommentDTO> latestCommentsDTO(int max) {
        CommentList commentList = manageComment.latestAsList(max);
        Map<Key<Citizen>,Citizen> citizensByIds = manageCitizen.getCitizensByIds(commentList.fetchAuthorsIds());
        Map<Key<Contribution>,Contribution> parentContributionsByIds = manageContribution.getContributionsByIds(commentList.fetchParentContributionsIds());
        List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
        for(Comment c: commentList.getObject()){
            Citizen author = citizensByIds.get(c.getAuthor());
            if (c.getParentContribution() == null) {
                throw new RuntimeException("comment without parent ? " + c.toString());
            }
            Contribution parentContribution = parentContributionsByIds.get(c.getParentContribution());
            CommentDTO dto = new CommentDTO(author, c, parentContribution);
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }

    private List<CommentDTO> getProposalCommentsDTO(Long propId) {
        Proposal parentProposal = manageProposal.getById(propId);
        CommentList commentList = manageComment.getProposalCommentsAsList(propId);
        Map<Key<Citizen>,Citizen> citizensByIds = manageCitizen.getCitizensByIds(commentList.fetchAuthorsIds());
        List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>(commentList.getCount());
        for(Comment c: commentList.getObject()){
            Citizen author = citizensByIds.get(c.getAuthor());
            CommentDTO dto = new CommentDTO(author, c, parentProposal);
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }

    public ProposalInformations getProposalInformations(Long propId) {
        Proposal proposal = manageProposal.getById(propId);
        if (proposal == null) {
            throw new RuntimeException("proposal not found");
        }
        Citizen author = null;
        if (proposal.getAuthor() != null) {
            author = manageCitizen.getById(proposal.getAuthorId());
        }
        ProposalDTO proposalRetrieved = new ProposalDTO(author, proposal);
        ProposalVotes proposalVotes = manageVote.getProposalVotes(propId);
        List<CommentDTO> proposalComments = getProposalCommentsDTO(propId);
        return new ProposalInformations(proposalRetrieved, proposalVotes, proposalComments);
    }
}
