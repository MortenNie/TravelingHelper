package dat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;

public class FileIO {

public List<String> makeRoute40E(String startDestination, String endDestination) {
        File file = new File("40E");
        ArrayList<String> route = new ArrayList<>();
        List<String> finalRoute = new ArrayList<>();
        String [] routeHelper;
        try {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {

        routeHelper = scanner.nextLine().trim().split(",");
        route.add(routeHelper[0]);

        for (int i = 0; i < route.size(); i++) {
        for (int j = 0; j < route.size(); j++) {
        if (startDestination.equalsIgnoreCase(route.get(i)) && endDestination.equalsIgnoreCase(route.get(j))) {

        finalRoute = route.subList(i, j + 1);
        }
        }
        }
        }

        } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
        }

        return finalRoute;
        }
public List<String> getZones40E(String startDestination, String endDestination) {
        File file = new File("40E");
        ArrayList<String> route = new ArrayList<>();
        List<String> finalZones;
        String [] routeHelper;
        ArrayList<String> zones = new ArrayList<>();
        List<String> finalZones2 = new ArrayList<>();

        try {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {

        routeHelper = scanner.nextLine().trim().split(",");
        route.add(routeHelper[0]);
        zones.add(routeHelper[1]);

        for (int i = 0; i < route.size(); i++) {
        for (int j = 0; j < route.size(); j++) {
        if (startDestination.contains(route.get(i)) && endDestination.contains(route.get(j))) {

        finalZones = zones.subList(i, j + 1);
        finalZones2 = finalZones.stream().distinct().collect(Collectors.toList());

        }
        }
        }
        }

        } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
        }

        return finalZones2;
        }
        }
