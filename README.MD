Assignment Pt1: GA

## Parameter Tuning:
The ParameterTuner class has been set up to call the ApplicationPT class.

geneticAlgorithm is also modified to return a string, which is the best fitness from the population

The Parameter tuning results can be found in Parametertuning.txt

The best results and parameters are the last entry of the file

## Normal Running:
run main method in Application class.

Fitness function is punishing time window heavily to make sure cars arrive on time.

Additionally, several mutation methods were attempted. Swap works the best.

A new survival implementation has been implemented to ensure enough population diversity whilst protecting 
good solutions with an elitism ratio. 