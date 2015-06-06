package models;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Kris on 2015-06-06.
 */
@Embedded
public class LocationCoordinates {
    Double xCoordinate;
    Double yCoordinate;

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
