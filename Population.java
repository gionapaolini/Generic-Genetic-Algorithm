package GenericGA;

import GenericGA.SpecificImplementations.KnapsackIndividual;
import GenericGA.SpecificImplementations.TSPIndividual;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Population {

    private Individual[] population;
    private Individual[] matingPool;
    private int currentGenerationCount;
    private Text label;
    private double mutationRate;

    public Population(String type, int popsize, double mutationRate, Text layout){
        this.mutationRate = mutationRate;
        initializePopulation(type,popsize);
        this.label = layout;
    }

    public void initializePopulation(String type, int popsize){
        population = new Individual[popsize];
        matingPool = new Individual[popsize/2];
        switch (type){
            case "KNAPSACK":
                for (int i=0;i<popsize;i++){
                    population[i] = new KnapsackIndividual();
                }

                break;
            case "TSP":
                for (int i=0;i<popsize;i++){
                    population[i] = new TSPIndividual();
                }
                break;
        }

        updateFitness();
    }


    public void newGeneration(){
        currentGenerationCount++;

        updateMatingPool();
        reproduction();
        mutatePopulation(mutationRate);
        updateFitness();
        Individual best = getBestIndividual();
        best.printIndividual(label);
    }

    private void printPopulation() {
        for(int i=0; i<population.length;i++){
            population[i].printIndividual();
        }
    }


    private void updateMatingPool(){

        for(int i=0; i<matingPool.length;i++){
            matingPool[i] = rouletteWheel();
        }
    }


    public Individual getBestIndividual(){
        double best = 0;
        int bestindex = -1;
        for (int i =0;i<population.length;i++){
            if(best<population[i].getFitness()){
                best=population[i].getFitness();
                bestindex = i;
            }

        }

        return population[bestindex];

    }
    private Individual rouletteWheel(){

        int index = 0;
        double random = Math.random();
        while (random>0){
            random = random-population[index].getProbability();
            index++;
        }

        index--;

        return population[index];

    }


    private void reproduction() {

        for (int i=0; i<population.length;i++){
            population[i] = getRandomFromMatingPool().crossover(getRandomFromMatingPool());
        }


    }


    private Individual getRandomFromMatingPool(){
        int random = (int)(Math.random()*matingPool.length);
        return matingPool[random];
    }



    private void mutatePopulation(double mutationRate){


        for (int i=0;i<population.length;i++){
            population[i].mutate(mutationRate);
        }


    }

    private void updateFitness(){
        double totalFitness = 0;

        for (int i=0;i<population.length;i++){
            population[i].updateFitness();
            totalFitness+=population[i].getFitness();
        }

        for (int i=0;i<population.length;i++){
            population[i].updateProb(totalFitness);
        }


    }

    public double getMedian() {
        double[] fitnessArray = new double[population.length];
        for (int i =0;i<population.length;i++){
            fitnessArray[i]=population[i].getFitness();
        }
        Arrays.sort(fitnessArray);
        double median;
        if (fitnessArray.length % 2 == 0)
            median = ((double)fitnessArray[fitnessArray.length/2] + (double)fitnessArray[fitnessArray.length/2 - 1])/2;
        else
            median = (double) fitnessArray[fitnessArray.length/2];
        return median;

    }

    public int getCurrentGenerationCount() {
        return currentGenerationCount;
    }


    public Individual[] getPopulation() {

        return population;

    }

    public void printMatingPool(){

        System.out.println("Printing Mating Pool");


        for (int i=0;i<matingPool.length;i++){
            matingPool[i].printIndividual();
        }

        System.out.println("END Mating Pool");

    }
}
