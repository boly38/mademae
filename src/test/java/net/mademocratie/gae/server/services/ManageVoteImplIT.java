package net.mademocratie.gae.server.services;

import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.*;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageVoteImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageVoteImplIT.class.getName());

    @Inject
    private ManageCitizenImpl manageCitizen;
    @Inject
    private ManageProposalImpl manageProposal;
    @Inject
    private ManageVoteImpl manageVote;


    private static final String PROPOSAL_TITLE = "test_proposal_vote";
    private static final String PROPOSAL_CONTENT = "test_proposal_vote";
    private Citizen myAuthorA;
    private Citizen myAuthorB;
    private Proposal testProposalAnon;
    private Proposal testProposalA;
    private Proposal testProposalB;
    private Proposal testProposalA2;

    @Before
    public void init() {
        super.setUp();
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

    private Citizen assertTestCitizenPresence(String email, String pseudo) {
        Citizen cit  = new Citizen(pseudo, "frite365", email, "abc123");
        try {
            manageCitizen.addCitizen(cit);
        } catch (CitizenAlreadyExistsException e) {
            // nothing to do
        }
        return cit;
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
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalAnonymous.getItemIt(), VoteKind.CON);
        // THEN
        assertThat(testVote).isNotNull();
        assertThat(testVote.getItemIt()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void should_vote_pro_on_anonymous_proposal() throws Exception {
        // GIVEN
        Proposal testProposalAnonymous = createAnonymousProposal();
        // WHEN
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalAnonymous.getItemIt(), VoteKind.PRO);
        // THEN
        assertThat(testVote).isNotNull();
        assertThat(testVote.getItemIt()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void should_vote_neutral_on_authored_proposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorB.getEmail(), testProposalA.getItemIt(), VoteKind.NEUTRAL);
        assertThat(testVote).isNotNull();
        assertThat(testVote.getItemIt()).isNotNull();
    }

    @Test
    public void should_add_two_distinct_votes_on_authored_proposal() throws Exception {
        manageVote.vote(myAuthorB.getEmail(), testProposalA.getItemIt(), VoteKind.NEUTRAL);
        Vote testVoteBis = manageVote.vote(myAuthorB.getEmail(), testProposalA.getItemIt(), VoteKind.PRO);
        assertThat(testVoteBis).isNotNull();
        assertThat(testVoteBis.getItemIt()).isNotNull();
    }

    @Test
    public void voting_again_should_not_append_a_now_vote_but_replace_the_existing_one() throws Exception {
        logger.info("voting_again_should_not_append_a_now_vote_but_replace_the_existing_one");
        Long proposalId = testProposalA.getItemIt();
        manageVote.vote(myAuthorB.getEmail(), proposalId, VoteKind.NEUTRAL);
        manageVote.vote(myAuthorB.getEmail(), proposalId, VoteKind.PRO);

        ProposalVotes proposalVotes = manageVote.getProposalVotes(proposalId);
        assertThat(proposalVotes).isNotNull();
        assertThat(proposalVotes.getVotesCount()).isEqualTo(1);
    }

    @Test
    public void should_get_proposal_vote_of_a_citizen() throws Exception {
        // GIVEN
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalA.getItemIt(), VoteKind.PRO);
        assertThat(testVote).isNotNull();
        assertThat(testVote.getItemIt()).isNotNull();
        // WHEN
        List<Vote> retrievedVotes = manageVote.getProposalVotesOfACitizen(myAuthorA.getEmail(), testProposalA.getItemIt());
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
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalA.getItemIt(), VoteKind.PRO);
        Vote testVoteB = manageVote.vote(myAuthorB.getEmail(), testProposalA.getItemIt(), VoteKind.NEUTRAL);
        assertThat(testVote.getItemIt()).isNotNull();
        // WHEN
        ProposalVotes retrievedVotes = manageVote.getProposalVotes(testProposalA.getItemIt());
        //THEN
        assertThat(retrievedVotes)
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(retrievedVotes.getVotes())
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(retrievedVotes.getVotesCount())
                .as("proposal's vote count is incorrect : " + retrievedVotes.getVotesCount())
                .isEqualTo(2);
        assertThat(retrievedVotes.getVotes())
                .as("unable to retrieve given proposal's vote")
                .contains(testVote, testVoteB);
    }

}
