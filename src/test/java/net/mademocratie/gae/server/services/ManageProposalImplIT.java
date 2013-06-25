package net.mademocratie.gae.server.services;

import junit.framework.Assert;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.impl.AbstractIT;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Proposal;
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
import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageProposalImplIT extends AbstractIT {
    private static final Logger logger = Logger.getLogger(ManageProposalImplIT.class.getName());

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";

    @Before
    public void setUp() throws MaDemocratieException {
        super.setUp();
        cleanProposalsAndCitizens();
    }
    @After
    public void after() {
        cleanProposalsAndCitizens();
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
        assertThat(testProposal.getContributionId()).as("just created proposal don't have id")
                    .isNotNull();
        Assert.assertEquals("just created proposal title has been updated", PROPOSAL_TITLE, testProposal.getTitle());
        Assert.assertEquals("just created proposal content has been updated",PROPOSAL_CONTENT, testProposal.getContent());
    }

    @Test
    public void testAddProposalWithAuthor() throws Exception {
        Citizen myAuthor = new Citizen("jo la frite", "frite365", "frite@jo-la.fr", "abc123");
        manageCitizen.addCitizen(myAuthor);
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        logger.info("addProposal input " + testProposal.toString());
        manageProposal.addProposal(testProposal, myAuthor);
        logger.info("addProposal result " + testProposal.toString());
        Assert.assertNotNull("just created proposal is null", testProposal);
        Assert.assertNotNull("just created proposal don't have id", testProposal.getContributionId());
        Assert.assertEquals("just created proposal title has been updated", PROPOSAL_TITLE, testProposal.getTitle());
        Assert.assertEquals("just created proposal content has been updated",PROPOSAL_CONTENT, testProposal.getContent());
    }

    @Test
    public void testAddProposalsWithAuthors() throws Exception {
        Citizen myAuthorA = new Citizen("jo la frite", "frite365", "frite@jo-la.fr", "abc123");
        Citizen myAuthorB = new Citizen("ji la frote", "frite421", "frote@jo-la.fr", "abc123");
        manageCitizen.addCitizen(myAuthorA);
        manageCitizen.addCitizen(myAuthorB);
        Proposal testProposalAnon = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalA = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalB = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalA2 = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal addedTestProposalAnon = manageProposal.addProposal(testProposalAnon);
        manageProposal.addProposal(testProposalA, myAuthorA);
        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);
        logger.info("last addProposal result " + testProposalA2.toString());
        assertNotNull("last created proposal is null", testProposalA2);
        assertNotNull("last created proposal don't have id", testProposalA2.getContributionId());
        assertEquals("last created proposal title has been updated", PROPOSAL_TITLE, testProposalA2.getTitle());
        assertEquals("last created proposal content has been updated", PROPOSAL_CONTENT, testProposalA2.getContent());
        assertNotNull(manageProposal.getById(addedTestProposalAnon.getContributionId()));
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

    public void setManageProposal(ManageProposalImpl manageProposal) {
        this.manageProposal = manageProposal;
    }
}
