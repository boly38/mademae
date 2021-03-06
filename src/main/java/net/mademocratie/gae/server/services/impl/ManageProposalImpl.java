package net.mademocratie.gae.server.services.impl;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import net.mademocratie.gae.server.entities.ProposalList;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageProposal;

import java.util.*;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageProposalImpl implements IManageProposal {
    private final static Logger LOGGER = Logger.getLogger(ManageProposalImpl.class.getName());

    @Inject
    private IManageCitizen manageCitizen;

    public Proposal addProposal(Proposal inputProposal, Citizen author) {
        if (author != null) {
            inputProposal.setAuthorFromValue(author);
        }
        return addProposal(inputProposal);
    }

    public Proposal addProposal(Proposal inputProposal) {
        inputProposal.setDate(new Date());
        return importProposal(inputProposal);
    }

    public Proposal importProposal(Proposal inputProposal) {
        Key<Proposal> proposalKey = ofy().save().entity(inputProposal).now();
        Proposal addedProposal = getById(proposalKey.getId());
        LOGGER.info("* Proposal ADDED : " + addedProposal);
        return addedProposal;
    }

    public void importProposals(List<Proposal> proposals) {
        for(Proposal p : proposals) {
            importProposal(p);
        }
    }

    public List<Proposal> latest(int max) {
        Query<Proposal> orderedProposals = ofy().load().type(Proposal.class)
                .order("-date");
        if (max > 0) {
            orderedProposals = orderedProposals.limit(max);
        }
        List<Proposal> latestProposals = orderedProposals.list();
        int resultCount = latestProposals != null ? latestProposals.size() : 0;
        LOGGER.info("* latest proposals asked " + (max > 0 ? max : "unlimited") + " result " +resultCount);
        return latestProposals;
    }

    public ProposalList latestAsList(int max) {
        return new ProposalList(latest(max));
    }

    public List<Proposal> latest() {
        return latest(0);
    }

    public void removeAll() {
        int limit = 100;
        List<Proposal> proposals = ofy().load().type(Proposal.class).limit(limit).list();
        if (proposals.size()>0) {
            ofy().delete().entities(proposals).now();
        }
        LOGGER.info(proposals.size() + " proposal(s) removed");
        if (proposals.size() == limit) {
            removeAll();
        }
    }

    public Proposal getById(Long proposalId) {
        return ofy().load().type(Proposal.class).id(proposalId).get();
    }


    public List<ProposalDTO> findByAuthor(Citizen author) throws MaDemocratieException {
        Citizen checkedAuthor = manageCitizen.checkCitizen(author);
        int max = 10;
        List<Proposal> proposals = ofy().load().type(Proposal.class).filter("author", checkedAuthor)
                .order("-date")
                .limit(max)
                .list();
        List<ProposalDTO> proposalsDTO = new ArrayList<ProposalDTO>(proposals.size());
        for(Proposal proposal : proposals) {
            proposalsDTO.add(new ProposalDTO(checkedAuthor, proposal));
        }
        LOGGER.info("* findByCitizenEmail asked " + max + " result " + proposalsDTO.size());
        return proposalsDTO;
    }

    public Map<Long, Proposal> getProposalsByIds(Set<Key<Proposal>> keys) {
        List<Long> proposals = new ArrayList<Long>();
        for (Key<Proposal> key : keys) {
            proposals.add(key.getId());
        }
        Map<Long, Proposal> proposalMap = ofy().load().type(Proposal.class).ids(proposals);
        return proposalMap;
    }

}
