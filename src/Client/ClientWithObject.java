package Client;
import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientWithObject extends Application{
    private TextField tfn = new TextField();
    private TextField tfa = new TextField();
    private TextField tfw = new TextField();
    private TextField tfh = new TextField();
    private Button bts = new Button("Submit");
    private TextArea ta = new TextArea();
    ObjectInputStream input;
    ObjectOutputStream output;
    @Override
    public void start(Stage primaryStage){
        ta.setWrapText(true);
        GridPane gP = new GridPane();
        gP.add(new Label("Name"), 0, 0);
        gP.add(new Label("Age"), 0, 1);
        gP.add(new Label("Weight in pounds"), 0, 2);
        gP.add(new Label("Height in inches"), 0, 3);
        gP.add(tfn, 1, 0);
        gP.add(tfa, 1, 1);
        gP.add(tfw, 1, 2);
        gP.add(tfh, 1, 3);
        gP.add(bts, 2, 1);
        tfn.setAlignment(Pos.BASELINE_RIGHT);
        tfa.setAlignment(Pos.BASELINE_RIGHT);
        tfw.setAlignment(Pos.BASELINE_RIGHT);
        tfh.setAlignment(Pos.BASELINE_RIGHT);
        tfn.setPrefColumnCount(5);
        tfa.setPrefColumnCount(5);
        tfw.setPrefColumnCount(5);
        tfh.setPrefColumnCount(5);
        BorderPane pane = new BorderPane();
        pane.setCenter(new ScrollPane(ta));
        pane.setTop(gP);
        
        Scene scene = new Scene(pane, 200, 200);
        primaryStage.setTitle("Exercise#31.2 Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        bts.setOnAction(e->connectToServer());
        try{
            Socket socket = new Socket("localhost",8000);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex){
            ta.appendText(ex.toString());
        }
    }
    public void connectToServer(){
        try {
            String name = tfn.getText().trim();
            int age = Integer.parseInt(tfa.getText());
            double weight = Double.parseDouble(tfw.getText().trim());
            double height = Double.parseDouble(tfh.getText());
            SerializedBMI bmi = new SerializedBMI(name, age, weight, height);
            output.writeObject(bmi);
            try {
                bmi = (SerializedBMI)input.readObject();
            } catch (ClassNotFoundException e){}
            ta.appendText(bmi.toString()+"     ");
            ta.appendText(bmi.getReport());
        } catch (IOException ex){}
    }
    public static void main(String[] args){
        launch(args);
        System.exit(0);
    }
}

