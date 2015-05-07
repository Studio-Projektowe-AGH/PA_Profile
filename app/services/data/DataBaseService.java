package services.data;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import models.BusinessUserProfile;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * Created by Kris on 2015-05-07.
 */
public class DataBaseService extends BasicDAO<BusinessUserProfile, ObjectId>{

    protected DataBaseService(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
    }

    public BusinessUserProfile findOneByEmail(String email){
        Query<BusinessUserProfile> query = createQuery().field("email").equal(email.toLowerCase());
        return findOne(query);
    }
    public WriteResult deleteOne(String email){
        return delete(findOneByEmail(email));
    }
}
