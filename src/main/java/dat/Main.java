package dat;

import io.javalin.Javalin;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {


        ApplicationConfig
                .startServer(
                        Javalin.create(),
                        Integer.parseInt(ApplicationConfig.getProperty("javalin.port")));



    }




}