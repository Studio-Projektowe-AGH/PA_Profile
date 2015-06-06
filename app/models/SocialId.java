package models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class SocialId {

    private String social_name;
    private String social_id;

    public String getSocial_name() {
        return social_name;
    }

    public void setSocial_name(String social_name) {
        this.social_name = social_name;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }
}