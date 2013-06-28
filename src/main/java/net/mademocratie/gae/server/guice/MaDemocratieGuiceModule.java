package net.mademocratie.gae.server.guice;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyService;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.services.*;
import net.mademocratie.gae.server.services.helper.TemplateHelper;
import net.mademocratie.gae.server.services.impl.*;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MaDemocratieGuiceModule extends ServletModule {

    private final static Logger LOGGER = Logger.getLogger(MaDemocratieGuiceModule.class.getName());

    public void configureServlets() {
        // Persistence registrations
        // Objectify register
        ObjectifyService.register(Contribution.class);
        ObjectifyService.register(Citizen.class);
        ObjectifyService.register(Proposal.class);
        ObjectifyService.register(Vote.class);
        ObjectifyService.register(Comment.class);

        // Bind services
        bind(IManageCitizen.class).to(ManageCitizenImpl.class);
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IManageVote.class).to(ManageVoteImpl.class);
        bind(IManageComment.class).to(ManageCommentImpl.class);
        bind(IManageContributions.class).to(ManageContributionsImpl.class);
        bind(IManageMaDemocratie.class).to(ManageMaDemocratieImpl.class);

        Map<String,String> jerseyParams = new HashMap<String,String>();
        jerseyParams.put("com.sun.jersey.config.property.packages",
                         "net.mademocratie.gae.server.json");

        serve("/json/*").with(GuiceContainer.class, jerseyParams);
        // bind(AboutService.class);

        Configuration configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());

        ServletContext servletContext = getServletContext();
        if (servletContext != null) {
            configuration.setServletContextForTemplateLoading(servletContext, "/");
        } else {
            configuration.setClassForTemplateLoading(MaDemocratieGuiceModule.class, "/");
        }

        bind(Configuration.class).toInstance(configuration);

        TemplateHelper templateHelper = new TemplateHelper();
        bind(TemplateHelper.class).toInstance(templateHelper);

    }
}