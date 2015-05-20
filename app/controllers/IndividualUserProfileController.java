package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.WriteResult;
import models.IndividualUserProfile;
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

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Pine on 17/05/15.
 */
public class IndividualUserProfileController extends Controller {
    static DBIndividualProfileService dbIndividualProfileService = DBServicesProvider.getDbIndividualProfileService();
    static Morphia mapper = new Morphia();

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
            UpdateOperations<IndividualUserProfile> updateOperation = dbIndividualProfileService.createUpdateOperations()
                    .set(field.getKey(), field.getValue().asText());
            UpdateResults updateResults = dbIndividualProfileService.getDatastore().update(query, updateOperation, true);
            System.out.println("Uaktualnienie: " + field.getKey() + " z wartoscia: " + field.getValue() + " powiodlo sie (1 oznacza tak): " + updateResults.getInsertedCount());
        }

        return ok("Profile updated");
    }


    public static Result deleteProfile(String userId) {
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


