package net.mademocratie.gae.server.json;

import com.google.inject.Inject;
import net.mademocratie.gae.server.services.helper.JsonHelper;
import net.mademocratie.gae.server.services.impl.AbstractIT;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.entities.v1.*;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class OpsServiceIT extends AbstractIT {
    private static final Logger logger = Logger.getLogger(OpsServiceIT.class.getName());

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";
    private Proposal testProposalA;

    @Inject
    protected IOpsService opsService;


    @Before
    public void setUp() throws MaDemocratieException {
        super.setUp();
        cleanAll();
    }

    @After
    public void after() {
        cleanAll();
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
    public void should_provide_exportv1() throws MaDemocratieException {
        logger.info("should_provide_exportv1_admin_feature");
        // GIVEN
        int createdContributionCount = initContributions();
        // WHEN
        Response response = opsService.dbExport();
        // THEN
        assertThat(response).isNotNull();

        // Response exportContent = Response.ok(databaseContentV1).type(MediaType.APPLICATION_JSON_TYPE).build();
        assertThat(response.getEntity()).isNotNull();
        logger.info("db export : " + response.getEntity());
    }

    @Test
    public void should_export_import_citizen() throws MaDemocratieException {
        // GIVEN
        Citizen myAuthor = assertTestCitizenPresence("froteC@jo-la.fr", "François avec accentué");

        Response response = opsService.dbExport();
        String exportContent = (String) response.getEntity();
        logger.info("export content:" + exportContent);
        cleanAll();

        // WHEN
        DbImport dbImport = new DbImport();
        dbImport.setImportContent(exportContent);
        opsService.dbImport(dbImport);

        // THEN
        Citizen reImportedCitizen = manageCitizen.getById(myAuthor.getId());
        assertThat(reImportedCitizen).isEqualTo(myAuthor);
    }

    @Test
    public void should_export_import_proposal() throws MaDemocratieException {
        // GIVEN
        Proposal myProposal = new Proposal("ooOtitle","oOoContent");
        myProposal = manageProposal.addProposal(myProposal);

        Response response = opsService.dbExport();
        String exportContent = (String) response.getEntity();
        logger.info("export content:" + exportContent);
        cleanAll();

        // WHEN
        DbImport dbImport = new DbImport();
        dbImport.setImportContent(exportContent);
        opsService.dbImport(dbImport);

        // THEN
        Proposal reImportedProposal = manageProposal.getById(myProposal.getContributionId());
        assertThat(reImportedProposal).isEqualTo(myProposal);
    }

    @Test
    public void should_export_import_vote() throws MaDemocratieException, IOException {
        // GIVEN
        Citizen myAuthor = assertTestCitizenPresence("froteC@jo-la.fr", "François avec accentué");

        Proposal myProposal = new Proposal("ooOtitle","oOoContent");
        myProposal.setAuthorFromValue(myAuthor);

        myProposal = manageProposal.addProposal(myProposal);

        Vote myVote = manageVote.vote(myAuthor, myProposal, VoteKind.CON);

        Response response = opsService.dbExport();
        String exportContent = (String) response.getEntity();
        logger.info("export content:" + exportContent);
        cleanAll();

        // WHEN
        DbImport dbImport = new DbImport();
        dbImport.setImportContent(exportContent);
        opsService.dbImport(dbImport);

        // THEN
        Proposal reImportedProposal = manageProposal.getById(myProposal.getContributionId());
        assertThat(reImportedProposal).isEqualTo(myProposal);

        Vote reImportedVote = manageVote.getById(myVote.getContributionId());
        assertThat(reImportedVote).isEqualTo(myVote);
    }
}
