package controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Kris on 2015-05-07.
 */
public class TestOfWebServiceClient extends Controller {

//    public static F.Promise<Result> test(){
//        ObjectNode body = Json.newObject();
//        body.put("email","krzysiekplachno@gmail.com");
//        body.put("password", "krzysiekplachno@gmail.com");
//        body.put("role","idividual");
//        F.Promise<WSResponse> resultOfLogging = WS.url("https://partyadvisor.herokuapp.com/auth/signup")
//                .setHeader("content-type", "application/json")
//                .post(body);
//
//        F.Promise<Result> map = resultOfLogging.map(
//                new F.Function<WSResponse, Result>() {
//                    @Override
//                    public Result apply(WSResponse wsResponse) throws Throwable {
//                        return (Result) ok("Wartosc zwrocona przez zapytanie: " + wsResponse.getStatusText());
//                    }
//                }
//        );
//        System.out.println(map.get(1000L));
//        return map;
//
//    }

    public static Result restEasyTest(){
        ObjectNode body = Json.newObject();
        body.put("email","krzysiekplachno@gmail.com");
        body.put("password", "krzysiekplachno@gmail.com");
        body.put("role","idividual");

        ClientRequest request = new ClientRequest("https://partyadvisor.herokuapp.com/auth/signup");

        request.accept("application/json");
        request.body("application/json",body);

        ClientResponse<String> response = null;
        try {
            response = request.post(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError("Error whist parsing response from other WebService; starus:" + response.getStatus());
        }

        if(response.getStatus() == 200){
            return ok("Udało się połączyć, jest ok!");
        }else {
            return internalServerError("I dont know why but the response from external server is not 200 but: " + response.getStatus() + response.getEntity());
        }
    }
}
