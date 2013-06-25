package net.mademocratie.gae.server.services.impl;

import com.google.appengine.tools.development.testing.*;
import com.google.inject.Inject;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.services.IManageComment;
import net.mademocratie.gae.server.services.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.services.impl.ManageProposalImpl;
import net.mademocratie.gae.server.services.impl.ManageVoteImpl;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

public class AbstractIT {

    @Inject @Spy
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
            new LocalServiceTestHelper(
                        new LocalUserServiceTestConfig(),
                        new LocalURLFetchServiceTestConfig(),
                        new LocalMailServiceTestConfig())
                    .setEnvIsAdmin(true)
                    .setEnvIsLoggedIn(true)
                    .setEnvEmail("boly38@mademocratie.net")
                    .setEnvAuthDomain("mademocratie.net")
                    ;


    @Before
    public void setUp() throws MaDemocratieException {
        MockitoAnnotations.initMocks(this);
        // google service
        helper.setUp();
        doNothing().when(manageCitizen).sendMail(anyString(), anyString(), anyString(), anyString());
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
