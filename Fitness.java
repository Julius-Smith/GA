public class Fitness {
    private Fitness() {
    }

    public static void evaluate(Route route) {
        double currentDistance = 0;
        int currentDemand;
        //penalty for time-window
        int penalty = 0;

        for (Car car : route.getCars()) {
            int tempVehicleCapacity = Configuration.INSTANCE.vehicleCapacity;
            int listSize = car.getRoute().size() - 1;

            for (int i = 0; i < listSize; i++) {
                if (i == 0) {
                    currentDistance += Configuration.INSTANCE.distanceMatrix.get(0).get(car.getRoute().get(0));
                } else {
                    currentDistance += Configuration.INSTANCE.distanceMatrix.get(car.getRoute().get(i - 1)).get(car.getRoute().get(i));
                }

                //punishing fitness according to demand and capacity
                currentDemand = (int)Configuration.INSTANCE.cities.get(i).getDemand();

                while (currentDemand > 0) {
                    if (tempVehicleCapacity - currentDemand < 0) {
                        currentDemand -= tempVehicleCapacity;
                        currentDistance += Configuration.INSTANCE.distanceMatrix.get(car.getRoute().get(i)).get(0);
                        currentDistance += Configuration.INSTANCE.distanceMatrix.get(0).get(car.getRoute().get(i));
                        tempVehicleCapacity = Configuration.INSTANCE.vehicleCapacity;
                    } else {
                        tempVehicleCapacity -= currentDemand;
                        currentDemand = 0;
                    }
                }

                //punish fitness according to time window
//
//                    //setting time of car to start of window of first customer
//                    int tempCustomerIndex = car.getRoute().get(i);
//                    City tempCustomer = Configuration.cities.get(tempCustomerIndex);
//                    int tempReadyTime = (int)tempCustomer.getReadyTime();
//                    int tempDueTime = (int)tempCustomer.getDueTime();
//                    if(car.getCurrentTime() < tempReadyTime){
//                        car.setTime(tempReadyTime);
//                    }
//
//                    if(car.getCurrentTime() > tempReadyTime) {
//                        penalty +=  car.getCurrentTime() - tempReadyTime;
//                    }
//
//                    car.updateTime();

                }



            currentDistance += Configuration.INSTANCE.distanceMatrix.get(car.getRoute().get(listSize)).get(0);
        }
        route.setFitness(currentDistance + (0.15)*penalty);
    }
}