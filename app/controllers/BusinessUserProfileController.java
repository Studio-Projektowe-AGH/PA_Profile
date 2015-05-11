package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.BusinessUserProfile;
import models.StringPair;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.mongodb.morphia.Morphia;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.authorization.AuthorizationService;
import services.data.DataBaseService;
import services.data.DataBaseServiceProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Kris on 2015-05-07.
 */
public class BusinessUserProfileController extends Controller{
    static DataBaseService dataBaseService = DataBaseServiceProvider.getDataBaseService();

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getBusinessUserProfile(){
        JsonNode jsonBody = request().body().asJson();
        String auth_token = jsonBody.findPath("auth_token").textValue();
        String email = AuthorizationService.verifyToken(auth_token);
        if(email == null){
            return unauthorized("Wrong token");
        }
        Morphia morphia = new Morphia().map(BusinessUserProfile.class);
        BusinessUserProfile profile = dataBaseService.findOneByEmail(email);
        if(profile == null){
            return internalServerError("Weird thing: token was verified and it's ok, but there is no profile for this user ");
        }

        JsonNode result = Json.parse(morphia.toDBObject(profile).toString());

        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateProfile(){
        JsonNode jsonBody = request().body().asJson();
        String auth_token = jsonBody.findPath("auth_token").textValue();
        String email = AuthorizationService.verifyToken(auth_token);
        if(email == null){
            return unauthorized("Wrong token");
        }
        List<StringPair> updateData = new ArrayList<StringPair>();
        Iterator<String> stringIterator = jsonBody.fieldNames();
        while(stringIterator.hasNext()){
            String fieldName = stringIterator.next();
            if(fieldName.equals("auth_token")){
                continue;
            }
            updateData.add(new StringPair(fieldName, jsonBody.findPath(fieldName).textValue()));
        }
        BusinessUserProfile userToUpdate = dataBaseService.findOneByEmail(email);
        if(userToUpdate == null){
            return Results.notFound("There is no user with such email in database");
        }
        for(StringPair x : updateData) {
            if (x.getKey().equals("name")) {
                userToUpdate.setName(x.getValue());
            } else if (x.getKey().equals("category")) {
                userToUpdate.setCategory(x.getValue());
            } else if (x.getKey().equals("description")) {
                userToUpdate.setDescription(x.getValue());
            } else if (x.getKey().equals("website")) {
                userToUpdate.setWebsite(x.getValue());
            } else {
                return Results.badRequest("Wrong name of field to update: " + x.getKey());
            }
        }

        dataBaseService.save(userToUpdate);
        return Results.ok("Profile updated");
    }



//Tu oczekuje w Jsonie tokena naszego i fbkowego
    @BodyParser.Of(BodyParser.Json.class)
    public static Result createProfile(){
        JsonNode jsonBody = request().body().asJson();
        String auth_token = jsonBody.findPath("auth_token").textValue();
        String email = AuthorizationService.verifyToken(auth_token);
        //Todo check this fields later
        String fbToken = jsonBody.findPath("fb_token").textValue();

        if(email == null){
            return internalServerError("Received token is not valid");
        }

        BusinessUserProfile newUser = new BusinessUserProfile(email, fbToken);
        dataBaseService.save(newUser);
        updateProfileFromFB(email);
        return ok();

    }

    private static boolean updateProfileFromFB(String email) {
        BusinessUserProfile user = dataBaseService.findOneByEmail(email);

        ObjectNode body = Json.newObject();
        body.put("fb_token",user.getFbToken());
        //Todo check this address
        ClientRequest request = new ClientRequest("https://partyadvisor.herokuapp.com/auth/verifyToken");
        request.accept("application/json");
        request.body("application/json",body);

        ClientResponse<JsonNode> response = null;
        try {
            response = request.post(JsonNode.class);
            if(response.getStatus() != 200){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        JsonNode fbAnwer = response.getEntity();
        user.setCategory(fbAnwer.get("category").asText());
        //Todo dopisz reszte pól

        dataBaseService.save(user);
        return true;
    }

}


