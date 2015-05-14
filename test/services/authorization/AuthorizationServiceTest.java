package services.authorization;

import org.junit.BeforeClass;

import static play.test.Helpers.fakeApplication;

public class AuthorizationServiceTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }
//
//    @Test
//    public void getUserIdTest() {
//        running(fakeApplication(), new Runnable() {
//            @Override
//            public void run() {
//
//                String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTQ5MTVkMDUyNDIyOWY4Y2IwNDc1ZTQiLCJ1c2VyUm9sZSI6ImluZGl2aWR1YWwifQ.eDHKz_XsePl7wmqiargSJF7csNGN22xwF84WZvdh1O4";
//                String correctId = "554915d0524229f8cb0475e4";
//
//                try {
//                    String receivedId = services.authorization.AuthorizationService.getUserId(token);
//
//                    assertEquals("Retreiving id from token, should be: " + correctId + " \n retreived: " + receivedId, correctId, receivedId);
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    assertTrue("Error whist parsing token", false);
//
//                } catch (JOSEException e) {
//                    e.printStackTrace();
//                    assertTrue("Error whist verifying token", false);
//                }
//            }
//        });
//    }
//
//    @Test
//    public void verifyTokenTest() throws Exception {
//        running(fakeApplication(), new Runnable() {
//            @Override
//            public void run() {
//                String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTQ5MTVkMDUyNDIyOWY4Y2IwNDc1ZTQiLCJ1c2VyUm9sZSI6ImluZGl2aWR1YWwifQ.eDHKz_XsePl7wmqiargSJF7csNGN22xwF84WZvdh1O4";
//                String correctMail = "krzysiekplachno@gmail.com";
//
//                String retreivedMail = null;
//                try {
//                    retreivedMail = services.authorization.AuthorizationService.verifyToken(token);
//                } catch (ParseException | JOSEException e) {
//                    e.printStackTrace();
//                    assertFalse("Invalid token - error whilst parsing or verifying", true);
//                }
//
//                assertEquals("Retrieved mail for given token is wrong ", correctMail, retreivedMail);
//            }
//        });
//    }
}