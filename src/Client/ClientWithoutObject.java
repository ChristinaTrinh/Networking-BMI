package Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
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

public class ClientWithoutObject extends Application{
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
        gP.add(new Label("Weight in pounds " ), 0, 0);
        gP.add(new Label("Height in inches "), 0, 1);
        gP.add(tfw, 1, 0);
        gP.add(tfh, 1, 1);
        gP.add(bts, 2, 1);
        tfw.setAlignment(Pos.BASELINE_RIGHT);
        tfh.setAlignment(Pos.BASELINE_RIGHT);
        tfw.setPrefColumnCount(5);
        tfh.setPrefColumnCount(5);
        BorderPane pane = new BorderPane();
        pane.setCenter(new ScrollPane(ta));
        pane.setTop(gP);
        
        Scene scene = new Scene(pane, 200, 200);
        primaryStage.setTitle("Exercise#31.2 Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        try{
            Socket socket = new Socket("localhost",8000);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream((socket.getInputStream()));
            bts.setOnAction(e->connectToServer());
        } catch (IOException ex){
            ta.appendText(ex.toString());
        }
    }
    public void connectToServer(){
        try {
            double weight = Double.parseDouble(tfw.getText());
            output.writeObject(weight);
            double height = Double.parseDouble(tfh.getText());
            output.writeObject(height);
            String report = "";
            try{
                report = (String)input.readObject();
            } catch (ClassNotFoundException e){}
            ta.appendText("Weight: "+weight+" \nHeight: "+height+"\n");
            ta.appendText(report);
        } catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
    public static void main(String[] args){
        launch(args);
        System.exit(0);
    }
}
