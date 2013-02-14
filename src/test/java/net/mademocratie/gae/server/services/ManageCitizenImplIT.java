package net.mademocratie.gae.server.services;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import junit.framework.Assert;
import net.mademocratie.gae.server.entities.Citizen;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.guice.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
public class ManageCitizenImplIT extends BaseIT {
    private static final Logger LOGGER = Logger.getLogger(ManageCitizenImplIT.class.getName());
    @Inject
    private ManageCitizenImpl manageCitizen;

    private static final String TEST_USER_MAIL = "boly38@gmail.com";
    private static final String TEST_USER_PSEUDO = "bobo";
    private final User TEST_USER_GOOGLE = new User(TEST_USER_MAIL, "gmail.com");

    @Before
    public void setUp() {
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

    @After
    public void tearDown() {
        super.tearDown();
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
        Citizen citizen= new Citizen(TEST_USER_PSEUDO, TEST_USER_GOOGLE);
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
        manageCitizen.register("boly38", TEST_USER_GOOGLE);
        this.helper.setEnvEmail(TEST_USER_MAIL);
        this.helper.setEnvIsAdmin(true);
        Citizen authCitizen = manageCitizen.authenticateCitizen(TEST_USER_MAIL, null);
        assertNotNull("could not authenticate a true user ?", authCitizen);
    }


}
