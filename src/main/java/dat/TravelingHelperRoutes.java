package dat;

import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class TravelingHelperRoutes {

    TravelingHelperController tvc = new TravelingHelperController();

    public EndpointGroup getTravelHelperRoutes() {
        return () ->
                path("/", () -> {
                    post("findroute", tvc.findRoute());
                    post("findprice",  tvc.findPrice());

                    get("getallstationsforyourroute",tvc.getAllStationsForYourRoute());
                    get("price", tvc.getPrice());

                });
    }
}
