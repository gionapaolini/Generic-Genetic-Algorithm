package GenericGA;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {


    XYChart.Series currentPopLine = new XYChart.Series();
    XYChart.Series allGenLine = new XYChart.Series();
    Text label = new Text("Best Individual");
    Population p;


    @Override
    public void start(Stage primaryStage) {

        BorderPane layout = new BorderPane();
        VBox vBox = new VBox();
        layout.setCenter(vBox);
        HBox horizontalLayout = new HBox();
        layout.setBottom(horizontalLayout);
        Label l1 = new Label("Population Size: ");
        TextField pSize = new TextField("100");
        Label l2 = new Label("Mutation Rate: ");
        TextField mRate = new TextField("0.01");
        Label l3 = new Label("Type of problem: ");
        final ComboBox types = new ComboBox();
        types.getItems().addAll("KNAPSACK", "TSP");
        types.getSelectionModel().selectFirst();
        Button initializePopButton = new Button("Initialize Population");
        Button increaseGenButton = new Button("Next Generation");

        horizontalLayout.getChildren().addAll(l1,pSize,l2,mRate,l3,types,initializePopButton,increaseGenButton);



        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Individuals");
        yAxis.setLabel("Fitness");
        final LineChart<Number, Number> currentPopulationChart = new LineChart<Number, Number>(xAxis, yAxis);
        currentPopulationChart.setTitle("Generation 0");
        vBox.getChildren().add(currentPopulationChart);
        currentPopulationChart.getData().add(currentPopLine);


        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Generations");
        yAxis1.setLabel("Median Fitness");
        final LineChart<Number, Number> allGenerationsChart = new LineChart<Number, Number>(xAxis1, yAxis1);
        allGenerationsChart.setTitle("All Generations");
        vBox.getChildren().add(allGenerationsChart);
        allGenerationsChart.getData().add(allGenLine);

        vBox.getChildren().add(label);


        initializePopButton.setOnAction((event)->{
            currentPopLine.getData().clear();
            allGenLine.getData().clear();
            p = new Population((String) types.getValue(), Integer.parseInt(pSize.getText()), Double.parseDouble(mRate.getText()),label);
            Individual[] pop = p.getPopulation();
            for (int i=0;i<pop.length;i++){
                currentPopLine.getData().add(new XYChart.Data(i, pop[i].getFitness()));
            }

            allGenLine.getData().add(new XYChart.Data(p.getCurrentGenerationCount(), p.getMedian()));
        });


        increaseGenButton.setOnAction((event)->{
            for (int i=0;i<1;i++){
                p.newGeneration();
            }
            Individual[] pop1 = p.getPopulation();
            currentPopLine.getData().clear();


            for (int i=0;i<pop1.length;i++){
                currentPopLine.getData().add(new XYChart.Data(i, pop1[i].getFitness()));
            }
            allGenLine.getData().add(new XYChart.Data(p.getCurrentGenerationCount(), p.getMedian()));
            currentPopulationChart.setTitle("Generation "+p.getCurrentGenerationCount());

        });




        Scene scene = new Scene(layout, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();





    }

    public static void main(String[] args) {
        launch(args);
    }


}
