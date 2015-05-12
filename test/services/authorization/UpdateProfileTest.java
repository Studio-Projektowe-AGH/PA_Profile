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
                String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTQ5MTVkMDUyNDIyOWY4Y2IwNDc1ZTQiLCJ1c2VyUm9sZSI6ImluZGl2aWR1YWwifQ.eDHKz_XsePl7wmqiargSJF7csNGN22xwF84WZvdh1O4";

                ObjectNode body = Json.newObject();
                body.put("auth_token",token);
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
