import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class GeneticAlgorithm {
    private List<Route> routes;
    private int countCrossover;
    private int countMutation;

    public GeneticAlgorithm() {
        DataManagement.readData();
    }

    public void execute() {
        routes = buildInitialPopulation();
        evolve();
    }

    private List<Route> buildInitialPopulation() {
        List<Route> routes = new ArrayList<>();
        List<Integer> cityIndexList = new ArrayList<>();

        for (int i = 1; i <= Configuration.INSTANCE.countCities; i++) {
            cityIndexList.add(i);
        }

        for (int i = 0; i < Configuration.INSTANCE.populationQuantity; i++) {
            Collections.shuffle(cityIndexList, Configuration.INSTANCE.randomGenerator);
            routes.add(Route.build(cityIndexList));
        }

        return routes;
    }

    private void evolve() {
        int currentGeneration = 0;
        int bestFitness = Integer.MAX_VALUE;

        while (Configuration.INSTANCE.maximumCountGeneration != currentGeneration) {
            currentGeneration++;

            for (Route route : routes) {
                Fitness.evaluate(route);
            }

            sort(routes);

            List<Route> matingPool =  tournamentSelection(routes, Configuration.INSTANCE.truncationNumber);
            // List<Route> matingPool = select(routes, Configuration.INSTANCE.truncationNumber);

            List<Route> children = orderCrossover(matingPool);
            //mutate(children);
            inversionMutate(children);

            removeLastNChromosomes(routes, (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * routes.size() * (1 / 10.0)));
            addChildrenToPopulation(routes, children);

            for (Route route : routes) {
                Fitness.evaluate(route);
            }

            if ((int) Math.round(getFittestChromosome(routes).getFitness()) < Math.round(bestFitness)) {
                bestFitness = (int) Math.round(getFittestChromosome(routes).getFitness());
                System.out.println(currentGeneration + " | bestFitness = " + (int) Math.round(getFittestChromosome(routes).getFitness()));
            }

            sort(routes);
        }

        System.out.println();

        System.out.println("[tour management]");
        for (Car car : routes.get(0).getCars()) {
            System.out.print(car.getRoute() + " ");
        }

        System.out.println();
        System.out.println();
        System.out.println("[best route]");
        Utility.printRoute(routes.get(0), Configuration.INSTANCE.cities);

        System.out.println();
        System.out.println("countCrossover | " + countCrossover);
        System.out.println("countMutation  | " + countMutation);
    }

    private List<Route> select(List<Route> routes, int limit) {
        return routes.stream().limit(limit).collect(Collectors.toList());
    }

    private List<Route> tournamentSelection(List<Route> routes, int limit){
        List<Route> tempMatingPool = new ArrayList<Route>();
        int currentMemberIndex = 0;
        //tournament size set to 10% of population
        int k = (int) ((0.01) * Configuration.INSTANCE.populationQuantity);
        //int k = (int) (Configuration.INSTANCE.randomGenerator.nextDouble()  * Configuration.INSTANCE.populationQuantity);
        //generate parents
        while(currentMemberIndex < limit){
            //randomly pick individuals to compete in tournament
            List<Route> tournament = new ArrayList<>();
            for(int i = 0; i<k; i++){
                int tempIndex = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);
                tournament.add(routes.get(tempIndex));
            }
            //order tournament and get best member
            sort(tournament);
            Route bestIndividual = tournament.get(0);
            tempMatingPool.add(currentMemberIndex, bestIndividual);
            currentMemberIndex++;
        }
        return tempMatingPool;
    }
    private List<Route> crossover(List<Route> routes) {
        Collections.shuffle(routes);
        List<Route> children = new ArrayList<>();

        for (int i = 0; i < routes.size(); i += 2) {
            if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.crossoverRate) {
                countCrossover++;

                List<Integer> parent01 = new ArrayList<>();
                List<Integer> parent02 = new ArrayList<>();

                for (Car car : routes.get(i).getCars()) {
                    parent01.addAll(car.getRoute());
                }

                for (Car car : routes.get(i + 1).getCars()) {
                    parent02.addAll(car.getRoute());
                }

                List<Integer> tempChild01 = new ArrayList<>(Collections.nCopies(Configuration.INSTANCE.countCities, 0));
                List<Integer> tempChild02 = new ArrayList<>(Collections.nCopies(Configuration.INSTANCE.countCities, 0));

                int upperBound = Configuration.INSTANCE.randomGenerator.nextInt(parent01.size());
                int lowerBound = Configuration.INSTANCE.randomGenerator.nextInt(parent01.size() - 1);

                int start = Math.min(upperBound, lowerBound);
                int end = Math.max(upperBound, lowerBound);

                List<Integer> parent01Genes = new ArrayList<>(parent01.subList(start, end));
                List<Integer> parent02Genes = new ArrayList<>(parent02.subList(start, end));

                tempChild01.addAll(start, parent01Genes);
                tempChild02.addAll(start, parent02Genes);

                for (int j = 0; j <= parent01Genes.size() - 1; j++) {
                    parent01.remove(parent02Genes.get(j));
                    parent02.remove(parent01Genes.get(j));
                }

                for (int z = 0; z < parent01.size(); z++) {
                    tempChild01.set(tempChild01.indexOf(0), parent02.get(z));
                    tempChild02.set(tempChild02.indexOf(0), parent01.get(z));
                }

                Route child01CityRoute = Route.build(tempChild01);
                Route child02CityRoute = Route.build(tempChild02);

                children.add(child01CityRoute);
                children.add(child02CityRoute);
            }
        }

        return children;
    }

    private List<Route> orderCrossover(List<Route> routes){
        Collections.shuffle(routes);
        List<Route> children = new ArrayList<>();

        for (int i = 0; i < routes.size(); i += 2) {
            if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.crossoverRate) {
                countCrossover++;

                List<Integer> parent01 = new ArrayList<>();
                List<Integer> parent02 = new ArrayList<>();

                for (Car car : routes.get(i).getCars()) {
                    parent01.addAll(car.getRoute());
                }

                for (Car car : routes.get(i + 1).getCars()) {
                    parent02.addAll(car.getRoute());
                }

                List<Integer> child01 = new ArrayList<>();
                List<Integer> child02 = new ArrayList<>();

                //crossover points
                int crossPoint1 = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);
                int crossPoint2 = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);

                //order cross points
                if (crossPoint1 > crossPoint2) {
                    int temp = crossPoint1;
                    crossPoint1 = crossPoint2;
                    crossPoint2 = temp;
                }

                //add first parent genes to each child

                child01.addAll(parent01.subList(crossPoint1, crossPoint2));
                child02.addAll(parent02.subList(crossPoint1, crossPoint2));

                final int size = parent02.size();

                //tracks position of current customer in children
                int currentIndex = 0;
                int currentCustomerInParent1 = 0;
                int currentCustomerInParent2 = 0;

                for (int j = 0; j < size; j++) {
                    currentIndex = (crossPoint2 + j) % size; // wraps around to beginning of list after crossing endpoint

                    // get the customer at the current index in each parent
                    currentCustomerInParent1 = parent01.get(currentIndex);
                    currentCustomerInParent2 = parent02.get(currentIndex);

                    // if child 1 does not already contain the current customer in parent 2, add it
                    if (!child01.contains(currentCustomerInParent2)) {
                        child01.add(currentCustomerInParent2);
                    }

                    // if child 2 does not already contain the current customer in parent 1, add it
                    if (!child02.contains(currentCustomerInParent1)) {
                        child02.add(currentCustomerInParent1);
                    }
                }
                //i rotate the children to allign the segments from the cross over to the original points
                //i.e, the start of the children (the original segment) is shifted to the index of the first cross over
                Collections.rotate(child01, crossPoint1);
                Collections.rotate(child02, crossPoint1);
                Route child01CityRoute = Route.build(child01);
                Route child02CityRoute = Route.build(child02);
                children.add(child02CityRoute);
                children.add(child01CityRoute);
            }
        }
        return children;
    }
    private void mutate(List<Route> children) {
        for (Route child : children) {
            List<Integer> currentChromosome = new ArrayList<>();

            for (Car gene : child.getCars()) {
                currentChromosome.addAll(gene.getRoute());
            }

            for (Integer city : currentChromosome) {
                if (Configuration.INSTANCE.randomGenerator.nextDouble() < Configuration.INSTANCE.mutationRate) {
                    countMutation++;
                    int tempIndex = currentChromosome.indexOf(city);
                    int tempValue = city;
                    int indexToSwap = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);
                    int valueToSwap = currentChromosome.get(indexToSwap);
                    currentChromosome.set(tempIndex, valueToSwap);
                    currentChromosome.set(indexToSwap, tempValue);
                }
            }

            Route mutatedRoute = Route.build(currentChromosome);
            routes.add(mutatedRoute);
        }
    }
    public void inversionMutate(List<Route> children){
        for (Route child : children) {
            List<Integer> currentChromosome = new ArrayList<>();

            for (Car gene : child.getCars()) {
                currentChromosome.addAll(gene.getRoute());
            }

            if (Configuration.INSTANCE.randomGenerator.nextDouble()
                        < Configuration.INSTANCE.mutationRate) {

                countMutation++;
                int index1 = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);
                int index2 = (int) (Configuration.INSTANCE.randomGenerator.nextDouble() * Configuration.INSTANCE.countCities);

                if(index1 > index2 ){
                    int temp = index1;
                    index1 = index2;
                    index2 = temp;
                }

                List<Integer> subL = currentChromosome.subList(index1, index2);
                Collections.reverse(subL);

                int tempArr[] = new int[subL.size()];
                //avoids concurrent access java bug with modification of lists using sublists
                for(int i = 0; i< subL.size(); i++){
                    tempArr[i] = subL.get(i);
                }
                for(int i = 0; i< subL.size(); i++){
                    currentChromosome.set(index1,tempArr[i]);
                    index1++;
                }
                //put this inside the if, so that it only adds mutated children to your population - original code was growing population
                Route mutatedRoute = Route.build(currentChromosome);
                routes.add(mutatedRoute);
            }
        }
    }

    public Route getFittestChromosome(List<Route> routes) {
        return routes.get(0);
    }

    private void addChildrenToPopulation(List<Route> population, List<Route> newChildren) {
        population.addAll(newChildren);
    }
    //
    private void removeLastNChromosomes(List<Route> population, int n) {
        for (int i = 0; i < n; i++) {
            int indexToRemove = (int) ((population.size() - n) + Configuration.INSTANCE.randomGenerator.nextDouble() * n);
            population.remove(indexToRemove);
        }
    }

    private void sort(List<Route> routes) {
        routes.sort(Comparator.comparing(Route::getFitness));
    }
}