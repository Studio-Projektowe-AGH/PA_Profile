package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public static Result index() {

        return ok("<html><body><h3>Witaj na GoParty: Profile Service</h3> <br/>" +
                "<h5><a href=\"http://docs.gopartyprofileserviceapi.apiary.io/#\" > " +
                "Dokumentacja API serwisu, bla bla bla </a></h5></body></html>").as("text/html");
    }

}
