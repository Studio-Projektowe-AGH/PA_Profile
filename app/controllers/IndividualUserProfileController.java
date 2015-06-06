package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.WriteResult;
import models.IndividualUserProfile;
import models.SocialID;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.database.DBIndividualProfileService;
import services.database.DBServicesProvider;
import services.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Pine on 17/05/15.
 */
public class IndividualUserProfileController extends Controller {
    static DBIndividualProfileService dbIndividualProfileService = DBServicesProvider.getDbIndividualProfileService();
    static Morphia mapper = new Morphia();
    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        mapper.map(IndividualUserProfile.class).getMapper().getOptions().setStoreNulls(true);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateProfile(String userId) {

        try {
            if (dbIndividualProfileService.get(new ObjectId(userId)) == null) {
                dbIndividualProfileService.save(new IndividualUserProfile(userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return notFound("This is wrong format of user id: " + userId);
        }

        JsonNode jsonBody = request().body().asJson();
        Iterator<Map.Entry<String, JsonNode>> nodeIterator = jsonBody.fields();

        while (nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = nodeIterator.next();
            boolean isValidUserField = Utils.isMemberOfClass(field.getKey(), IndividualUserProfile.class);
            if (!isValidUserField) {
                return badRequest("Wrong json field: " + field.getKey());
            }
        }
        nodeIterator = jsonBody.fields();
        Query<IndividualUserProfile> query = dbIndividualProfileService.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(userId));
        while (nodeIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = nodeIterator.next();
            UpdateOperations<IndividualUserProfile> updateOperation = null;
            if(field.getValue().isArray()){
                //if json node is list
                updateOperation = dbIndividualProfileService.createUpdateOperations()
                        .unset(field.getKey());
                dbIndividualProfileService.getDatastore().update(query, updateOperation, true);
                List<String> newValues = new ArrayList();
                for(JsonNode x: field.getValue()){
                    newValues.add(x.asText());
                }
                updateOperation = dbIndividualProfileService.createUpdateOperations()
                        .addAll(field.getKey(), newValues, false );
            }else if(field.getKey().equals("social_id")) {
                try {
                    SocialID socialID = objectMapper.readValue(field.getValue().toString(), SocialID.class);
                    updateOperation = dbIndividualProfileService.createUpdateOperations()
                            .set(field.getKey(), socialID);
                } catch (IOException e) {
                    return badRequest("Wrong format of field: "+field.getKey()+" " +field.getValue().toString());
                }
            }else {
                //if json node is not list
                updateOperation = dbIndividualProfileService.createUpdateOperations()
                        .set(field.getKey(), field.getValue().asText());
            }
            UpdateResults updateResults = dbIndividualProfileService.getDatastore().update(query, updateOperation, true);
            System.out.println("Uaktualnienie: " + field.getKey() + " z wartoscia: " + field.getValue() + " powiodlo sie (1 oznacza tak): " + updateResults.getInsertedCount());
        }

        return ok("Profile updated");
    }


    public static Result deleteProfile(String userId) {
        IndividualUserProfile profileToDelete = dbIndividualProfileService.get(new ObjectId(userId));
        if(profileToDelete == null){
            return notFound("No existing profile for this user: " + userId);
        }
        WriteResult writeResult = dbIndividualProfileService.deleteById(new ObjectId(userId));
        if (writeResult.getN() == 1) {
            return ok();
        } else {
            return internalServerError("Not deleted for some reason");
        }
    }

    public static Result getProfile(String userId) {
        IndividualUserProfile profile = dbIndividualProfileService.get(new ObjectId(userId));
        if(profile == null){
            return notFound("There is no user profile with this id: " + userId);
        }
        JsonNode profileInJson = Json.parse(mapper.toDBObject(profile).toString());
        return ok(profileInJson);
    }
}


