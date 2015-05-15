package services.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Morphia;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created by Kris on 2015-05-07.
 */
public class DBServicesProvider {
    static DBProfileService dbProfileService = null;

    private static void createDBProfileService(){
        String uriString = Play.application().configuration().getString("mongo.uri");
        String dbName = Play.application().configuration().getString("mongo.db");

        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uriString);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            Morphia morphia = new Morphia();
            morphia.getMapper().getOptions().setStoreEmpties(true);
            morphia.getMapper().getOptions().setStoreNulls(true);

            dbProfileService = new DBProfileService(mongoClient, morphia, dbName);
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
    }


    public static DBProfileService getDbProfileService(){
        if(dbProfileService == null){
            createDBProfileService();
        }
        return dbProfileService;
    }
}
