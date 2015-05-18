package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pine on 17/05/15.
 */

@Entity(value = "individualUserProfiles", noClassnameStored=true)
public class IndividualUserProfile {

    @Id
    private ObjectId id;
    public String avatar_url;
    public List<String> music_genres = new ArrayList<String>();



    public IndividualUserProfile(){};

    public IndividualUserProfile(String  userId) {
        id = new ObjectId(userId);
    }

    public ObjectId getId() {
        return id;
    }
}
