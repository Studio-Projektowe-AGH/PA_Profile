package services.data;

import com.mongodb.WriteResult;
import models.BusinessUserProfile;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;


public class DBProfileServiceTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }


    @Test
    public void addReadRemoveProfileTest() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String email = "kokomarokoNieMaTakiegoMailaWBazie@gmail.com";
                String testName = "TEST_NAME";
                BusinessUserProfile newProfile = new BusinessUserProfile(email, null);
                newProfile.setName(testName);
                DBProfileService DBProfileService = DBServicesProvider.getDbProfileService();
                DBProfileService.save(newProfile);

                BusinessUserProfile receivedProfile = DBProfileService.findOneByEmail(email);
                assertNotNull("user of given email not found: "+ email, receivedProfile);

                assertEquals("Not adding/read profile to/from db failed", testName, receivedProfile.getName());

                WriteResult writeResult = DBProfileService.deleteOne(email);
                assertEquals("Deleting object failed", 1, writeResult.getN());
            }
        });
    }


}