package net.mademocratie.gae.server.guice;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyService;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.services.*;
import net.mademocratie.gae.server.services.impl.*;

import java.util.HashMap;
import java.util.Map;

public class MaDemocratieGuiceModule extends ServletModule {

    public void configureServlets() {
        // Persistence registrations
        // Objectify register
        ObjectifyService.register(Contribution.class);
        ObjectifyService.register(Citizen.class);
        ObjectifyService.register(Proposal.class);
        ObjectifyService.register(Vote.class);
        ObjectifyService.register(ProposalVotes.class);
        ObjectifyService.register(CommentContribution.class);

        // Bind services
        bind(IManageCitizen.class).to(ManageCitizenImpl.class);
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IManageVote.class).to(ManageVoteImpl.class);
        bind(IManageContributions.class).to(ManageContributionsImpl.class);
        bind(IManageComment.class).to(ManageCommentImpl.class);

        Map<String,String> jerseyParams = new HashMap<String,String>();
        jerseyParams.put("com.sun.jersey.config.property.packages",
                         "net.mademocratie.gae.server.json");

        serve("/json/*").with(GuiceContainer.class, jerseyParams);
        // bind(AboutService.class);
    }
}