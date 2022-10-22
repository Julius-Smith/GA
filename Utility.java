import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utility {
    private Utility() {
    }

    public static List<List<Double>> calculateDistanceMatrix(Map<Integer, City> cities) {
        Set<Integer> tempCities = cities.keySet();
        List<List<Double>> distanceMatrix = new ArrayList<>();

        for (Integer city01 : tempCities) {
            List<Double> tempDistance = new ArrayList<>();
            for (Integer city02 : tempCities) {
                if (!city01.equals(city02)) {
                    List<Double> city01Coordinates = cities.get((city01)).getCoordinates();
                    List<Double> city02Coordinates = cities.get((city02)).getCoordinates();
                    tempDistance.add(calculateEuclideanDistance(city01Coordinates.get(0), city02Coordinates.get(0), city01Coordinates.get(1), city02Coordinates.get(1)));
                } else {
                    tempDistance.add(0.0);
                }
            }
            distanceMatrix.add(tempDistance);
        }

        return distanceMatrix;
    }

    public static double calculateEuclideanDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static void printRoute(Route route, Map<Integer, City> cities) {
        int vehicleID = 0;

        for (Car car : route.getCars()) {
            vehicleID++;
            System.out.print("vehicle #" + vehicleID + " | route = [ " + cities.get(0).getName() + " -> ");

            for (int i = 0; i < car.getRoute().size(); i++) {
                if (i == car.getRoute().size() - 1) {
                    System.out.print(cities.get(car.getRoute().get(i)).getName() + " -> depot]");
                } else {
                    System.out.print(cities.get(car.getRoute().get(i)).getName() + " -> ");
                }
            }

            System.out.println();
        }
    }
}