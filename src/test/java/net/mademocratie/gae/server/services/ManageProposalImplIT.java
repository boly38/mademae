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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageProposalImplIT {
    private static final Logger logger = Logger.getLogger(ManageProposalImplIT.class.getName());

    @Inject
    private ManageProposalImpl manageProposal;

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
        manageProposal.addProposal(testProposal, null);
        logger.info("addProposal result " + testProposal.toString());
        assertThat(testProposal).as("just created proposal is null")
                    .isNotNull();
        assertThat(testProposal.getId()).as("just created proposal don't have id")
                    .isNotNull();
        Assert.assertEquals("just created proposal title has been updated", PROPOSAL_TITLE, testProposal.getTitle());
        Assert.assertEquals("just created proposal content has been updated",PROPOSAL_CONTENT, testProposal.getContent());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testLatest() throws Exception {
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        manageProposal.addProposal(testProposal, null);
        List<Proposal> latestProposals = manageProposal.latest(10);
        Assert.assertNotNull("latest proposals List is null", latestProposals);
        int latestProposalsSize = latestProposals.size();
        String latestProposalsLogString = Arrays.toString(latestProposals.toArray());
        Assert.assertFalse("latest proposals List size " + latestProposalsSize + " < 1 : \n\t"
                + latestProposalsLogString, latestProposalsSize < 1);
        Assert.assertFalse("latest proposals List size " + latestProposalsSize + "> 10 : \n\t"
                + latestProposalsLogString, latestProposalsSize > 10);
    }
}
