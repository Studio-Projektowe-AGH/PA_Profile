package services.authorization;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * Created by Pine on 17/05/15.
 */
public class UpdateIndividualProfileTest {

    static play.test.FakeApplication application;
    static String correctUserId = "11100111";

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
                body.put("avatar_url", "justurl");

                FakeRequest request = fakeRequest("PUT", "/profiles/individual/"+correctUserId).withJsonBody(body);
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
                body.put("thereIsNoSuchFieldInClass", "Nofield");

                FakeRequest request = fakeRequest("PUT", "/profiles/individual/"+correctUserId).withJsonBody(body);
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

                FakeRequest request = fakeRequest("PUT", "/profiles/individual/zleId").withJsonBody(body);
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

                FakeRequest request = fakeRequest("PUT", "/profiles/individual/"+correctUserId).withJsonBody(body);
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
                body.put("avatar_url", "new_url");

                FakeRequest request = fakeRequest("PUT", "/profiles/individual/"+correctUserId).withJsonBody(body);
                Result result = route(request);

                assertEquals("When correct userId in update response: "+ contentAsString(result),
                        200, status(result));
            }
        });

    }

}

