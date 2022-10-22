import java.util.List;

//Added components regarding ready and due time
//in this instance, City is a customer
public record City(List<Double> coordinates, double demand, String name, double readyTime, double dueTime
                   ) {
    public String getName() {
        return name;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public double getDemand() {
        return demand;
    }

    public double getReadyTime(){return readyTime;}

    public double getDueTime(){return dueTime;}
}