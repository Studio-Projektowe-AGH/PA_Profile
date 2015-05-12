package services.data;

import com.mongodb.MongoClient;
import models.LoginCredentials;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import play.Logger;

import java.util.List;

/**
 * Created by Wojtek on 20/04/15.
 */


public class DBLoginCredentialsService extends BasicDAO<LoginCredentials, ObjectId> {

    public DBLoginCredentialsService(MongoClient mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    public boolean exists(LoginCredentials loginCredentials) {
        Query<LoginCredentials> query = createQuery().field("email").equal(loginCredentials.getEmail().toLowerCase());
        Logger.debug("Exists: " + query.toString());

        return exists(query);
    }

    public List<LoginCredentials> findByCredentials(LoginCredentials credentials) {
        Query<LoginCredentials> query = createQuery().field("email").equal(credentials.getEmail().toLowerCase());
        Logger.debug("Find By Credentials: " + query.toString());

        return find(query).asList();
    }

    public LoginCredentials findOneByCredentials(LoginCredentials credentials) {
        Query<LoginCredentials> query = createQuery().field("email").equal(credentials.getEmail().toLowerCase());
        Logger.debug("Find One By Credentials: " + query.toString());

        return findOne(query);
    }

    public LoginCredentials findOneById(String id){
//        LoginCredentials loginCredentials = new LoginCredentials();
//        loginCredentials.setId(new ObjectId(id));
//
        return get(new ObjectId(id));
    }

}
