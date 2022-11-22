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
    public final int vehicleQuantity = 20;
    public final int vehicleCapacity = 200;

    // genetic algorithm
    public int populationQuantity = 1500;
    public final int maximumCountGeneration = 10000;
    public  double crossoverRate = 0.5;
    public  double mutationRate = 0.002;
    public  double elitismRatio = 0.2;
    //public final double mutationRateIntra = 0.08;
    public  int truncationNumber = 1000;
    public int countCities = 0;
    public List<List<Double>> distanceMatrix;

    public void initDistanceMatrix() {
        distanceMatrix = Utility.calculateDistanceMatrix(cities);
    }
}