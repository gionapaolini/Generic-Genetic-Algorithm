package GenericGA.SpecificImplementations;

import GenericGA.Individual;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.HashMap;

public class TSPIndividual extends Individual {


    final static int MIN_DISTANCE = 1;
    final static int MAX_DISTANCE = 10;
    final static String[] cities = {"Rome", "Paris", "Amsterdam", "Brussels", "Madrid", "Berlin", "Warsaw", "London", "Oslo"};
    final static HashMap<String, Integer> distances = new HashMap();

    private int[] encoding;

    public TSPIndividual(){

        if(distances.size()<=0){
            initializeDistances("random");
        }
        generateIndividual();



    }
    public TSPIndividual(int[] encoding){
       this.encoding = encoding;
    }

    @Override
    public void generateIndividual() {

        encoding = new int[cities.length];
        for (int i=0; i<cities.length;i++){
            encoding[i] = i;
        }
        for (int i=0; i<1000;i++){
            randomSwap();
        }

    }

    @Override
    public void updateFitness() {
        double reward = 0;
        for (int i=0; i<encoding.length-1;i++){
            reward+=distances.get(encoding[i]+""+encoding[i+1]);
        }
        reward+=distances.get(encoding[0]+""+encoding[encoding.length-1]);
        reward = 1/reward;
        fitness = Math.pow(reward,1);
    }

    @Override
    public Individual crossover(Individual partner) {

        int[] newIndividual = new int[encoding.length];

        TSPIndividual p = (TSPIndividual) partner;

        int randomMidpoint = (int)(Math.random()*encoding.length);


        for (int i=0;i<randomMidpoint;i++){

            newIndividual[i] = p.encoding[i];


        }

        int count = 0;
        for (int i=0;i<encoding.length;i++){
            boolean found = false;
            for(int j=0; j<randomMidpoint;j++){
                if(encoding[i]==p.encoding[j]){
                    found = true;
                    break;
                }
            }
            if(!found){
                newIndividual[randomMidpoint+count] = encoding[i];
                count++;
            }

        }

        TSPIndividual newIndi = new TSPIndividual(newIndividual);

        return newIndi;
    }

    @Override
    public void mutate(double mutationRate) {

        if(Math.random()<mutationRate){
            randomSwap();
        }
    }


    @Override
    public void printIndividual(Text label) {

        String s = "Best Current Individual: ";
        for (int i=0;i<encoding.length;i++){
            s+=cities[encoding[i]]+" -> ";
        }

        s+=cities[encoding[0]];
        label.setText(s);

    }

    @Override
    public void printIndividual() {


    }


    private static void initializeDistances(String type) {


        switch (type) {
            case "random":

                for (int i = 0; i < cities.length; i++) {
                    for (int j = i + 1; j < cities.length; j++) {
                        int distance = (int) (Math.random() * (MAX_DISTANCE - MIN_DISTANCE + 1) + MIN_DISTANCE);
                        distances.put(i + "" + j, distance);
                        distances.put(j + "" + i, distance);
                    }
                }
                break;

            case "obvious":

                for (int i=0;i<cities.length;i++){
                    for (int j=i+1;j<cities.length;j++){
                        if(j==i+1){
                            distances.put(i+""+j,1);
                            distances.put(j+""+i,1);
                        }else {
                            distances.put(i+""+j,10);
                            distances.put(j+""+i,10);
                        }

                    }
                }

                distances.replace("08",1);
                distances.replace("80",1);

                break;
        }
    }




    public void swap(int index1, int index2){
        int temp = encoding[index1];
        encoding[index1] = encoding[index2];
        encoding[index2] = temp;
    }

    public void randomSwap(){
        int random1,random2;

        do{

            random1 = (int)(Math.random()*encoding.length);
            random2 = (int)(Math.random()*encoding.length);

        }while (random1==random2);

        swap(random1,random2);
    }
}
