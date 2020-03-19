package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Automaty Komórkowe");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Label rozmiar = new Label("Rozmiar:");
        grid.add(rozmiar, 0, 0);

        TextField rozmiarTextField = new TextField();
        grid.add(rozmiarTextField, 0, 1);

        Label iteracje = new Label("Iteracje:");
        grid.add(iteracje, 1, 0);

        TextField iteracjeTextField = new TextField();
        grid.add(iteracjeTextField, 1, 1);

        Label regula = new Label("Reguła:");
        grid.add(regula, 2, 0);

        ChoiceBox regulaChoiceBox = new ChoiceBox();
        regulaChoiceBox.setMinWidth(50.);
        grid.add(regulaChoiceBox, 2, 1);

        regulaChoiceBox.getItems().add("30");
        regulaChoiceBox.getItems().add("60");
        regulaChoiceBox.getItems().add("90");
        regulaChoiceBox.getItems().add("120");
        regulaChoiceBox.getItems().add("225");

        Button start = new Button("Start");
        start.setMinWidth(150.);
        grid.add(start, 3, 1);
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GridPane.setColumnSpan(canvas, 4);
        start.setOnAction(actionEvent ->  {
            gc.clearRect(0,0,600,600);
            String rule = (String) regulaChoiceBox.getValue();
            int size=Integer.parseInt(rozmiarTextField.getText());
            int iteration = Integer.parseInt(iteracjeTextField.getText());
            boolean[][] gd=new boolean[iteration][size];
            for(int i =0; i< size; i++){
                if(i==size/2)
                    gd[0][i]=true;
                else
                    gd[0][i]=false;
            }
            int state;
            switch(rule){
                case "30":
                    for(int i =1; i< iteration; i++){
                        for (int j = 0; j < size; j++) {
                            state=get_state(gd, i, j, size);
                            if (state==1||state==2||state==3||state==4)
                                gd[i][j]=true;
                            else
                                gd[i][j]=false;
                        }
                    }
                    break;
                case "60":
                    for(int i =1; i< iteration; i++){
                        for (int j = 0; j < size; j++) {
                            state=get_state(gd, i, j, size);
                            if (state==5||state==2||state==3||state==4)
                                gd[i][j]=true;
                            else
                                gd[i][j]=false;
                        }
                    }
                    break;
                case "90":
                    for(int i =1; i< iteration; i++){
                        for (int j = 0; j < size; j++) {
                            state=get_state(gd, i, j, size);
                            if (state==1||state==3||state==4||state==6)
                                gd[i][j]=true;
                            else
                                gd[i][j]=false;
                        }
                    }
                    break;
                case "120":
                    for(int i =1; i< iteration; i++){
                        for (int j = 0; j < size; j++) {
                            state=get_state(gd, i, j, size);
                            if (state==3||state==4||state==5||state==6)
                                gd[i][j]=true;
                            else
                                gd[i][j]=false;
                        }
                    }
                    break;
                case "225":
                    for(int i =1; i< iteration; i++){
                        for (int j = 0; j < size; j++) {
                            state=get_state(gd, i, j, size);
                            if (state==0||state==5||state==6||state==7)
                                gd[i][j]=true;
                            else
                                gd[i][j]=false;
                        }
                    }
            }
            draw(gc, gd, size, iteration);
        });
        grid.add(canvas, 0, 3);

        Scene scene = new Scene(grid, 650, 720);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private int get_state(boolean[][]grid, int i, int j, int size){
        boolean a, b, c;
        if(j==0)
            a=grid[i-1][size-1];
        else
            a=grid[i-1][j-1];
        b=grid[i-1][j];
        if(j==size-1)
            c=grid[i-1][0];
        else
            c=grid[i-1][j+1];
        if(a) {
            if (b){
                if (c)
                    return 7;
                else
                    return 6;
            }
            else if(c)
                return 5;
            else
                return 4;
        }
        else if(b) {
            if (c)
                return 3;
            else
                return 2;
        }
        else if(c)
            return 1;
        else
            return 0;
    }

    private void draw(GraphicsContext gc, boolean[][] grid, int size, int iteration) {
        int a;
        if(size<iteration)
            a=600/iteration;
        else
            a=600/size;
        gc.setFill(Color.RED);
        for(int i =0; i< iteration; i++){
            for (int j = 0; j < size; j++) {
                if(grid[i][j])
                    gc.fillRect(j*a, i*a, a, a);
            }
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
