package services.restMethods;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Created by Kris on 2015-05-08.
 */
public class UpdateBusinessProfileTest {

    static play.test.FakeApplication application;
    //static String correctUserId = "5554f84952423afe1e6ebdcf";
    static String correctUserId = "5554f8915242c8e312e87226";


    @BeforeClass
    public static void setUpClass(){
        application = fakeApplication();
    }


    @Test
    public void acceptingCorrectFieldsInJsonTest(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                ObjectNode body = Json.newObject();
                body.put("name", "Tawo Club");

                FakeRequest request = fakeRequest("PUT", "/profiles/business/"+correctUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When correct field in update response: "+ contentAsString(result),
                        200, status(result));

            }
        });

    }

    @Test
    public void denyingWrongFieldsInJsonTest(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                ObjectNode body = Json.newObject();
                body.put("thereIsNoSuchFieldInClass", "Tawo Club");

                FakeRequest request = fakeRequest("PUT", "/profiles/business/"+correctUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When wrong field in update response: "+ contentAsString(result),
                        400, status(result));
            }
        });

    }

   @Test
    public void denyingWrongUserIds(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                ObjectNode body = Json.newObject();

                FakeRequest request = fakeRequest("PUT", "/profiles/business/zleId").withJsonBody(body);
                Result result = route(request);

                assertEquals("When wrong userId in update response: "+ contentAsString(result),
                        404, status(result));
            }
        });

    }

    @Test
    public void acceptingCorrectUserIds(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                ObjectNode body = Json.newObject();

                FakeRequest request = fakeRequest("PUT", "/profiles/business/"+correctUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When correct userId in update response: "+ contentAsString(result),
                        200, status(result));
            }
        });

    }

    @Test
    public void updatingProfile(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                ObjectNode body = Json.newObject();
                body.put("name", "Tawo Club");
                body.put("phone", "888 613 538");
                body.put("category_list", "[ Bar, Pub ]");

                FakeRequest request = fakeRequest("PUT", "/profiles/business/"+correctUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When correct userId in update response: "+ contentAsString(result),
                        200, status(result));
            }
        });

    }

    @Test
    public void createAndDeleteUserProfile(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String newUserId = "553f5db8d4c6879782ddca7f";
                ObjectNode body = Json.newObject();
                body.put("name", "Nowy klub");
                body.put("phone", "888 613 538");
                body.put("category_list", "[ Bar, Pub ]");

                FakeRequest request = fakeRequest("POST", "/profiles/business/"+newUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When correct userId in create user response: "+ contentAsString(result),
                        200, status(result));

                FakeRequest request2 = fakeRequest("DELETE", "/profiles/business/"+newUserId);
                Result result2 = route(request2);

                assertEquals("When correct userId in delete user response: "+ contentAsString(result2),
                        200, status(result2));
            }
        });

    }

    @Test
    public void getAllUserProfilesIds(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                FakeRequest request = fakeRequest("GET", "/profiles/business/all");
                Result result = route(request);

                assertEquals("When obtaining all bussiness ids: "+ contentAsString(result),
                        200, status(result));
                System.out.println(contentAsString(result));


            }
        });

    }
}
