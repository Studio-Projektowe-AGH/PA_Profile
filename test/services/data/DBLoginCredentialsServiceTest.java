package services.data;

import models.LoginCredentials;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class DBLoginCredentialsServiceTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }

    @Test
    public void getUserByIdTest() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String correctId = "554915d0524229f8cb0475e4";
                String correctMail = "krzysiekplachno@gmail.com";
                DBLoginCredentialsService dbLoginCredentialsService = DBServicesProvider.getDbLoginCredentialsService();
                LoginCredentials user = dbLoginCredentialsService.findOneById(correctId);
                assertNotNull("Did not returned any user by id", user);
                assertEquals("Received user of wrong mail", correctMail, user.getEmail());
            }
        });

    }
}