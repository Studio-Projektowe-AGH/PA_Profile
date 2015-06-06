package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.mongodb.WriteResult;
import models.BusinessUserProfile;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.database.DBBusinessProfileService;
import services.database.DBServicesProvider;
import services.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Kris on 2015-05-07.
 */
public class BusinessUserProfileController extends Controller {
    static DBBusinessProfileService dbBusinessProfileService = DBServicesProvider.getDbBusinessProfileService();
    static Morphia mapper = new Morphia();

    static {
        mapper.map(BusinessUserProfile.class).getMapper().getOptions().setStoreNulls(true);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateProfile(String userId) {
        try {
            if(!ObjectId.isValid(userId)){
                return badRequest("This is wrong format of user id: " + userId );
            }
            if (dbBusinessProfileService.get(new ObjectId(userId)) == null) {
                dbBusinessProfileService.save(new BusinessUserProfile(userId));
                //return notFound("There is no user with this id: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return notFound("Data in database is in wrong format - cannot be mapped to Java object.");
        }
        JsonNode jsonBody = request().body().asJson();
        System.out.println("Oto body json " +jsonBody);
        Iterator<Map.Entry<String, JsonNode>> nodeIterator = jsonBody.fields();

        while (nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = nodeIterator.next();
            boolean isValidUserField = Utils.isMemberOfClass(field.getKey(), BusinessUserProfile.class);
            if (isValidUserField == false) {
                return badRequest("Wrong json field: " + field.getKey());
            }
        }

        nodeIterator = jsonBody.fields();
        Query<BusinessUserProfile> query = dbBusinessProfileService.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(userId));
        while (nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = nodeIterator.next();
            UpdateOperations<BusinessUserProfile> updateOperation = null;
            if(field.getValue().isArray()){
                //if json node is list
                updateOperation = dbBusinessProfileService.createUpdateOperations()
                        .unset(field.getKey());
                dbBusinessProfileService.getDatastore().update(query, updateOperation, true);
                List<String> newValues = new ArrayList();
                for(JsonNode x: field.getValue()){
                    newValues.add(x.asText());
                }
                    updateOperation = dbBusinessProfileService.createUpdateOperations()
                            .addAll(field.getKey(), newValues, false );
            }else {
                //if json node is not list
                updateOperation = dbBusinessProfileService.createUpdateOperations()
                        .set(field.getKey(), field.getValue().asText());
            }

            UpdateResults updateResults = dbBusinessProfileService.getDatastore().update(query, updateOperation, true);
            System.out.println("Uaktualnienie: " + field.getKey() + " z wartoscia: " + field.getValue() + " powiodlo sie (1 oznacza tak): " + updateResults.getInsertedCount());
        }

        return ok("Profile updated");
    }



    public static Result deleteProfile(String userId) {
        BusinessUserProfile profileToDelete = dbBusinessProfileService.get(new ObjectId(userId));

        if(profileToDelete == null){
            return notFound("No existing profile for this user: " + userId);
        }
        WriteResult writeResult = dbBusinessProfileService.deleteById(new ObjectId(userId));
        if(writeResult.getN()==1){
            return ok();
        }else {
            return internalServerError("Not deleted for some reason");
        }
    }

    public static Result getProfile(String userId) {
        if(! ObjectId.isValid(userId)){
            return badRequest("Bad request, wrong user id: "+userId+", cannot map it to ObjectId. Check by means of ObjectId.isValid()");
        }
        BusinessUserProfile profile = null;
        try {
            ObjectId userObjectId = new ObjectId(userId);
            System.out.println("Bad ass!!!!!!!!!");
            profile = dbBusinessProfileService.get(userObjectId);

        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError("Wrong user id, cannot map it to ObjectId. Checked by catching exception");
        }

        if(profile==null){
            return notFound("No existing profile for this user: " + userId);
        }
        JsonNode profileInJson = Json.parse(mapper.toDBObject(profile).toString());
        return ok(profileInJson);
    }

    public static Result getAllProfilesId() {
        List<String> allUsers = new ArrayList<>();
        QueryResults<BusinessUserProfile> businessUserProfiles = dbBusinessProfileService.find();

        for(Key<BusinessUserProfile> x: businessUserProfiles.asKeyList()){
            try {

                allUsers.add(x.getId().toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("to jest id: " + x.getId()+  "to string" + x.toString());
            }
        }
        ObjectNode jsonResponse =  Json.newObject();
        jsonResponse.put("clubsIds",Json.parse(new Gson().toJson(allUsers)));
        return ok(jsonResponse);
    }
}


