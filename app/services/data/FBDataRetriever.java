package services.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import play.libs.Json;

/**
 * Created by Kris on 2015-05-11.
 */
public class FBDataRetriever {

    JsonNode getFBData(String fBtoken){
        ObjectNode body = Json.newObject();
        body.put("fbToken",fBtoken);

        //TODO insert right address
        ClientRequest request = new ClientRequest("https://partyadvisor.herokuapp.com/auth/signup");

        request.accept("application/json");
        request.body("application/json",body);

        ClientResponse<JsonNode> response = null;
        try {
            response = request.post(JsonNode.class);
            if(response.getStatus() == 200){
                return response.getEntity();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
