package net.mademocratie.gae.server.services;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;
import org.junit.Before;

public abstract class BaseIT {

    @Inject
    protected ManageCitizenImpl manageCitizen;

    @Inject
    protected ManageProposalImpl manageProposal;

    @Inject
    protected ManageVoteImpl manageVote;

    @Inject
    protected IManageComment manageComment;
    /*
     * needed to inject UserServiceFactory.getUserService();
     * http://man.lesca.me/local/gae/appengine/docs/java/tools/localunittesting.html#Writing_Authentication_Tests
     */
    protected final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalUserServiceTestConfig())
                    .setEnvIsAdmin(true)
                    .setEnvIsLoggedIn(true)
                    .setEnvEmail("toto@yoyo.fr")
                    .setEnvAuthDomain("yoyo.fr");

    @Before
    public void setUp() {
        // google service
        helper.setUp();
    }

    protected void cleanProposalsAndCitizens() {
        manageProposal.removeAll();
        manageCitizen.removeAll();
    }
    protected void cleanAll() {
        manageVote.removeAll();
        manageComment.removeAll();
        cleanProposalsAndCitizens();
    }

    protected Citizen assertTestCitizenPresence(String email, String pseudo) {
        Citizen cit  = new Citizen(pseudo, "frite365", email, "abc123");
        try {
            manageCitizen.addCitizen(cit);
        } catch (CitizenAlreadyExistsException e) {
            // nothing to do
        }
        return cit;
    }
}
