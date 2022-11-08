import java.io.*;

public class ParameterTuner {

    public static void main(String args[]){

        try {
            File file = new File("Parametertuning.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            double best = 0;
            double bestP = 0;
            double bestT = 0;
            double bestM =0;
            double bestC = 0;
            //pop
            for(int i = 1000; i<=3500; i+= 500){
                Configuration.INSTANCE.populationQuantity = i;
                //truncation
                for(int j = 250; j<=250; j+=150) {
                    Configuration.INSTANCE.truncationNumber = j;
                    //mutation
                    for(double mutation = 0.001; mutation <= 0.005; mutation += 0.001){
                        Configuration.INSTANCE.mutationRate = mutation;
                        //crossover
                        for(double crossover = 0.5; crossover <=0.7; crossover += 0.05){
                            Configuration.INSTANCE.crossoverRate = crossover;
                            System.out.println(Configuration.INSTANCE.populationQuantity);
                            System.out.println(Configuration.INSTANCE.truncationNumber);
                            System.out.println(Configuration.INSTANCE.mutationRate);
                            System.out.println(Configuration.INSTANCE.crossoverRate);

                            String Dist = Application.go();
                            String output = "Popsize: " + Integer.toString(i) + "\nTruncation: " + Integer.toString(j) +
                                    "\nMuationR: " + Double.toString(mutation) + "\nCrossoverR: " + Double.toString(crossover)
                                    + "\nDistance:  " + Dist + "\n";
                            out.println(output);
                            if(Double.parseDouble(Dist) >= best){
                                best = Double.parseDouble(Dist);
                                bestP = i;
                                bestT = j;
                                bestM = mutation;
                                bestC =crossover;
                            }
                        }
                    }
                }
            }

            out.println("Best: " + best);
            out.println("P: " + bestP);
            out.println("T: " + bestT);
            out.println("M: " + bestM);
            out.println("C: " + bestC);
            out.close();
            bw.close();
            fw.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}