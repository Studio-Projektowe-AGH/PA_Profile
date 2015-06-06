package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * Created by Kris on 2015-05-07.
 */

@Entity(value = "businessUserProfiles", noClassnameStored=true)
public class BusinessUserProfile {

    @Id
    private ObjectId id;

    public String name;
    public List<String> category_list;// = new ArrayList<String>();
    public String about;

    @Embedded
    public Location location = new Location();
    
    @Embedded
    public LocationCoordinates locationCoordinates;// = new LocationCoordinates();
    public String website;
    public List<String> music_genres;// = new ArrayList<String>();
    public String phone;
    public String picture_url;




    public BusinessUserProfile(){};

    public BusinessUserProfile(String  userId) {
        id = new ObjectId(userId);
    }

    public ObjectId getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
