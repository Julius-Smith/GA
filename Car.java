import java.util.List;

public class Car {
    //Current time is to keep track of customer schedule.
    //This will be initialised to the starting drop-off time of the first customer of the route
    //i.e., the car would wait at the depo until it is time to go to the first customer
    private int currentTime = 0;
    private List<Integer> route;

    public Car() {
    }

    public List<Integer> getRoute() {
        return route;
    }

    public void setRoute(List<Integer> route) {
        this.route = route;
    }

    public void updateTime() {this.currentTime +=10;}

    public void setTime(int time){this.currentTime = time;}

    public int getCurrentTime(){return this.currentTime;}
}