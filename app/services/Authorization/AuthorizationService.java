package services.authorization;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import models.LoginCredentials;
import net.minidev.json.JSONObject;
import org.bson.types.ObjectId;
import play.Play;
import services.data.DBLoginCredentialsService;
import services.data.DBServicesProvider;

import java.text.ParseException;

/**
 * Created by Kris on 2015-05-08.
 */
public class AuthorizationService {

    private static DBLoginCredentialsService  dbLoginCredentialsService = DBServicesProvider.getDbLoginCredentialsService();

    public static String verifyToken(String auth_token) throws ParseException, JOSEException {
        //TODO to ma być wywoływana autoryzacja z zewnętrzenego serwisu
//        ObjectNode body = Json.newObject();
//        body.put("auth_token",auth_token);
//
//        ClientRequest request = new ClientRequest("https://partyadvisor.herokuapp.com/auth/verifyToken");
//        request.accept("application/json");
//        request.body("application/json",body);
//
//        ClientResponse<JsonNode> response = null;
//        try {
//            response = request.post(JsonNode.class);
//            if(response.getStatus() == 200){
//                return response.getEntity().get("email").asText();
//            }else{
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

        String userId = getUserId(auth_token);
        LoginCredentials user = dbLoginCredentialsService.findOneById(userId);

        return user.getEmail();
    }

    public static String getUserId(String auth_token) throws ParseException, JOSEException {
        String secret = Play.application().configuration().getString("jwt.secret");
        JWSObject jwsObject = JWSObject.parse(auth_token);
        JWSVerifier verifier = new MACVerifier(secret.getBytes());
        jwsObject.verify(verifier);

        JSONObject jsonObject =  jwsObject.getPayload().toJSONObject();
        return (String) jsonObject.get("userId");

    }

}
