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
 * Created by Kris on 2015-05-08.
 */
public class UpdateProfileTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass(){
        application = fakeApplication();
    }


    @Test
    public void handleCorrectProfileUpdate(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                ObjectNode body = Json.newObject();
                body.put("auth_token","THERE SHOULD BE TOKEN - INSERT IT HERE LATER");
                body.put("name", "Tawo Club");
                body.put("category", "Students clubs");
                FakeRequest request = fakeRequest("POST", "/updateProfile").withJsonBody(body);

                Result result = route(request);

                assertEquals("When correct update response: "+ contentAsString(result),
                        200, status(result));
            }
        });

    }

}
