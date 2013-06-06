package net.mademocratie.gae.server.services.impl;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.cmd.Query;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageProposal;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageProposalImpl implements IManageProposal {
    private final static Logger LOGGER = Logger.getLogger(ManageProposalImpl.class.getName());

    public Proposal addProposal(Proposal inputProposal, Citizen author) {
        if (author != null) {
            inputProposal.setAuthorEmailString(author.getEmail());
            inputProposal.setAuthorPseudo(author.getPseudo());
        }
        return addProposal(inputProposal);
    }

    public Proposal addProposal(Proposal inputProposal) {
        inputProposal.setDate(new Date());
        ofy().save().entity(inputProposal).now();
        LOGGER.info("* Proposal ADDED : " + inputProposal);
        return inputProposal;
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
    public List<Proposal> latest() {
        return latest(0);
    }

    public void removeAll() {
        ofy().delete().type(Proposal.class);
    }

    public Proposal getById(Long proposalId) {
        return ofy().load().type(Proposal.class).id(proposalId).get();
    }


    public List<Proposal> findByCitizenEmail(String email) {
        int max = 10;
        List<Proposal> proposals = ofy().load().type(Proposal.class).filter("authorEmail", email)
                .order("-date")
                .limit(max)
                .list();
        LOGGER.info("* findByCitizenEmail asked " + max + " result " + proposals.size());
        return proposals;
    }

}
