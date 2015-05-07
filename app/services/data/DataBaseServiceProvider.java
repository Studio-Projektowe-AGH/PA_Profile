package services.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Morphia;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created by Kris on 2015-05-07.
 */
public class DataBaseServiceProvider {
    static DataBaseService dataBaseService = null;

    private static void createDataService(){
        String uriString = Play.application().configuration().getString("mongo.uri");
        String dbName = Play.application().configuration().getString("mongo.db");

        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uriString);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            Morphia morphia = new Morphia();

            dataBaseService = new DataBaseService(mongoClient, morphia, dbName);
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
    }

    public static DataBaseService getDataBaseService(){
        if(dataBaseService == null){
            createDataService();
        }
        return dataBaseService;
    }
}
