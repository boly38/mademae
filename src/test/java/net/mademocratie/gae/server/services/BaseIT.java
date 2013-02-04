package net.mademocratie.gae.server.services;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import org.junit.After;
import org.junit.Before;

public abstract class BaseIT {

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

    @After
    public void tearDown() {
        helper.tearDown();
    }

}
