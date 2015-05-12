package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("<h3>Witaj na GoParty: Profile Service</h3> <br/>" +
                "<h5><a href=\"http://docs.gopartyprofileserviceapi.apiary.io/#\" > Dokumentacja API serwisu </a></h5>"));
    }

}
