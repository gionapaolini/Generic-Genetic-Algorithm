package GenericGA.SpecificImplementations;


import GenericGA.Individual;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class KnapsackIndividual extends Individual {

    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    final static int N_ITEMS = 20;
    final static int MIN_WEIGHT = 1;
    final static int MAX_WEIGHT = 10;
    final static int MIN_VALUE = 1;
    final static int MAX_VALUE = 10;
    final static int maxWeight = 25;
    public static int[] weights;
    public static int[] values;

    private boolean[] encoding;


    public KnapsackIndividual(){

        if(weights==null){
            initializeItemSet("random");
        }

        generateIndividual();

    }

    public KnapsackIndividual(boolean[] encoding){

       this.encoding = encoding;

    }


    @Override
    public void generateIndividual(){
        encoding = new boolean[weights.length];
        for (int i=0; i<weights.length;i++){
            if(Math.random()>0.5)
                encoding[i] = true;
            else
                encoding[i] = false;
        }
    }


    @Override
    public void updateFitness(){
        double reward = 0;
        double totalW = 0;
        for (int i=0; i<encoding.length;i++){
            if(encoding[i]){
                reward+=values[i];
                totalW+= weights[i];
            }
        }
        if(totalW>maxWeight) {
            reward = 1;
        }
        fitness = Math.pow(reward,2);
    }


    @Override
    public Individual crossover(Individual partner){

        KnapsackIndividual p = (KnapsackIndividual) partner;

        boolean[] newIndividual = new boolean[encoding.length];

        int randomMidpoint = (int)(Math.random()*encoding.length);


        for (int i=0;i<newIndividual.length;i++){
            if(i>randomMidpoint){
                newIndividual[i] = p.encoding[i];
            }
            else{
                newIndividual[i] = encoding[i];

            }
        }

        KnapsackIndividual newIndi = new KnapsackIndividual(newIndividual);

        return newIndi;
    }

    @Override
    public void mutate(double mutationRate){

        if(Math.random()<mutationRate){

            int random = (int)(Math.random()*encoding.length);

            encoding[random] = !encoding[random];
        }

    }


    private static void initializeItemSet(String type) {
        int[] values = new int[N_ITEMS];
        int[] weights = new int[N_ITEMS];

        switch (type){
            case "random":

                for (int i = 0; i<N_ITEMS;i++){
                    values[i] = (int)(Math.random()*(MAX_VALUE-MIN_VALUE+1) + MIN_VALUE);
                    weights[i] = (int)(Math.random()*(MAX_WEIGHT-MIN_WEIGHT+1) + MIN_WEIGHT);

                }


                KnapsackIndividual.values = values;
                KnapsackIndividual.weights = weights;

                break;

            case "obvious":

                for (int i = 0; i<N_ITEMS/2;i++){
                    values[i] = 10;
                    weights[i] = 1;
                }
                for (int i = N_ITEMS/2; i<N_ITEMS;i++){
                    values[i] = 1;
                    weights[i] = 10;
                }

                KnapsackIndividual.values = values;
                KnapsackIndividual.weights = weights;

                break;
        }




    }


    public void printIndividual(Text label){
        String toPrint = "Score: "+fitness+" - \n                   ";

        for (int i = 0;i<weights.length;i++){
            if(encoding[i]){
                toPrint+="[[("+values[i]+", "+weights[i]+")]]     ";
            }else {
                toPrint+="("+values[i]+", "+weights[i]+")     ";

            }
            if(i==weights.length/2){
                toPrint+="\n                    gitr ";
            }
        }

        label.setText(toPrint);
    }

    public void printIndividual(){
        System.out.print("Score: "+fitness+" - ");
        for (int i=0;i<encoding.length;i++){
            if(encoding[i]){
                System.out.print(ANSI_GREEN_BACKGROUND+"|"+i+"|"+ANSI_RESET);
            }else {
                System.out.print("|"+i+"|");

            }

        }
        System.out.println();
    }



}
