package services.authorization;

/**
 * Created by Kris on 2015-05-08.
 */
public class AuthorizationService {

    public static String verifyToken(String auth_token) {
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

        return "krzysiekplachno@gmail.com";
    }
}
