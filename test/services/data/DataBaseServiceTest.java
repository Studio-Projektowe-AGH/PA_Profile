package services.data;

import com.mongodb.WriteResult;
import models.BusinessUserProfile;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;


public class DataBaseServiceTest {

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
                String email = "krzysiekplachno@gmail.com";
                String testName = "TEST_NAME";
                BusinessUserProfile newProfile = new BusinessUserProfile(email);
                newProfile.setName(testName);
                DataBaseService dataBaseService = DataBaseServiceProvider.getDataBaseService();
                dataBaseService.save(newProfile);

                BusinessUserProfile receivedProfile = dataBaseService.findOneByEmail(email);

                assertTrue("Not adding/read profile to/from db failed", receivedProfile.getName().equals(testName));

                WriteResult writeResult = dataBaseService.deleteOne(email);
                assertEquals("Deleting object failed", 1, writeResult.getN());
            }
        });
    }


}