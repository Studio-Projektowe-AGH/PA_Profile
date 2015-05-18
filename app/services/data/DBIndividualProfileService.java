package services.data;

import com.mongodb.MongoClient;
import models.IndividualUserProfile;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by Pine on 17/05/15.
 */
public class DBIndividualProfileService extends BasicDAO<IndividualUserProfile, ObjectId> {

    protected DBIndividualProfileService(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
    }

}
