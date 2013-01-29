package net.mademocratie.gae.server.services;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Inject;
import junit.framework.Assert;
import net.mademocratie.gae.server.entities.Proposal;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageProposalImplIT {
    private static final Logger logger = Logger.getLogger(ManageProposalImplIT.class.getName());

    @Inject
    private ManageProposalImpl manageProposalImpl;

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddAnonymousProposal() throws Exception {
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        logger.info("addProposal input " + testProposal.toString());
        manageProposalImpl.addProposal(testProposal, null);
        logger.info("addProposal result " + testProposal.toString());
        assertThat(testProposal).as("just created proposal is null")
                    .isNotNull();
        assertThat(testProposal.getId()).as("just created proposal don't have id")
                    .isNotNull();
        Assert.assertEquals("just created proposal title has been updated", PROPOSAL_TITLE, testProposal.getTitle());
        Assert.assertEquals("just created proposal content has been updated",PROPOSAL_CONTENT, testProposal.getContent());
    }
}
