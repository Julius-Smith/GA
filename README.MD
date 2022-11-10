Assignment Pt1: GA

## Parameter Tuning:
The ParameterTuner class has been set up to call the Application class. Accordingly, to use the PT change the Application main
method to: *go()* and have it return a string.

i.e., public static String go() {}

have it do the following: return geneticAlgorithm.execute();

geneticAlgorithm is also modified to return a string, which is the best fitness from the population

## Normal Running:
run main method in Application class.
Fitness function is punishing time window heavily to insure cars arrive on time.
Additionally, several mutation methods were attempted. Swap works the best.
A new survival implementation has been implemented to ensure enough population diversity whilst protecting 
good solutions with an elitism ratio. 