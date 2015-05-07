package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Kris on 2015-05-07.
 */

@Entity(value = "businessUserProfiles", noClassnameStored=true)
public class BusinessUserProfile {

    @Id
    private ObjectId id;

    private String email;
    private String name;
    private String category;
    private String description;
    private String website;
    private String current_locaiton;

    private String fbToken;

    public BusinessUserProfile(){};

    // TODO najlepiej jakby tutaj przekazywac w tym konstruktorze takze token fb
    public BusinessUserProfile(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getCurrent_locaiton() {
        return current_locaiton;
    }
    public void setCurrent_locaiton(String current_locaiton) {
        this.current_locaiton = current_locaiton;
    }
    public String getFbToken() {
        return fbToken;
    }
    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }
}
