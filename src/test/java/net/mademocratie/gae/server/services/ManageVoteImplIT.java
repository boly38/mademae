package net.mademocratie.gae.server.services;

import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.impl.AbstractIT;
import net.mademocratie.gae.server.entities.VoteList;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageVoteImplIT extends AbstractIT {
    private static final Logger logger = Logger.getLogger(ManageVoteImplIT.class.getName());


    private static final String PROPOSAL_TITLE = "test_proposal_vote";
    private static final String PROPOSAL_CONTENT = "test_proposal_vote";
    private Citizen myAuthorA;
    private Citizen myAuthorB;
    private Proposal testProposalAnon;
    private Proposal testProposalA;
    private Proposal testProposalB;
    private Proposal testProposalA2;

    @Before
    public void init() throws MaDemocratieException {
        super.setUp();
        cleanTestData();
        myAuthorA = assertTestCitizenPresence("friteA@jo-la.fr", "jo la frite");
        myAuthorB = assertTestCitizenPresence("froteB@jo-la.fr", "ji la frote");

        testProposalAnon = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalA = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalB = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalA2 = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        createAnonymousProposal();
        manageProposal.addProposal(testProposalA, myAuthorA);
        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);
    }

    @After
    public void after() {
        cleanTestData();
    }

    private void cleanTestData() {
        manageProposal.removeAll();
        manageCitizen.removeAll();
    }

    private Proposal createAnonymousProposal() {
        return manageProposal.addProposal(testProposalAnon, null);
    }

    /**
     * @throws Exception
     **/
    @Test
    public void should_vote_con_on_anonymous_proposal() throws Exception {
        // GIVEN
        Proposal testProposalAnonymous = createAnonymousProposal();
        // WHEN
        Vote testVote = manageVote.vote(myAuthorA, testProposalAnonymous, VoteKind.CON);
        // THEN
        assertThat(testVote).isNotNull();
        assertThat(testVote.getContributionId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void should_vote_pro_on_anonymous_proposal() throws Exception {
        // GIVEN
        Proposal testProposalAnonymous = createAnonymousProposal();
        // WHEN
        Vote testVote = manageVote.vote(myAuthorA, testProposalAnonymous, VoteKind.PRO);
        // THEN
        assertThat(testVote).isNotNull();
        assertThat(testVote.getContributionId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void should_vote_neutral_on_authored_proposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorB, testProposalA, VoteKind.NEUTRAL);
        assertThat(testVote).isNotNull();
        assertThat(testVote.getContributionId()).isNotNull();
    }

    @Test
    public void should_add_two_distinct_votes_on_authored_proposal() throws Exception {
        manageVote.vote(myAuthorB, testProposalA.getContributionId(), VoteKind.NEUTRAL);
        Vote testVoteBis = manageVote.vote(myAuthorB, testProposalA, VoteKind.PRO);
        assertThat(testVoteBis).isNotNull();
        assertThat(testVoteBis.getContributionId()).isNotNull();
    }

    @Test
    public void voting_again_should_not_append_a_now_vote_but_replace_the_existing_one() throws Exception {
        logger.info("voting_again_should_not_append_a_now_vote_but_replace_the_existing_one");
        Long proposalId = testProposalA.getContributionId();
        manageVote.vote(myAuthorB, proposalId, VoteKind.NEUTRAL);
        manageVote.vote(myAuthorB, proposalId, VoteKind.PRO);

        VoteList votes = manageVote.getProposalVotes(proposalId);
        assertThat(votes).isNotNull();
        assertThat(votes.getCount()).isEqualTo(1);
    }

    @Test
    public void should_get_proposal_vote_of_a_citizen() throws Exception {
        // GIVEN
        Vote testVote = manageVote.vote(myAuthorA, testProposalA, VoteKind.PRO);
        assertThat(testVote).isNotNull();
        assertThat(testVote.getContributionId()).isNotNull();
        // WHEN
        List<Vote> retrievedVotes = manageVote.getProposalVotesOfACitizen(myAuthorA, testProposalA.getContributionId());
        // THEN
        assertThat(retrievedVotes)
                .as("unable to retrieve a vote of a citizen")
                .isNotNull();
        assertThat(retrievedVotes)
                .as("unable to retrieve the vote of the citizen")
                .contains(testVote);
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testGetProposalVotes() throws Exception {
        // GIVEN
        Vote testVote = manageVote.vote(myAuthorA, testProposalA, VoteKind.PRO);
        Vote testVoteB = manageVote.vote(myAuthorB, testProposalA, VoteKind.NEUTRAL);
        assertThat(testVote.getContributionId()).isNotNull();
        // WHEN
        VoteList proposalVotes = manageVote.getProposalVotes(testProposalA.getContributionId());
        //THEN
        assertThat(proposalVotes)
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(proposalVotes.getObject())
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(proposalVotes.getCount())
                .as("proposal's vote count is incorrect : " + proposalVotes.getCount())
                .isEqualTo(2);
        assertThat(proposalVotes.getObject())
                .as("unable to retrieve given proposal's vote")
                .contains(testVote, testVoteB);
    }

}
