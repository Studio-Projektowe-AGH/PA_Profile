package services.database;

import com.mongodb.MongoClient;
import models.BusinessUserProfile;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by Kris on 2015-05-07.
 */
public class DBBusinessProfileService extends BasicDAO<BusinessUserProfile, ObjectId>{

    protected DBBusinessProfileService(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
    }

}
