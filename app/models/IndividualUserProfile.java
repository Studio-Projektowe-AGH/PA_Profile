package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * Created by Pine on 17/05/15.
 */

@Entity(value = "individualUserProfiles", noClassnameStored=true)
public class IndividualUserProfile {

    @Id
    private ObjectId id;
    public String first_name;
    public String last_name;
    public int age;
    public List<String> friends_list;
    public String picture_url;
    public List<String> favourite_genres;
    public List<String> favourite_bands;
    public SocialID social_id;



    public IndividualUserProfile(){};

    public IndividualUserProfile(String  userId) {
        id = new ObjectId(userId);
    }

    public ObjectId getId() {
        return id;
    }
}
