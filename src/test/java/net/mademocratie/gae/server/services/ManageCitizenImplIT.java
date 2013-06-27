package net.mademocratie.gae.server.services;

import junit.framework.Assert;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.services.impl.AbstractIT;
import net.mademocratie.gae.server.entities.dto.ContributionDTO;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageCitizenImplIT extends AbstractIT {
    private static final Logger LOGGER = Logger.getLogger(ManageCitizenImplIT.class.getName());

    private static final String TEST_USER_MAIL = "boly38@gmail.com";
    private static final String TEST_USER_PSEUDO = "bobo";

    @Before
    public void setUp() throws MaDemocratieException {
        super.setUp();
        cleanTestData();
    }

    @After
    public void after() {
        cleanTestData();
    }

    private void cleanTestData() {
        Citizen testUser = manageCitizen.findCitizenByEmail(TEST_USER_MAIL);
        if (testUser != null) {
            manageCitizen.delete(testUser);
        }
        LOGGER.info("cleanTestData done.");
    }


    @Test
    public void testSuggestCitizen() {
        Citizen suggestCitizen = manageCitizen.suggestCitizen();
        LOGGER.info("suggestCitizen result " + suggestCitizen.toString());
        Assert.assertNotNull("suggestCitizen is null", suggestCitizen);
        Assert.assertNotNull("suggestCitizen don't have pseudo", suggestCitizen.getPseudo());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddCitizen() throws Exception {
        Citizen citizen= new Citizen(TEST_USER_PSEUDO,TEST_USER_MAIL);
        LOGGER.info("addCitizen input " + citizen.toString());
        manageCitizen.addCitizen(citizen);
        assertThat(citizen).as("just created citizen is null")
                .isNotNull();
        assertThat(citizen.getId()).as("just created citizen don't have id")
                .isNotNull();
    }
    @Test
    public void testAuthenticateFakeCitizen() {
        assertNull("authenticate a fake user ?", manageCitizen.authenticateCitizen("test@yoyo.fr", "pass"));
    }

    @Test
    public void testAuthenticateAndSignInGoogleCitizen() throws RegisterFailedException {
        manageCitizen.registerGoogleUser("boly38", TEST_USER_MAIL, false);
        this.helper.setEnvEmail(TEST_USER_MAIL);
        this.helper.setEnvIsAdmin(true);
        Citizen authCitizen = manageCitizen.authenticateCitizen(TEST_USER_MAIL, null);
        assertNotNull("could not authenticate a true user ?", authCitizen);
    }

    @Test
    public void testSendMail() throws MaDemocratieException {
        // GIVEN
        ArrayList<ContributionDTO> contributions = new ArrayList<ContributionDTO>();
        ProposalDTO proposalDTO = new ProposalDTO(null, new Proposal("my Test title", "my test content"));
        contributions.add(proposalDTO);

        // WHEN
        manageCitizen.notifyAdminReport(contributions);
    }
}
