package dat.user;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class UserRoutes {

    private final UserController userController = new UserController();

    public EndpointGroup getRoutes() {

        return () -> {
            path("/user", () -> {
                post("/login", userController::login);
                post("/register", userController::register);
            });
        };
    }
}
