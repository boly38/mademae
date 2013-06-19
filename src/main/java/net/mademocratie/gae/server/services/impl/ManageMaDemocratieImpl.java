package net.mademocratie.gae.server.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.domain.ProposalInformations;
import net.mademocratie.gae.server.entities.CommentList;
import net.mademocratie.gae.server.entities.ProposalList;
import net.mademocratie.gae.server.entities.VoteList;
import net.mademocratie.gae.server.entities.dto.*;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
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
        List<VoteDTO> latestVotes = lastestVotesDTO(maxContributions);
        List<ContributionDTO> latestContributions = new ArrayList<ContributionDTO>();
        latestContributions.addAll(latestProposals);
        latestContributions.addAll(latestVotes);
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
        Map<Long,Contribution> parentContributionsByIds = manageContribution.getContributionsByIds(commentList.fetchParentContributionsIds());
        List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
        for(Comment c: commentList.getObject()){
            Citizen author = citizensByIds.get(c.getAuthor());
            Contribution parentContribution = parentContributionsByIds.get(c.getParentContributionId());
            if (parentContribution == null) {
                throw new RuntimeException("comment without parent ? " + c.toString());
            }
            CommentDTO dto = new CommentDTO(author, c, parentContribution);
            commentDTOs.add(dto);
        }
        return commentDTOs;
    }


    private List<VoteDTO> lastestVotesDTO(int max) {
        VoteList voteList = manageVote.latestAsList(max);
        LOGGER.info("lastestVotesDTO max="+ max + " voteList:" + voteList.getObject().toString());
        Map<Key<Citizen>,Citizen> citizensByIds = manageCitizen.getCitizensByIds(voteList.fetchAuthorsIds());
        Set<Key<Proposal>> parentProposalsKeys = voteList.fetchProposalsIds();
        LOGGER.info("fetched proposals ids "+ parentProposalsKeys);
        Map<Long, Proposal> parentProposalsByIds = manageProposal.getProposalsByIds(parentProposalsKeys);
        LOGGER.info("fetched proposals "+ parentProposalsByIds);
        List<VoteDTO> voteDTOs = new ArrayList<VoteDTO>();
        for(Vote v: voteList.getObject()){
            LOGGER.info("vote " + v.toString());
            Citizen author = citizensByIds.get(v.getAuthor());
            Proposal parentProposal = parentProposalsByIds.get(v.getProposal().getId());
            if (parentProposal == null) {
                throw new RuntimeException("vote without proposal? " + v.toString());
            }
            VoteDTO dto = new VoteDTO(author, v, new ProposalDTO(null, parentProposal));
            voteDTOs.add(dto);
        }
        return voteDTOs;
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
        VoteList proposalVotes = manageVote.getProposalVotes(propId);
        ProposalVotesDTO proposalVotesDTO = proposalVotes.asProposalVotes(proposalRetrieved);
        List<CommentDTO> proposalComments = getProposalCommentsDTO(propId);
        return new ProposalInformations(proposalRetrieved, proposalVotesDTO, proposalComments);
    }

    public boolean isUserAdmin() {
        return manageCitizen.isGoogleUserAdmin();
    }

    public void notifyAdminReport() throws MaDemocratieException {
        manageCitizen.notifyAdminReport();
    }

    public Citizen getAuthenticatedUser(String authToken) {
        return manageCitizen.getAuthenticatedUser(authToken);
    }

    public DatabaseContentV1 dbExportV1() {
        List<Citizen> citizens = manageCitizen.latest();
        List<Proposal> proposals = manageProposal.latest();
        List<Vote> votes = manageVote.latest();
        List<Comment> comments = manageComment.latest();
        return new DatabaseContentV1(
                new ArrayList<Citizen>(citizens),
                new ArrayList<Proposal>(proposals),
                new ArrayList<Vote>(votes),
                new ArrayList<Comment>(comments)
        );
    }

    public void dbImportV1(DbImport dbImport) {
        if (dbImport== null) return;
        JSONObject jsonDbImport = new JSONObject(dbImport);
        LOGGER.info("dbImport POST received : " + jsonDbImport.toString());
        ObjectMapper mapper = new ObjectMapper();
        DatabaseContentV1 databaseImportV1;
        try {
            databaseImportV1= mapper.readValue(dbImport.getImportContent(), DatabaseContentV1.class);
            manageCitizen.importCitizens(databaseImportV1.getCitizens());
            manageProposal.importProposals(databaseImportV1.getProposals());
            manageComment.importComments(databaseImportV1.getComments());
            manageVote.importVotes(databaseImportV1.getVotes());
            LOGGER.info("dbImport stats : " + databaseImportV1.toString());
        } catch (IOException e) {
            LOGGER.warning("dbImport failed (io exception) : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("manageCitizen", manageCitizen)
                .add("manageProposal", manageProposal)
                .add("manageVote", manageVote)
                .add("manageComment", manageComment)
                .add("manageContribution", manageContribution)
                .toString();
    }
}
