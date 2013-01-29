package net.mademocratie.gae.server.guice;

import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyService;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.services.IManageProposal;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;

public class MaDemocratieGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // Persistence registrations
        // Objectify register
        ObjectifyService.register(Proposal.class);

        // Bind services
        bind(IManageProposal.class).to(ManageProposalImpl.class);
    }
}