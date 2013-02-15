package net.mademocratie.gae.server.guice;

import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyService;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.entities.Vote;
import net.mademocratie.gae.server.services.IManageCitizen;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.IManageVote;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;

public class MaDemocratieGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // Persistence registrations
        // Objectify register
        ObjectifyService.register(Citizen.class);
        ObjectifyService.register(Proposal.class);
        ObjectifyService.register(Vote.class);

        // Bind services
        bind(IManageCitizen.class).to(ManageCitizenImpl.class);
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IManageVote.class).to(ManageVoteImpl.class);
    }
}