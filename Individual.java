package GenericGA;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class Individual {

    protected double fitness;
    protected double probability;


    public double getFitness() {
        return fitness;
    }

    public double getProbability() {
        return probability;
    }

    public void updateProb(double totalFitness){
        probability = fitness/totalFitness;
    }


    public abstract void generateIndividual();
    public abstract void updateFitness();
    public abstract Individual crossover(Individual partner);
    public abstract void mutate(double mutationRate);
    public abstract void printIndividual(Text label);
    public abstract void printIndividual();

}
