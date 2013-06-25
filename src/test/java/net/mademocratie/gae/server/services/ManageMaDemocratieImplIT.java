package net.mademocratie.gae.server.services;

import com.google.inject.Inject;
import junit.framework.Assert;
import net.mademocratie.gae.server.services.impl.AbstractIT;
import net.mademocratie.gae.server.domain.GetContributionsResult;
import net.mademocratie.gae.server.domain.ProfileInformations;
import net.mademocratie.gae.server.domain.ProposalInformations;
import net.mademocratie.gae.server.entities.IContribution;
import net.mademocratie.gae.server.entities.dto.ContributionDTO;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageMaDemocratieImplIT extends AbstractIT {
    private static final Logger logger = Logger.getLogger(ManageMaDemocratieImplIT.class.getName());
    @Inject
    private IManageMaDemocratie  manageMD;

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";
    private Proposal testProposalA;


    @Before
    public void setUp() throws MaDemocratieException {
        super.setUp();
        cleanAll();
    }

    @After
    public void after() {
        cleanAll();
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
        testProposalA = new Proposal(PROPOSAL_TITLE + "A", PROPOSAL_CONTENT);
        Proposal testProposalB = new Proposal(PROPOSAL_TITLE + "B", PROPOSAL_CONTENT);
        Proposal testProposalA2 = new Proposal(PROPOSAL_TITLE + "A2", PROPOSAL_CONTENT);

        testProposalAnon = manageProposal.addProposal(testProposalAnon, null);
        testProposalAnonB = manageProposal.addProposal(testProposalAnonB, null);
        testProposalA = manageProposal.addProposal(testProposalA, myAuthorA);

        Vote bForANeutral = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.NEUTRAL);
        Vote bForAPro = manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.PRO);

        testProposalB = manageProposal.addProposal(testProposalB, myAuthorB);
        testProposalA2 = manageProposal.addProposal(testProposalA2, myAuthorA);

        Comment authorAComment = new Comment(myAuthorA, "oodod d", testProposalB);
        authorAComment = manageComment.comment(myAuthorA, authorAComment);

        int contributionsCount = 7;
        return contributionsCount;
    }

    @Test
    public void should_provide_last_contributions() throws MaDemocratieException {
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
            if (contribution.getContributionType().equals("COMMENT")) {
                assertThat(contribution.getContributionId()).isNotNull();
            }
        }
        assertThat(contributions)
                .hasSize(Math.min(createdContributionCount, askedMaxContributions));
    }


    @Test
    public void should_provide_proposal_informations() throws MaDemocratieException {
        // GIVEN
        int createdContributionCount = initContributions();
        // WHEN
        ProposalInformations proposalInformations = manageMD.getProposalInformations(testProposalA.getContributionId());
        // THEN
        assertThat(proposalInformations)
                .isNotNull();
        JSONObject jsonProposalInformations = new JSONObject(proposalInformations);
        logger.info(jsonProposalInformations.toString());
    }

    @Test
    @Ignore("TO FIX :(")
    public void should_notif_admin_report() throws MaDemocratieException {
        // GIVEN
        int createdContributionCount = initContributions();
        // WHEN
        manageMD.notifyAdminReport();
    }

}
