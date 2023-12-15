package dat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TravelingHelperController {

    public static List<String> stationer = new ArrayList<>();

    public static double price;



    public Handler getAllStationsForYourRoute(){
       return context -> { context.json(stationer);

       };
    }

    public Handler getPrice() {
        return context -> {context.json(price);

        };


    }



    public Handler findRoute() {
        return ctx -> {
            stationer.clear();
            ObjectMapper objectMapper = new ObjectMapper();
            StationData sd = objectMapper.readValue(ctx.body(), StationData.class);
            String startStation = sd.getStartStation();
            String endStation = sd.getEndStation();
            FileIO fileIO = new FileIO();
            List<String> busRoute = fileIO.makeRoute40E(startStation, endStation);
            stationer.addAll(busRoute.stream().collect(Collectors.toList()));


        };

    }

    public Handler findPrice() {
        return ctx -> {

            ObjectMapper objectMapper = new ObjectMapper();
            StationData sd = objectMapper.readValue(ctx.body(), StationData.class);
            String startStation = sd.getStartStation();
            String endStation = sd.getEndStation();
            FileIO fileIO = new FileIO();
            List<String> zones = fileIO.getZones40E(startStation, endStation);
            double amountOfZones = zones.stream().distinct().count();
            double priceFinal = getTravelCost(amountOfZones);
            price = priceFinal;


        };

    }

    public double getTravelCost(double k) {

        return 16 +( k - 1) * 6;




    }



}
