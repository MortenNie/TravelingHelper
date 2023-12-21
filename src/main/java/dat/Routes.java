package dat;

import dat.user.UserRoutes;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

 private final TravelingHelperRoutes thr= new TravelingHelperRoutes();

 private final UserRoutes userRoutes = new UserRoutes();



    private void requestInfoHandler(Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
        ctx.attribute("requestInfo", requestInfo);
    }

    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.before(this::requestInfoHandler);

            app.routes(() -> {
                path("/", thr.getTravelHelperRoutes());
                path("/", userRoutes.getRoutes());


            });


        };
    }
}