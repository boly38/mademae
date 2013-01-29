package net.mademocratie.gae.server.services.impl;

import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageProposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ManageProposalImpl implements IManageProposal {
    private final static Logger LOGGER = Logger.getLogger(ManageProposalImpl.class.getName());

    public void addProposal(Proposal inputProposal, String authorMail) {
        inputProposal.setAuthorEmail(authorMail);
        inputProposal.setDate(new Date());
        ofy().save().entity(inputProposal).now();
        LOGGER.info("* Proposal ADDED : " + inputProposal);
    }

    public List<Proposal> latest(int max) {
        return new ArrayList<Proposal>();
    }
}
