package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.WriteResult;
import models.BusinessUserProfile;
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
import services.database.DBBusinessProfileService;
import services.database.DBServicesProvider;
import services.utils.Utils;

import java.util.Iterator;
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
            if (dbBusinessProfileService.get(new ObjectId(userId)) == null) {
                dbBusinessProfileService.save(new BusinessUserProfile(userId));
                //return notFound("There is no user with this id: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return notFound("This is wrong format of user id: " + userId);
        }



        JsonNode jsonBody = request().body().asJson();
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
            UpdateOperations<BusinessUserProfile> updateOperation = dbBusinessProfileService.createUpdateOperations()
                    .set(field.getKey(), field.getValue().asText());
            UpdateResults updateResults = dbBusinessProfileService.getDatastore().update(query, updateOperation, true);
            System.out.println("Uaktualnienie: " + field.getKey() + " z wartoscia: " + field.getValue() + " powiodlo sie (1 oznacza tak): " + updateResults.getInsertedCount());
        }

        return ok("Profile updated");
    }



    public static Result deleteProfile(String userId) {
        WriteResult writeResult = dbBusinessProfileService.deleteById(new ObjectId(userId));
        if(writeResult.getN()==1){
            return ok();
        }else {
            return internalServerError("Not deleted for some reason");
        }
    }

    public static Result getProfile(String userId) {
        BusinessUserProfile profile = dbBusinessProfileService.get(new ObjectId(userId));
        JsonNode profileInJson = Json.parse(mapper.toDBObject(profile).toString());
        return ok(profileInJson);
    }
}


