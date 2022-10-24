import java.util.ArrayList;
import java.util.List;

public class Route implements Comparable<Route>{
    private List<Car> cars;
    private double fitness;

    public Route() {
    }

    public static Route build(List<Integer> cityIndexList) {
        List<Car> tempCars = new ArrayList<>();
        Route route = new Route();
        int n = Configuration.INSTANCE.countCities / Configuration.INSTANCE.vehicleQuantity;

        for (int k = 0; k < Configuration.INSTANCE.vehicleQuantity; k++) {
            List<Integer> stops = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                //adding the depo to the start and end of every car route
                if (j == n - 1 && k == Configuration.INSTANCE.vehicleQuantity - 1) {
                    for (int l = 0; l < Configuration.INSTANCE.countCities % Configuration.INSTANCE.vehicleQuantity; l++) {
                        stops.add(cityIndexList.get(0));
                        int index = cityIndexList.remove(0);
                        cityIndexList.add(index);
                    }
                }

                stops.add(cityIndexList.get(0));
                int num = cityIndexList.remove(0);
                cityIndexList.add(num);
            }

            Car car = new Car();
            car.setRoute(stops);
            tempCars.add(car);
        }

        route.setCars(tempCars);
        return route;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Route route) {
        return (int) (fitness - route.getFitness());
    }
}