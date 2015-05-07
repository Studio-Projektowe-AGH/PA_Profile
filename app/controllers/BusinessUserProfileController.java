package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.BusinessUserProfile;
import org.mongodb.morphia.Morphia;
import play.libs.Json;
import services.data.DataBaseService;
import services.data.DataBaseServiceProvider;

/**
 * Created by Kris on 2015-05-07.
 */
public class BusinessUserProfileController {
    static DataBaseService dataBaseService = DataBaseServiceProvider.getDataBaseService();





    private static JsonNode getBusinessUserProfile(String email){
        Morphia morphia = new Morphia().map(BusinessUserProfile.class);

        BusinessUserProfile profile = dataBaseService.findOneByEmail(email);
        JsonNode result = Json.parse(morphia.toDBObject(profile).toString());

        return result;
    }

}
