import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum Configuration {
    INSTANCE;

    // random generator
    public final MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    // data management
    public final Path path = Paths.get("instance.txt");
    public static final Map<Integer, City> cities = new TreeMap<>();
    // depot
    public final int vehicleQuantity = 8;
    public final int vehicleCapacity = 200;
    //travel time
    public final int serviceTime = 10;
    // genetic algorithm
    public final int populationQuantity = 3000;
    public final int maximumCountGeneration = 10000;
    public final double crossoverRate = 0.7;
    public final double mutationRate = 0.03;
    public final int truncationNumber = 300;
    public int countCities = 0;
    public List<List<Double>> distanceMatrix;

    public void initDistanceMatrix() {
        distanceMatrix = Utility.calculateDistanceMatrix(cities);
    }
}