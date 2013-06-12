package net.mademocratie.gae.server.services;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

/**
 * ManageContributionsImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageContributionsImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageContributionsImplIT.class.getName());
    @Inject
    private IManageCitizen manageCitizen;
    @Inject
    private IManageProposal manageProposal;
    @Inject
    private IManageVote manageVote;
    @Inject
    private IManageContributions manageContributions;

    private static final String PROPOSAL_TITLE = "test_contribution";
    private static final String PROPOSAL_CONTENT = "test_contribution_content";
    private Citizen myAuthorA;
    private Citizen myAuthorB;
    private Proposal testProposalAnon;
    private Proposal testProposalA;
    private Proposal testProposalB;
    private Proposal testProposalA2;
    private int contributionsCount;
    private Vote bForANeutral;
    private Vote bForAPro;
    private Contribution contribution_bForAPro;
    private Proposal testProposalAnonB;

    private void cleanData() {
        cleanVotes();
        cleanProposalsAndCitizens();
    }

    @Before
    public void init() throws MaDemocratieException {
        super.setUp();
        cleanData();
        myAuthorA = assertTestCitizenPresence("friteA@jo-la.fr", "jo la frite");
        myAuthorB = assertTestCitizenPresence("froteB@jo-la.fr", "ji la frote");

        testProposalAnon = new Proposal(PROPOSAL_TITLE + "Anon", PROPOSAL_CONTENT);
        testProposalAnonB = new Proposal(PROPOSAL_TITLE + "AnonB", PROPOSAL_CONTENT);
        testProposalA = new Proposal(PROPOSAL_TITLE + "A", PROPOSAL_CONTENT);
        testProposalB = new Proposal(PROPOSAL_TITLE + "B", PROPOSAL_CONTENT);
        testProposalA2 = new Proposal(PROPOSAL_TITLE + "A2", PROPOSAL_CONTENT);

        manageProposal.addProposal(testProposalAnon, null);
        manageProposal.addProposal(testProposalAnonB, null);
        manageProposal.addProposal(testProposalA, myAuthorA);

        bForANeutral = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.NEUTRAL);
        bForAPro = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.PRO);

        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);

        contributionsCount = 6;
    }

    @After
    public void after() {
        cleanData();
    }

    @Test
    public void testGetLastContributions() {
        // GIVEN
        int askedMaxContributions = 50; // 5; because of order
        // WHEN
        List<Contribution> lastContributions = manageContributions.getLastContributions(askedMaxContributions);
        // THEN
        assertThat(lastContributions)
                .isNotNull().isNotEmpty();
        logger.info(lastContributions.toString());
        for (IContribution contribution : lastContributions) {
            logger.info("/CONTRIBUTION/ '" + contribution.getContributionDetails()
                    +"' on '" + contribution.getDate().toString());
            // +"' accessible using " + contribution.getContributionPage().getSimpleName());
        }
        assertThat(lastContributions)
                .hasSize(Math.min(contributionsCount, askedMaxContributions));
        assertThat(lastContributions)
                .contains(testProposalA2);
        assertThat(lastContributions)
                .contains(testProposalB);
        assertThat(lastContributions)
                .contains(testProposalA);
        assertThat(lastContributions)
                .contains(testProposalAnonB);


    }
}
