package net.mademocratie.gae.server.services;

import com.google.inject.Inject;
import junit.framework.Assert;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.entities.dto.ContributionDTO;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

/**
 * ManageCitizenImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageMaDemocratieImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageMaDemocratieImplIT.class.getName());
    @Inject
    private IManageMaDemocratie  manageMD;

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";

    @Before
    public void setUp() {
        super.setUp();
        cleanProposalsAndCitizensAndComments();
    }

    @After
    public void after() {
        cleanProposalsAndCitizensAndComments();
    }


    @Test
    public void should_provide_user_profile_informations() throws Exception {
        // GIVEN
        Citizen myAuthor = new Citizen("jo la frite", "frite365", "frite@jo-la.fr", "abc123");
        manageCitizen.addCitizen(myAuthor);
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        logger.info("addProposal input " + testProposal.toString());
        manageProposal.addProposal(testProposal, myAuthor);

        // WHEN
        ProfileInformations profileInformations = manageMD.getProfileInformations(myAuthor);
        Assert.assertNotNull("user profile should be returned", profileInformations);
        JSONObject jsonProfileInformations = new JSONObject(profileInformations);
        logger.info("getProfileInformations result " + jsonProfileInformations.toString());
    }


    public int initContributions() throws MaDemocratieException {
        Citizen myAuthorA = assertTestCitizenPresence("friteA@jo-la.fr", "jo la frite");
        Citizen myAuthorB = assertTestCitizenPresence("froteB@jo-la.fr", "ji la frote");

        Proposal testProposalAnon = new Proposal(PROPOSAL_TITLE + "Anon", PROPOSAL_CONTENT);
        Proposal testProposalAnonB = new Proposal(PROPOSAL_TITLE + "AnonB", PROPOSAL_CONTENT);
        Proposal testProposalA = new Proposal(PROPOSAL_TITLE + "A", PROPOSAL_CONTENT);
        Proposal testProposalB = new Proposal(PROPOSAL_TITLE + "B", PROPOSAL_CONTENT);
        Proposal testProposalA2 = new Proposal(PROPOSAL_TITLE + "A2", PROPOSAL_CONTENT);

        manageProposal.addProposal(testProposalAnon, null);
        manageProposal.addProposal(testProposalAnonB, null);
        manageProposal.addProposal(testProposalA, myAuthorA);

        Vote bForANeutral = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.NEUTRAL);
        Vote bForAPro = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.PRO);

        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);

        Comment authorAComment = new Comment(myAuthorA, "oodod d", testProposalB);
        manageComment.comment(myAuthorA, authorAComment);

        int contributionsCount = 6;
        return contributionsCount;
    }

    @Test
    public void testGetLastContributions() throws MaDemocratieException {
        // GIVEN
        int createdContributionCount = initContributions();
        int askedMaxContributions = 50; // 5; because of order
        // WHEN
        GetContributionsResult lastContributions = manageMD.getLastContributions(askedMaxContributions, 10);
        // THEN
        assertThat(lastContributions)
                .isNotNull();
        ArrayList<ContributionDTO> contributions = lastContributions.getContributions();
        assertThat(contributions).isNotEmpty();
        logger.info(contributions.toString());
        for (IContribution contribution : contributions) {
            logger.info("/CONTRIBUTION/ '" + contribution.getContributionDetails()
                    +"' on '" + contribution.getDateFormat());
            // +"' accessible using " + contribution.getContributionPage().getSimpleName());
        }
        assertThat(contributions)
                .hasSize(Math.min(createdContributionCount, askedMaxContributions));
    }
}
